package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SparkServerTest {

    private SparkServer sparkServer;

    @BeforeEach
    public void setUp() {
        sparkServer = SparkServer.getInstance();
    }

    @Test
    public void testGetServiceRegistration() {
        RestService mockService = new RestService() {
            @Override
            public void handleRequest(BufferedReader in, OutputStream out) {
            }
        };

        sparkServer.get("/test", mockService);
        RestService service = SparkServer.findHandler("/test", "GET");
        assertNotNull(service);
    }

    @Test
    public void testGetServiceNotRegistered() {
        RestService service = SparkServer.findHandler("/not-found", "GET");
        assertNull(service);
    }
}
