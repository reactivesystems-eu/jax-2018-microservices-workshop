package eu.reactivesystems.workshop.user.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

import java.util.UUID;

public interface UserEvent extends Jsonable, AggregateEvent<UserEvent> {

    @Value
    class UserUpdated implements UserEvent {
         UUID id;
         String name;
    }

    @Override
    default AggregateEventTag<UserEvent> aggregateTag() {
        return AggregateEventTag.of(UserEvent.class);
    }
}
