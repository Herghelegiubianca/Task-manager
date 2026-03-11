package org.example.BusinessLogic;

public class NonexistentTask extends Exception {
    public NonexistentTask(String message) {
        super(message);
    }
}
