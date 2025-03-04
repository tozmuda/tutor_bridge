package org.tutorBridge.validation;

import java.util.Collection;
import java.util.List;

public class ValidationException extends RuntimeException {
    private final Collection<String> messages;

    public ValidationException(String message) {
        super(message);
        this.messages = List.of(message);
    }

    public ValidationException(Collection<String> messages) {
        super(String.join("\n", messages));
        this.messages = messages;
    }

}
