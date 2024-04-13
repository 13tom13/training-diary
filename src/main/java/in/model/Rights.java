package in.model;

public enum Rights {
    WRITE,
    EDIT,
    DELETE,
    STATISTICS;

    @Override
    public String toString() {
        return switch (this) {
            case WRITE -> "WRITE";
            case EDIT -> "EDIT";
            case DELETE -> "DELETE";
            case STATISTICS -> "STATISTICS";
        };
    }
}
