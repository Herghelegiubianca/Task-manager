package org.example.BusinessLogic;

public class NonexistentEmployee extends Exception {
    public NonexistentEmployee(String message) {
        super(message);
    }
}
