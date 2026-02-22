package org.example.miniusos.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String name, Long id) {
        super(String.format("%s with id %d already exists", name, id));
    }
}
