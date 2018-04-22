package eu.reactivesystems.workshop.user.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;
import java.util.UUID;

@Value
@AllArgsConstructor
public class User {

    UUID id;
    String name;

    @JsonCreator
    private User(@JsonProperty("id") Optional<UUID> id, @JsonProperty("name") String name) {
        this.id = id.orElse(null);
        this.name = name;
    }

    public User(String name) {
        this.id = null;
        this.name = name;
    }
}
