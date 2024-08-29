package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ResponseHelper {

    public static void sendResponse(OutputStream out, int statusCode, String statusText, String contentType, byte[] content) throws IOException {
        PrintWriter pw = new PrintWriter(out, false);
        pw.printf("HTTP/1.1 %d %s\r\n", statusCode, statusText);
        pw.printf("Content-Type: %s\r\n", contentType);
        pw.printf("Content-Length: %d\r\n", content.length);
        pw.print("\r\n");
        pw.flush();
        out.write(content);
        out.flush();
    }

    public static void sendError(OutputStream out, int statusCode, String statusText) throws IOException {
        String errorMessage = String.format("{\"error\":\"%s\"}", statusText);
        sendResponse(out, statusCode, statusText, "application/json", errorMessage.getBytes(StandardCharsets.UTF_8));
    }
}