package eu.reactivesystems.workshop.user.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.PathParamSerializers;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.UUID;

import static com.lightbend.lagom.javadsl.api.Service.*;

public interface UserService extends Service {

    ServiceCall<User, User> createUser();

    ServiceCall<User, User> updateUser();

    ServiceCall<NotUsed, User> getUser(UUID userId);


    @Override
    default Descriptor descriptor() {
        return named("user").withCalls(
                pathCall("/api/user", this::createUser),
                restCall(Method.PUT, "/api/user", this::updateUser),
                pathCall("/api/user/:id", this::getUser)
        ).withPathParamSerializer(UUID.class, PathParamSerializers.required("UUID", UUID::fromString, UUID::toString));
    }
}
