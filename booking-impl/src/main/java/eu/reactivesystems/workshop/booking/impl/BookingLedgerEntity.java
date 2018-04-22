package eu.reactivesystems.workshop.booking.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * The persistent entity that tracks all bookings for one accommodation listing.
 */
public class BookingLedgerEntity extends PersistentEntity<BookingCommand, PBookingEvent, PBookingLedgerState> {

    @Override
    public Behavior initialBehavior(final Optional<PBookingLedgerState> snapshotState) {
        PBookingLedgerStatus status = snapshotState
                .map(PBookingLedgerState::getStatus)
                .orElse(PBookingLedgerStatus.NOT_CREATED);
        switch (status) {
            case NOT_CREATED:
                return initial();
            default:
                throw new IllegalStateException();
        }
    }


    /**
     * Behavior for the "initial" state. The accommodation has to be
     * explicitly created with a host in order to make any bookings.
     */
    private Behavior initial() {
        BehaviorBuilder builder = newBehaviorBuilder(PBookingLedgerState.uninitialized());

        // TODO handle commands and events

        return builder.build();
    }


}
