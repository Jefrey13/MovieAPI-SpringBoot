package com.rest.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
@EnableCaching
public class MovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieApplication.class, args);
	}

	//Generardo y importarlo
	//Conectado al sistema de persistencia
	//Conectado al Mysql por medio de las application properties.
	//Hemos preparado la estructura que va a tener.
	//Modelado de base de datos y repositorios, para dar soporte a las futuras acciones solicitadas por los controladores.
	// -- Mapear la estructura de datos, para luego poderla usar por medio de POO. ORM de persistencia
	// -- Lo que hace hibernate y jpa en spring es usar una clase para mapear datos contra el sistema de persistencia, y no usar sistemas de herencias como larabel o django, que heredan clases bases.
	// -- En versiones anterior de SDK de java no se importa de jakarta, lo hace de javax
	//Controladores
	// -- Verbo de petición y el Long Tailing de la petición.
	//Seguridad: Spring boot no permite ls peticiones CROSS SIGHTING (Peticiones que no son hechas del mismo sitio).
	// -- Agregar la anotacion de CROSSORIGIN.
	// -- lombok, DAO, DTO y SERVICES.
	// .env, docker, railway.
	// BigDecimal, mejor para trabajar con dinero
	// Logger, Chaching, APi Version,  error handling, pagination, testing, and documenting the API.
/*-----------------------------------------------------------------------------------------------------------
* Solution 3: @ControllerAdvice
Spring 3.2 brings support for a global @ExceptionHandler with the @ControllerAdvice annotation.


freestar

This enables a mechanism that breaks away from the older MVC model and makes use of ResponseEntity along with the type safety and flexibility of @ExceptionHandler:

@ControllerAdvice
public class RestResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
      = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
Copy
The@ControllerAdvice annotation allows us to consolidate our multiple, scattered @ExceptionHandlers from before into a single, global error handling component.

The actual mechanism is extremely simple but also very flexible:

It gives us full control over the body of the response as well as the status code.
It provides mapping of several exceptions to the same method, to be handled together.
It makes good use of the newer RESTful ResponseEntity response.
One thing to keep in mind here is to match the exceptions declared with @ExceptionHandler to the exception used as the argument of the method.

If these don’t match, the compiler will not complain — no reason it should — and Spring will not complain either.

However, when the exception is actually thrown at runtime, the exception resolving mechanism will fail with:

java.lang.IllegalStateException: No suitable resolver for argument [0] [type=...]
HandlerMethod details: ...
Copy
5. Solution 4: ResponseStatusException (Spring 5 and Above)
Spring 5 introduced the ResponseStatusException class.

We can create an instance of it providing an HttpStatus and optionally a reason and a cause:

@GetMapping(value = "/{id}")
public Foo findById(@PathVariable("id") Long id, HttpServletResponse response) {
    try {
        Foo resourceById = RestPreconditions.checkFound(service.findOne(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        return resourceById;
     }
    catch (MyResourceNotFoundException exc) {
         throw new ResponseStatusException(
           HttpStatus.NOT_FOUND, "Foo Not Found", exc);
    }
}
Copy
What are the benefits of using ResponseStatusException?

Excellent for prototyping: We can implement a basic solution quite fast.
One type, multiple status codes: One exception type can lead to multiple different responses. This reduces tight coupling compared to the @ExceptionHandler.
We won’t have to create as many custom exception classes.
We have more control over exception handling since the exceptions can be created programmatically.
And what about the tradeoffs?

There’s no unified way of exception handling: It’s more difficult to enforce some application-wide conventions as opposed to @ControllerAdvice, which provides a global approach.
Code duplication: We may find ourselves replicating code in multiple controllers.
We should also note that it’s possible to combine different approaches within one application.

For example, we can implement a @ControllerAdvice globally but also ResponseStatusExceptions locally.

However, we need to be careful: If the same exception can be handled in multiple ways, we may notice some surprising behavior. A possible convention is to handle one specific kind of exception always in one way.

For more details and further examples, see our tutorial on ResponseStatusException.

6. Handle the Access Denied in Spring Security
The Access Denied occurs when an authenticated user tries to access resources that he doesn’t have enough authorities to access.

6.1. REST and Method-Level Security
Finally, let’s see how to handle Access Denied exception thrown by method-level security annotations – @PreAuthorize, @PostAuthorize, and @Secure.

Of course, we’ll use the global exception handling mechanism that we discussed earlier to handle the AccessDeniedException as well:

@ControllerAdvice
public class RestResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
      Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
          "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    ...
}
Copy
7. Spring Boot Support
Spring Boot provides an ErrorController implementation to handle errors in a sensible way.

In a nutshell, it serves a fallback error page for browsers (a.k.a. the Whitelabel Error Page) and a JSON response for RESTful, non-HTML requests:

{
    "timestamp": "2019-01-17T16:12:45.977+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Error processing the request!",
    "path": "/my-endpoint-with-exceptions"
}
Copy
As usual, Spring Boot allows configuring these features with properties:

server.error.whitelabel.enabled: can be used to disable the Whitelabel Error Page and rely on the servlet container to provide an HTML error message
server.error.include-stacktrace: with an always value; includes the stacktrace in both the HTML and the JSON default response
server.error.include-message: since version 2.3, Spring Boot hides the message field in the response to avoid leaking sensitive information; we can use this property with an always value to enable it
Apart from these properties, we can provide our own view-resolver mapping for /error, overriding the Whitelabel Page.

We can also customize the attributes that we want to show in the response by including an ErrorAttributes bean in the context. We can extend the DefaultErrorAttributes class provided by Spring Boot to make things easier:

@Component
public class MyCustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(
      WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes =
          super.getErrorAttributes(webRequest, options);
        errorAttributes.put("locale", webRequest.getLocale()
            .toString());
        errorAttributes.remove("error");

        //...

        return errorAttributes;
    }
}
Copy
If we want to go further and define (or override) how the application will handle errors for a particular content type, we can register an ErrorController bean.

Again, we can make use of the default BasicErrorController provided by Spring Boot to help us out.


freestar

For example, imagine we want to customize how our application handles errors triggered in XML endpoints. All we have to do is define a public method using the @RequestMapping, and stating it produces application/xml media type:

@Component
public class MyErrorController extends BasicErrorController {

    public MyErrorController(
      ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        super(errorAttributes, serverProperties.getError());
    }

    @RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Map<String, Object>> xmlError(HttpServletRequest request) {

    // ...

    }
}
Copy
Note: here we’re still relying on the server.error.* Boot properties we might have been defined in our project, which are bound to the ServerProperties bean.
*
*
* -----------------------------------------------------------------------------------------------------------
*
*
*Como integrar Swagger
* Versionado y Caching
	* */
}
