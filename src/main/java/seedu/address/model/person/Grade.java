package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Student's grade in the address book.
 */
public class Grade {

    /**
     * All possible grades.
     */
    public enum Level {
        PRI_1, PRI_2, PRI_3, PRI_4, PRI_5, PRI_6, SEC_1, SEC_2, SEC_3, SEC_4, SEC_5, NOT_APPLICABLE
    }

    public static final String MESSAGE_CONSTRAINTS = "Grades must be between Pri 1 to 6 and Sec 1 to 5";

    public final Level value;

    /**
     * Constructs a {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        this.value = checkGrade(grade);
    }

    /**
     * Returns the grade of a person.
     *
     * @param input Role that the user has input.
     * @return The type of role of a person.
     */
    public static Level checkGrade(String input) {
        if (input.length() != 5 && !input.equalsIgnoreCase("Not Applicable")) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        if (input.substring(0, 3).equalsIgnoreCase("Pri")) {
            switch (input.charAt(4)) {
            case ('1'):
                return Level.PRI_1;
            case ('2'):
                return Level.PRI_2;
            case ('3'):
                return Level.PRI_3;
            case ('4'):
                return Level.PRI_4;
            case ('5'):
                return Level.PRI_5;
            case ('6'):
                return Level.PRI_6;
            default:
                throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
            }
        } else if (input.substring(0, 3).equalsIgnoreCase("Sec")) {
            switch (input.charAt(4)) {
            case ('1'):
                return Level.SEC_1;
            case ('2'):
                return Level.SEC_2;
            case ('3'):
                return Level.SEC_3;
            case ('4'):
                return Level.SEC_4;
            case ('5'):
                return Level.SEC_5;
            default:
                throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
            }
        } else {
            return Level.NOT_APPLICABLE;
        }
    }

    /**
     * Checks if a String input is a valid grade.
     *
     * @param input The user input.
     * @return True if the input is a valid grade, and false otherwise.
     */
    public static boolean isValidGrade(String input) {
        try {
            checkGrade(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        if (value.equals(Level.NOT_APPLICABLE)) {
            return "Not applicable";
        }

        String grade = value.name();
        return grade.substring(0, 3) + " " + grade.substring(4);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Grade)) {
            return false;
        }

        Grade otherGrade = (Grade) other;
        return value.equals(otherGrade.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
