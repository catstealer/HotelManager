package com.example.demo.config;

public class Messages {

    private static String CONTROLLER_MESSAGE = "Received request to %s controller";

    public static String makeMessageForController(String nameOfController){
        return String.format(CONTROLLER_MESSAGE, nameOfController);
    }

}
