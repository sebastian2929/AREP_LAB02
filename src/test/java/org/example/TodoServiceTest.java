package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    private TodoService todoService;
    private OutputStream outputStream;

    @BeforeEach
    public void setUp() {
        todoService = new TodoService();
        outputStream = mock(OutputStream.class);
    }

    @Test
    public void testHandleGetTodos() throws IOException {
        todoService.handlePostTodos(outputStream, "{\"task\":\"Test task\"}");
        todoService.handleGetTodos(outputStream);

        byte[] expectedResponse = "{\"todos\":[\"Test task\"]}".getBytes(StandardCharsets.UTF_8);
        verify(outputStream).write(expectedResponse);
    }

    @Test
    public void testHandlePostTodos() throws IOException {
        String body = "{\"task\":\"New task\"}";
        todoService.handlePostTodos(outputStream, body);

        String expectedResponse = "{\"status\":\"success\",\"message\":\"Task added successfully\"}";
        verify(outputStream).write(expectedResponse.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testHandleClearTodos() throws IOException {
        todoService.handlePostTodos(outputStream, "{\"task\":\"Task to remove\"}");
        todoService.handleClearTodos(outputStream);

        String expectedResponse = "{\"status\":\"success\",\"message\":\"All tasks cleared\"}";
        verify(outputStream).write(expectedResponse.getBytes(StandardCharsets.UTF_8));
    }
}
