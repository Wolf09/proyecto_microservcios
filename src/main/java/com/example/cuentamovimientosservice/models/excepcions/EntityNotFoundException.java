package com.example.cuentamovimientosservice.models.excepcions;

public class EntityNotFoundException extends Throwable {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
