import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import eu.reactivesystems.workshop.listing.api.ListingService;
import eu.reactivesystems.workshop.listing.impl.ListingServiceImpl;

/**
 * Module that registers the listing service.
 */
public class Module extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(ListingService.class, ListingServiceImpl.class);
    }
}