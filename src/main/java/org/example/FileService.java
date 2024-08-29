package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.OutputStream;

public class FileService {

    public void handleGetRequest(OutputStream out, String requestedFile) throws IOException {
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
