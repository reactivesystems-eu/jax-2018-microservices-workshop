package eu.reactivesystems.workshop.booking.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

import java.util.UUID;

/**
 * The id and host of an accommodation for which a description
 * has been created and that can be booked.
 */
@Value
public final class Listing implements Jsonable {
    /**
     * The accommodation listing.
     */
    private final UUID listingID;
    /**
     * The accommodation's host.
     */
    private final UUID host;

    @JsonCreator
    public Listing(@JsonProperty("listingID") UUID listingID, @JsonProperty("host") UUID host) {
        this.listingID = listingID;
        this.host = host;
    }
}
