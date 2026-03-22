package org.example.businessLogic;

public class NonexistentEmployee extends Exception {
    public NonexistentEmployee(String message) {
        super(message);
    }
}
