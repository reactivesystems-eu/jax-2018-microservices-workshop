package eu.reactivesystems.workshop.listing.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import eu.reactivesystems.workshop.listing.api.ListingService;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class ListingServiceImpl implements ListingService {
    private final PersistentEntityRegistry registry;

    @Inject
    public ListingServiceImpl(PersistentEntityRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ServiceCall<NotUsed, String> healthCheck() {
        return notUsed -> completedFuture("UP");
    }

}
