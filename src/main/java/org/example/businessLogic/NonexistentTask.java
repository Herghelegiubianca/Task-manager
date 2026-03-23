package org.example.businessLogic;

public class NonexistentTask extends Exception {
    public NonexistentTask(String message) {
        super(message);
    }
}
