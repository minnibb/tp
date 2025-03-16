package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClassTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Class(null));
    }

    @Test
    public void constructor_invalidClass_throwsIllegalArgumentException() {
        String invalidClass = "";
        assertThrows(IllegalArgumentException.class, () -> new Class(invalidClass));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Class.isValidClass(null));

        // invalid name
        assertFalse(Class.isValidClass("")); // empty string
        assertFalse(Class.isValidClass(" ")); // spaces only
        assertFalse(Class.isValidClass(" 1A")); // start with space

        // valid name
        assertTrue(Class.isValidClass("Class A")); // alphabets only
        assertTrue(Class.isValidClass("1-2")); // numbers and symbols
        assertTrue(Class.isValidClass("1A")); // alphanumeric characters
        assertTrue(Class.isValidClass("CLASS A")); // with capital letters
        assertTrue(Class.isValidClass("Class 1A in 2025")); // long class name
    }

    @Test
    public void equals() {
        Class studentClass = new Class("3B");

        // same values -> returns true
        assertTrue(studentClass.equals(new Class("3B")));

        // same object -> returns true
        assertTrue(studentClass.equals(studentClass));

        // null -> returns false
        assertFalse(studentClass.equals(null));

        // different types -> returns false
        assertFalse(studentClass.equals(5.0f));

        // different values -> returns false
        assertFalse(studentClass.equals(new Name("3C")));
    }
}
