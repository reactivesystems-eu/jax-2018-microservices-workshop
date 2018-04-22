package eu.reactivesystems.workshop.booking.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.api.deser.PathParamSerializers;
import com.lightbend.lagom.javadsl.api.transport.Method;
import eu.reactivesystems.workshop.security.SecurityHeaderFilter;

import java.util.UUID;

import static com.lightbend.lagom.javadsl.api.Service.*;

/**
 * Service to book rooms/appartments.
 */
public interface BookingService extends Service {

    /* TODO
    Implement the needed service calls. See
    https://www.lagomframework.com/documentation/1.4.x/java/ServiceDescriptors.html
     */
    ServiceCall<NotUsed, String> healthCheck();


    @Override
    default Descriptor descriptor() {
        return named("bookings").withCalls(
                restCall(Method.GET, "api/bookings/health", this::healthCheck)
        ).withPathParamSerializer(
                UUID.class, PathParamSerializers.required("UUID", UUID::fromString, UUID::toString));
    }
}
