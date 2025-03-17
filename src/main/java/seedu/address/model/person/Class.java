package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's class in the address book.
 * Guarantees: immutable; is always valid as there are no restrictions on class
 */
public class Class {

    public static final String MESSAGE_CONSTRAINTS =
            "Classes can take any values, and it should not be blank for students";

    /*
     * The first character of the class must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Class}.
     *
     * @param classInput A valid class.
     */
    public Class(String classInput) {
        requireNonNull(classInput);
        checkArgument(isValidClass(classInput), MESSAGE_CONSTRAINTS);
        value = classInput;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidClass(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Class)) {
            return false;
        }

        Class otherClass = (Class) other;
        return value.equals(otherClass.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
