package eu.reactivesystems.workshop.booking.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Value;

/**
 * Messages published by the booking service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(BookingEvent.NoMessage.class)
})
public abstract class BookingEvent {

    /**
     * Placeholder...
     */
    @JsonTypeName(value = "booking-nomessage")
    @Value
    public static final class NoMessage extends BookingEvent {

        public NoMessage() {
        }
    }
}
