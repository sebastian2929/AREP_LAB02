# Web Framework Development for REST Services and Static File Management

## Descripción del Proyecto

Este proyecto amplía un servidor web existente, que actualmente soporta archivos HTML, JavaScript, CSS e imágenes, transformándolo en un marco web completamente funcional. Este marco permite el desarrollo de aplicaciones web con servicios REST backend. Proporciona herramientas para definir servicios REST utilizando funciones lambda, gestionar valores de consulta dentro de las solicitudes y especificar la ubicación de archivos estáticos.

## Requisitos

-   **Java Development Kit (JDK) 8 o superior**
-   **Maven 3.6.0 o superior**
-   **Un IDE como IntelliJ IDEA, Eclipse, o simplemente un editor de texto como Visual Studio Code**
-   **Un navegador web**

## Cómo Ejecutar el Proyecto

**1. Clona el Repositorio:**

        git clone https://github.com/tu_usuario/tu_repositorio.git 

**2. Construye el Proyecto:** Asegúrate de tener Maven instalado. Luego, ejecuta:

        mvn exec:java

**3. Accede a la Aplicación:** Abre un navegador y visita http://localhost:8080.

## Arquitectura

### Diagrama de Clases

```mermaid
classDiagram
    class SparkServer {
        +static SparkServer getInstance()
        +static RestService findHandler(String path, String method)
        +static void get(String path, RestService service)
        +static void post(String path, RestService service)
        +static void delete(String path, RestService service)
        +void setStaticFileLocation(String path)
        +String getStaticFileLocation()
    }
    
    class RestService {
        <<interface>>
        +void handleRequest(BufferedReader in, OutputStream out) throws IOException
    }
    
    class RequestHandler {
        -Socket clientSocket
        +void run()
        -void handleStaticFiles(OutputStream out, String requestedFile) throws IOException
    }
    
    class ResponseHelper {
        +static void sendResponse(OutputStream out, int statusCode, String statusText, String contentType, byte[] content) throws IOException
        +static void sendError(OutputStream out, int statusCode, String statusText) throws IOException
    }
    
    class TodoService {
        +void handleGetTodos(OutputStream out) throws IOException
        +void handlePostTodos(OutputStream out, String body) throws IOException
        +void handleClearTodos(OutputStream out) throws IOException
    }
    
    class WebServer {
        +static void main(String[] args)
        -static int getContentLength(BufferedReader in) throws IOException
        -static String getRequestBody(BufferedReader in, int contentLength) throws IOException
    }
    
    SparkServer "1" -- "0..*" RestService : manages
    SparkServer "1" -- "0..*" RequestHandler : handles requests
    RequestHandler "1" -- "1" ResponseHelper : uses
    WebServer "1" -- "1" SparkServer : uses
    WebServer "1" -- "1" TodoService : uses
```

### Diagrama de Componentes

```mermaid
graph TD
    A[Web Server] -->|Handles Requests| B[SparkServer]
    B -->|Defines Services| C[RestService]
    B -->|Manages Static Files| D[File System]
    A -->|Processes Requests| E[RequestHandler]
    E -->|Generates Responses| F[ResponseHelper]
    A -->|Interacts with| G[TodoService]
```


## Pruebas

**1. Probar el proyecto:** Ejecuta el siguiente comando:

    mvn test

![Resultado de las pruebas](https://github.com/user-attachments/assets/f4613dda-b57c-4481-9c8e-c926b9eac59a)  
  
   
  
**2. Pruebas de funcionamiento:** A continuación se puede observar el programa funcionando correctamente:

![Imagen del programa](https://github.com/user-attachments/assets/8a0d75a7-ea0d-4d09-9ba1-d70a6e5d4b21)

![Captura de ejecución](https://github.com/user-attachments/assets/38879d35-4d01-44f3-9927-7ef4f7eacef5)



## Contacto

Autor: Sebastián David Blanco Rodríguez

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
