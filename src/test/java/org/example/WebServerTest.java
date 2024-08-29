package org.example;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebServerTest {

    @Test
    public void testGetContentLength() throws IOException {
        String request = "POST / HTTP/1.1\r\nContent-Length: 15\r\n\r\n";
        BufferedReader reader = new BufferedReader(new StringReader(request));
        int contentLength = WebServer.getContentLength(reader);
        assertEquals(15, contentLength);
    }

    @Test
    public void testGetRequestBody() throws IOException {
        String body = "Hello, World!";
        StringReader bodyReader = new StringReader(body);
        BufferedReader reader = new BufferedReader(bodyReader);
        String result = WebServer.getRequestBody(reader, body.length());
        assertEquals(body, result);
    }
}
