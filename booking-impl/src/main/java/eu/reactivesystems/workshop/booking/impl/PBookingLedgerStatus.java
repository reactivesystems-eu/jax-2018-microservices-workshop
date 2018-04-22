package eu.reactivesystems.workshop.booking.impl;

/**
 * Accommodation status.
 */
public enum PBookingLedgerStatus {
    /**
     * The accommodation is not initialized yet.
     */
    NOT_CREATED,
    /**
     * The accommodation is listed.
     */
    LISTED,
    /**
     * The accommodation is unlisted.
     */
    UNLISTED
}
