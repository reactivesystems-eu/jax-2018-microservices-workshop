package eu.reactivesystems.workshop.listing.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Value;

import java.util.UUID;

/**
 * Messages published by the listing service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(ListingMessage.ListingCreated.class),
        @JsonSubTypes.Type(ListingMessage.ListingUpdated.class)
})
public abstract class ListingMessage {

    /**
     * Indicates an accommodation listing has been created.
     */
    @JsonTypeName(value = "listing-created")
    @Value
    public static final class ListingCreated extends ListingMessage {
        private final UUID listingId;
        private final UUID host;

        public ListingCreated(UUID listingId, UUID host) {
            this.listingId = listingId;
            this.host = host;
        }
    }

    /**
     * Indicates an accommodation listing has been updated.
     */
    @JsonTypeName(value = "listing-updated")
    @Value
    public static final class ListingUpdated extends ListingMessage {

        public ListingUpdated() {
        }
    }

}
