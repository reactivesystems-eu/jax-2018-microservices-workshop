package eu.reactivesystems.workshop.user.impl;

import lombok.Value;
import java.util.UUID;

@Value
public class PUser {
    UUID id;
    String name;
}
