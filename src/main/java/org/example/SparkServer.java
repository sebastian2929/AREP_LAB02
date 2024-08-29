package org.example;


import java.util.HashMap;
import java.util.Map;

public class SparkServer {

    private static SparkServer instance;
    private static final Map<String, RestService> getServices = new HashMap<>();
    private static final Map<String, RestService> postServices = new HashMap<>();
    private static final Map<String, RestService> deleteServices = new HashMap<>();
    private String staticFileLocation;

    private SparkServer() {
    }

    public static SparkServer getInstance() {
        if (instance == null) {
            instance = new SparkServer();
        }
        return instance;
    }

    public static RestService findHandler(String path, String method) {
        switch (method.toUpperCase()) {
            case "GET":
                return getServices.get(path);
            case "POST":
                return postServices.get(path);
            case "DELETE":
                return deleteServices.get(path);
            default:
                return null;
        }
    }

    public static void get(String path, RestService service) {
        getServices.put(path, service);
    }

    public static void post(String path, RestService service) {
        postServices.put(path, service);
    }

    public static void delete(String path, RestService service) {
        deleteServices.put(path, service);
    }

    public void setStaticFileLocation(String path) {
        this.staticFileLocation = path;
    }

    public String getStaticFileLocation() {
        return staticFileLocation;
    }
}