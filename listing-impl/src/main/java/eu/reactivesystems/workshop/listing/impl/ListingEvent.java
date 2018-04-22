package eu.reactivesystems.workshop.listing.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;

/**
 * lagom-book-example
 */
public interface ListingEvent extends Jsonable, AggregateEvent<ListingEvent> {

    // TODO: use read side sharding?
    int NUM_SHARDS = 4;
    AggregateEventShards<ListingEvent> TAG = AggregateEventTag.sharded(ListingEvent.class, NUM_SHARDS);

    @Override
    default AggregateEventTagger<ListingEvent> aggregateTag() {
        return TAG;
    }

    // TODO

}
