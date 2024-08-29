package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) {
        SparkServer server = SparkServer.getInstance();
        server.setStaticFileLocation("src/main/resources/webroot");

        server.get("/api/todos", (in, out) -> new TodoService().handleGetTodos(out));
        server.post("/api/todos", (in, out) -> {
            int contentLength = getContentLength(in);
            String body = getRequestBody(in, contentLength);
            new TodoService().handlePostTodos(out, body);
        });
        server.post("/api/todos/clear", (in, out) -> new TodoService().handleClearTodos(out));

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Servidor iniciado en el puerto " + 8080);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new RequestHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int getContentLength(BufferedReader in) throws IOException {
        int contentLength = 0;
        String line;
        while (!(line = in.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.substring("Content-Length:".length()).trim());
            }
        }
        return contentLength;
    }

    static String getRequestBody(BufferedReader in, int contentLength) throws IOException {
        char[] bodyChars = new char[contentLength];
        in.read(bodyChars);
        return new String(bodyChars);
    }
}
