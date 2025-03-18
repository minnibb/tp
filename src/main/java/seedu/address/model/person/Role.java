package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's role in the address book.
 */
public class Role {
    /**
     * All possible types of contacts in the address book.
     */
    public enum Type {
        STUDENT, STAFF, PARENT
    }

    public static final String MESSAGE_CONSTRAINTS = "Choose only one from Student, Parent or Staff";

    private final Type value;


    /**
     * @param role parent student or staff
     */
    public Role(String role) {
        requireNonNull(role);
        this.value = checkRole(role);
    }

    /**
     * Returns the type of Role of a person.
     *
     * @param input Role that the user has input.
     * @return The type of role of a person.
     */
    public static Type checkRole(String input) {
        if (input.equalsIgnoreCase(Type.PARENT.name())) {
            return Type.PARENT;
        } else if (input.equalsIgnoreCase(Type.STAFF.name())) {
            return Type.STAFF;
        } else if (input.equalsIgnoreCase(Type.STUDENT.name())) {
            return Type.STUDENT;
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks if a String input is a valid role.
     *
     * @param input The user input.
     * @return True if the input is a valid role, and false otherwise.
     */
    public static boolean isValidRole(String input) {
        boolean isParent = input.equalsIgnoreCase(Type.PARENT.name());
        boolean isStaff = input.equalsIgnoreCase(Type.STAFF.name());
        boolean isStudent = input.equalsIgnoreCase(Type.STUDENT.name());
        return (isStaff || isParent) || isStudent;
    }

    public Type getType() {
        return value;
    }

    @Override
    public String toString() {
        String role = value.name();
        return role.charAt(0) + role.substring(1).toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
