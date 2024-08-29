package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TodoService {

    private static final List<String> todos = new ArrayList<>();

    public void handleGetTodos(OutputStream out) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"todos\":");
        jsonBuilder.append("[");
        for (int i = 0; i < todos.size(); i++) {
            jsonBuilder.append("\"").append(escapeJson(todos.get(i))).append("\"");
            if (i < todos.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]}");

        byte[] jsonBytes = jsonBuilder.toString().getBytes(StandardCharsets.UTF_8);
        ResponseHelper.sendResponse(out, 200, "OK", "application/json", jsonBytes);
    }

    public void handlePostTodos(OutputStream out, String body) throws IOException {
        String task = parseTaskFromJson(body);
        if (task != null && !task.isEmpty()) {
            todos.add(task);
            String responseJson = "{\"status\":\"success\",\"message\":\"Task added successfully\"}";
            ResponseHelper.sendResponse(out, 200, "OK", "application/json", responseJson.getBytes(StandardCharsets.UTF_8));
        } else {
            ResponseHelper.sendError(out, 400, "Bad Request");
        }
    }

    public void handleClearTodos(OutputStream out) throws IOException {
        todos.clear();
        String responseJson = "{\"status\":\"success\",\"message\":\"All tasks cleared\"}";
        ResponseHelper.sendResponse(out, 200, "OK", "application/json", responseJson.getBytes(StandardCharsets.UTF_8));
    }

    private String parseTaskFromJson(String json) {
        if (json == null || json.isEmpty()) return null;
        json = json.trim();
        if (!json.startsWith("{") || !json.endsWith("}")) return null;

        int taskIndex = json.indexOf("\"task\":");
        if (taskIndex == -1) return null;

        int start = json.indexOf("\"", taskIndex + 7) + 1;
        int end = json.indexOf("\"", start);
        if (start == -1 || end == -1) return null;

        return json.substring(start, end);
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}