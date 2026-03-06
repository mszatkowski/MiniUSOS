package org.example.miniusos.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(Class<?> resourceClass, String duplicateValueName, String value) {
        super(String.format("%s with %s %s already exists", resourceClass.getSimpleName(), duplicateValueName, value));
    }
}
