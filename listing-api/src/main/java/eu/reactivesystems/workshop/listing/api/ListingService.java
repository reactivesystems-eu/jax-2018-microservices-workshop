package eu.reactivesystems.workshop.listing.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.api.deser.PathParamSerializers;
import com.lightbend.lagom.javadsl.api.transport.Method;
import eu.reactivesystems.workshop.security.SecurityHeaderFilter;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.UUID;

import static com.lightbend.lagom.javadsl.api.Service.*;

public interface ListingService extends Service {

    /* TODO
    Implement the needed service calls. See
    https://www.lagomframework.com/documentation/1.4.x/java/ServiceDescriptors.html
     */
    ServiceCall<NotUsed, String> healthCheck();


    @Override
    default Descriptor descriptor() {
        return named("listing").withCalls(
                restCall(Method.GET, "/api/listing/health", this::healthCheck)
        ).withPathParamSerializer(
                UUID.class, PathParamSerializers.required("UUID", UUID::fromString, UUID::toString))
                .withAutoAcl(true);

    }
}
