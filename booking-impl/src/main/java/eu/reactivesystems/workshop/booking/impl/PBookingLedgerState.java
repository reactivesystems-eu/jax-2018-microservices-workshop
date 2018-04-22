package eu.reactivesystems.workshop.booking.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lightbend.lagom.serialization.Jsonable;
import io.vavr.collection.*;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The booking ledger state.
 */
@Value
public final class PBookingLedgerState implements Jsonable {

    // TODO add required state.


    private static final long serialVersionUID = 1L;

    private final PBookingLedgerStatus status;

    @JsonCreator
    public PBookingLedgerState(@JsonProperty("status") final PBookingLedgerStatus status) {
        this.status = status;
    }

    static PBookingLedgerState uninitialized() {
        return new PBookingLedgerState(PBookingLedgerStatus.NOT_CREATED);
    }
}
