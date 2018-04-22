package eu.reactivesystems.workshop.booking.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity.ReplyType;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

/**
 * A booking command.
 */
public interface BookingCommand extends Jsonable {

    /*
    TODO implement required commands
     */

    /**
     * Unlist/hide the accomodation
     * This is a pattern for commands with no arguments.
     */
    enum HideListing implements BookingCommand, ReplyType<Done> {
        INSTANCE
    }


    @Value
    final class RequestBooking implements BookingCommand, ReplyType<UUID> {
        /**
         * The user that requests the booking.
         */
        private final UUID guest;

        @JsonCreator
        public RequestBooking(@JsonProperty("guest") UUID guest) {
            this.guest = guest;
        }
    }


}
