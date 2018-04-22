package eu.reactivesystems.workshop.user.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;
import java.util.UUID;

public interface UserCommand extends Jsonable {

    @Value
    @AllArgsConstructor(onConstructor = @__(@JsonCreator))
    final class CreateUser implements UserCommand, PersistentEntity.ReplyType<PUser> {
        String name;
    }

    @Value
    @AllArgsConstructor(onConstructor = @__(@JsonCreator))
    final class UpdateUser implements UserCommand, PersistentEntity.ReplyType<PUser> {
        UUID id;
        String name;
    }

    enum GetUser implements UserCommand, PersistentEntity.ReplyType<Optional<PUser>> {
        INSTANCE
    }
}
