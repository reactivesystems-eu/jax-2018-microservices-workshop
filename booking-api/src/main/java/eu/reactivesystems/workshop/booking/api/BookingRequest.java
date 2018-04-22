package eu.reactivesystems.workshop.booking.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

/**
 */
@Value
public final class BookingRequest {
    /**
     * The user that requests the booking.
     */
    private final UUID guest;
    /**
     * The starting date of the booking
     */
    private final LocalDate startingDate;
    /**
     * The duration of the booking
     */
    private final int duration;
    /**
     * The number of guests
     */
    private final int numberOfGuests;

    @JsonCreator
    // parameter annotations needed until https://github.com/lagom/lagom/issues/172 is fixed.
    public BookingRequest(@JsonProperty("guest") UUID guest, @JsonProperty("startingDate") LocalDate startingDate,
                          @JsonProperty("duration") int duration, @JsonProperty("numberOfGuests") int numberOfGuests) {
        this.guest = guest;
        this.startingDate = startingDate;
        this.duration = duration;
        this.numberOfGuests = numberOfGuests;
    }

}
