package eu.reactivesystems.workshop.user.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class UpdateFailureException extends RuntimeException implements Jsonable {

    public static final UpdateFailureException CANT_EDIT_ANOTHER_USER
            = new UpdateFailureException("Can't edit another user");

    private final String message;

    @JsonCreator
    public UpdateFailureException(String message) {
        super(message);
        this.message = message;
    }
}