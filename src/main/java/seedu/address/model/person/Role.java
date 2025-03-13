package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 */
public class Role {
    public static final String[] VALID_ROLES = {"Student", "Parent", "Staff"};

    public static final String MESSAGE_CONSTRAINTS = "Choose only one from Student, Parent or Staff";
    public final String value;


    /**
     * @param role parent student or staff
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role;
    }


    /**
     * @param test test
     * @return true or false
     */
    public static boolean isValidRole(String test) {
        for (String validRole : VALID_ROLES) {
            if (validRole.equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Role)
                && value.equalsIgnoreCase(((Role) other).value);
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
