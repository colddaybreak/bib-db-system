package org.example.cpt402cw3.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " not found with ID: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
