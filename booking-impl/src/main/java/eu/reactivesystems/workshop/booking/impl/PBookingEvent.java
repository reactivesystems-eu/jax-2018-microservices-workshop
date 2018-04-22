package eu.reactivesystems.workshop.booking.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

import java.util.UUID;

/**
 * lagom-book-example
 */
public interface PBookingEvent extends Jsonable, AggregateEvent<PBookingEvent> {

    AggregateEventTag<PBookingEvent> TAG = AggregateEventTag.of(PBookingEvent.class);

    @Override
    default AggregateEventTagger<PBookingEvent> aggregateTag() {
        return TAG;
    }


    /*
    TODO implement events as immutable classes that implement the
    interface
     */
}
