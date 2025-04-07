package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should not be blank, and should not contain more than 40 characters";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX) && (test.length() <= 40);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        //@@author ambertan77-reused
        // reused from https://stackoverflow.com/questions/33381237/string-doesnt-equal-string-with-spaces
        // with minor modifications
        String otherNameWithoutSpace = otherName.fullName.replaceAll("\\s+", "");
        String fullNameWithoutSpace = fullName.replaceAll("\\s+", "");
        //@@author
        return fullNameWithoutSpace.equalsIgnoreCase(otherNameWithoutSpace);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
