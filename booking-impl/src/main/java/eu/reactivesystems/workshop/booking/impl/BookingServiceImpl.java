package eu.reactivesystems.workshop.booking.impl;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.javadsl.Flow;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.broker.TopicProducer;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import eu.reactivesystems.workshop.booking.api.*;
import eu.reactivesystems.workshop.listing.api.ListingMessage;
import eu.reactivesystems.workshop.listing.api.ListingService;
import eu.reactivesystems.workshop.booking.api.BookingEvent;
import eu.reactivesystems.workshop.booking.api.BookingRequest;
import eu.reactivesystems.workshop.booking.api.BookingService;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class BookingServiceImpl implements BookingService {
    private final PersistentEntityRegistry registry;

    @Inject
    public BookingServiceImpl(PersistentEntityRegistry registry, ListingService listingService) {
        this.registry = registry;
        registry.register(BookingLedgerEntity.class);
    }


    @Override
    public ServiceCall<NotUsed, String> healthCheck() {
        return notUsed -> completedFuture("UP");
    }

    private PersistentEntityRef<BookingCommand> entityRef(UUID itemId) {
        return registry.refFor(BookingLedgerEntity.class, itemId.toString());
    }

}
