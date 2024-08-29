package org.example;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.IOException;

public interface RestService {
    void handleRequest(BufferedReader in, OutputStream out) throws IOException;
}
