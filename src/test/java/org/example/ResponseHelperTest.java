package org.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ResponseHelperTest {

    @Test
    public void testSendResponse() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String content = "Hello";
        ResponseHelper.sendResponse(out, 200, "OK", "text/plain", content.getBytes(StandardCharsets.UTF_8));

        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: 5\r\n\r\nHello";
        assertArrayEquals(expectedResponse.getBytes(StandardCharsets.UTF_8), out.toByteArray());
    }
}
