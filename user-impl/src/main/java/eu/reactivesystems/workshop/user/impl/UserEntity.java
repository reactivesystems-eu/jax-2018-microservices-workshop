package eu.reactivesystems.workshop.user.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import eu.reactivesystems.workshop.user.impl.UserEvent.UserUpdated;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class UserEntity extends PersistentEntity<UserCommand, UserEvent, Optional<PUser>> {

    @Override
    public Behavior initialBehavior(Optional<Optional<PUser>> snapshotState) {
        Optional<PUser> user = snapshotState.flatMap(Function.identity());

        if (user.isPresent()) {
            return created(user.get());
        } else {
            return notCreated();
        }
    }

    private Behavior created(PUser user) {
        BehaviorBuilder b = newBehaviorBuilder(Optional.of(user));

        b.setReadOnlyCommandHandler(UserCommand.GetUser.class, (get, ctx) ->
                ctx.reply(state())
        );

        b.setCommandHandler(UserCommand.UpdateUser.class, (cmd, ctx) -> {
            PUser stateUser = state().get();
            return updateUser(cmd, ctx, stateUser, () -> emitUpdatedEvent(cmd, ctx, stateUser));
        });
        b.setEventHandler(UserUpdated.class, updateUserData());

        b.setReadOnlyCommandHandler(UserCommand.CreateUser.class, (create, ctx) ->
                ctx.invalidCommand("User already exists.")
        );

        return b.build();
    }

    private Behavior notCreated() {
        BehaviorBuilder b = newBehaviorBuilder(Optional.empty());

        b.setReadOnlyCommandHandler(UserCommand.GetUser.class, (cmd, ctx) ->
                ctx.reply(state())
        );

        b.setReadOnlyCommandHandler(UserCommand.UpdateUser.class, (cmd, ctx) ->
                ctx.invalidCommand("User does not exists.")
        );

        b.setCommandHandler(UserCommand.CreateUser.class, (cmd, ctx) -> {
            PUser user = new PUser(UUID.fromString(entityId()), cmd.getName());
            return emitUpdatedEvent(cmd, ctx, user);
        });
        b.setEventHandlerChangingBehavior(UserUpdated.class, user -> created(new PUser(user.getId(), user.getName())));

        return b.build();
    }

    private Persist updateUser(UserCommand.UpdateUser cmd, CommandContext ctx, PUser user, Supplier<Persist> onSuccess) {
        if (!user.getId().equals(cmd.getId())) {
            ctx.commandFailed(UpdateFailureException.CANT_EDIT_ANOTHER_USER);
            return ctx.done();
        } else if (!user.getName().equals(cmd.getName())) {
            return onSuccess.get();
        } else {
            // when update and current are equal there's no need to emit an event.
            return ctx.done();
        }
    }

    private Persist emitUpdatedEvent(UserCommand.CreateUser cmd, CommandContext ctx, PUser user) {
        return ctx.thenPersist(
                new UserUpdated(user.getId(), cmd.getName()),
                evt -> ctx.reply(user)
        );
    }

    private Persist emitUpdatedEvent(UserCommand.UpdateUser cmd, CommandContext ctx, PUser user) {
        return ctx.thenPersist(
                new UserUpdated(user.getId(), cmd.getName()),
                evt -> ctx.reply(user)
        );
    }

    private Function<UserUpdated, Optional<PUser>> updateUserData() {
        return (evt) -> Optional.of(new PUser(evt.getId(), evt.getName()));
    }
}
