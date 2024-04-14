package in.model;

public enum Roles {
    USER,
    ADMIN;

    @Override
    public String toString() {
        return switch (this) {
            case USER -> "USER";
            case ADMIN -> "ADMIN";
        };
    }
}
