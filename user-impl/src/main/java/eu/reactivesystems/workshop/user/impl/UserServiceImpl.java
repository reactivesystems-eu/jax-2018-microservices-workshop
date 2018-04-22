package eu.reactivesystems.workshop.user.impl;

import akka.NotUsed;
import akka.actor.ActorSystem;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import eu.reactivesystems.workshop.user.api.User;
import eu.reactivesystems.workshop.user.api.UserService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final PersistentEntityRegistry registry;

    @Inject
    public UserServiceImpl(PersistentEntityRegistry registry, ActorSystem system) {
        this.registry = registry;
        registry.register(UserEntity.class);
    }

    @Override
    public ServiceCall<User, User> createUser() {
        return user -> {
            UUID uuid = UUID.randomUUID();
            UserCommand.CreateUser createUser = new UserCommand.CreateUser(user.getName());
            return entityRef(uuid)
                    .ask(createUser)
                    .thenApply(this::convertUser);
        };
    }

    @Override
    public ServiceCall<User, User> updateUser() {
        return user -> {
            return entityRef(user.getId())
                    .ask(new UserCommand.UpdateUser(user.getId(), user.getName()))
                    .thenApply(this::convertUser);
        };
    }

    @Override
    public ServiceCall<NotUsed, User> getUser(UUID userId) {
        return req -> {
            return entityRef(userId)
                    .ask(UserCommand.GetUser.INSTANCE)
                    .thenApply(maybeUser ->
                            maybeUser.map(this::convertUser).orElseGet(() -> {
                                throw new NotFound("User " + userId + " not found");
                            })
                    );
        };
    }


    private PersistentEntityRef<UserCommand> entityRef(UUID id) {
        return entityRef(id.toString());
    }

    private PersistentEntityRef<UserCommand> entityRef(String id) {
        return registry.refFor(UserEntity.class, id);
    }

    private User convertUser(PUser pUser) {
        return new User(pUser.getId(), pUser.getName());
    }

    private Optional<User> convertOptionalUser(Optional<PUser> maybePUser) {
        return maybePUser.map(this::convertUser);
    }
}
