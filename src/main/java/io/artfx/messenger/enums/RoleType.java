package io.artfx.messenger.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    SELLER( false),
    BUYER(false),
    ORGANIZATION(false),
    USER(true),
    ADMIN(true),
    SUPER_ADMIN(true);

    public final boolean restricted;

    RoleType(boolean restricted) {
        this.restricted = restricted;
    }
}
