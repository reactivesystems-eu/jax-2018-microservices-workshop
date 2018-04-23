import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import eu.reactivesystems.workshop.booking.api.BookingService;
import eu.reactivesystems.workshop.booking.impl.BookingServiceImpl;
import eu.reactivesystems.workshop.listing.api.ListingService;

/**
 * Module that registers the listing service.
 */
public class Module extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(BookingService.class, BookingServiceImpl.class);
        bindClient(ListingService.class);

    }
}