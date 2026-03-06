package org.example.miniusos.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> resourceClass,Long id) {
        super(String.format("%s with id %d not found", resourceClass.getSimpleName(), id));
    }
}
