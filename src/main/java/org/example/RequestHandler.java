package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestHandler implements Runnable {
    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;
            System.out.println("Request: " + requestLine);

            String[] tokens = requestLine.split(" ");
            if (tokens.length < 2) return;
            String method = tokens[0];
            String requestedFile = tokens[1];

            RestService service = SparkServer.findHandler(requestedFile, method);
            if (service != null) {
                service.handleRequest(in, out);
            } else {
                handleStaticFiles(out, requestedFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleStaticFiles(OutputStream out, String requestedFile) throws IOException {
        Path filePath = Paths.get(SparkServer.getInstance().getStaticFileLocation(), requestedFile.equals("/") ? "index.html" : requestedFile.substring(1));
        if (Files.exists(filePath)) {
            byte[] content = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            ResponseHelper.sendResponse(out, 200, "OK", contentType, content);
        } else {
            ResponseHelper.sendError(out, 404, "Not Found");
        }
    }
}