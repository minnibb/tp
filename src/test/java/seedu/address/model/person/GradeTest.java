package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "Pri 7";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // null name
        assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid name
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("Pri 7")); // not within Pri 1-6
        assertFalse(Grade.isValidGrade("Sec 0")); // not within Sec 1-5

        // valid grade - primary
        assertTrue(Grade.isValidGrade("pri 1")); // no capital letters
        assertTrue(Grade.isValidGrade("Pri 1")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("PRI 1")); // all capital letters
        assertTrue(Grade.isValidGrade("pri 2")); // no capital letters
        assertTrue(Grade.isValidGrade("Pri 2")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("PRI 2")); // all capital letters
        assertTrue(Grade.isValidGrade("pri 3")); // no capital letters
        assertTrue(Grade.isValidGrade("Pri 3")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("PRI 3")); // all capital letters
        assertTrue(Grade.isValidGrade("pri 4")); // no capital letters
        assertTrue(Grade.isValidGrade("Pri 4")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("PRI 4")); // all capital letters
        assertTrue(Grade.isValidGrade("pri 5")); // no capital letters
        assertTrue(Grade.isValidGrade("Pri 5")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("PRI 5")); // all capital letters
        assertTrue(Grade.isValidGrade("pri 6")); // no capital letters
        assertTrue(Grade.isValidGrade("Pri 6")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("PRI 6")); // all capital letters

        // valid grade - secondary
        assertTrue(Grade.isValidGrade("sec 1")); // no capital letters
        assertTrue(Grade.isValidGrade("Sec 1")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("SEC 1")); // all capital letters
        assertTrue(Grade.isValidGrade("sec 2")); // no capital letters
        assertTrue(Grade.isValidGrade("Sec 2")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("SEC 2")); // all capital letters
        assertTrue(Grade.isValidGrade("sec 3")); // no capital letters
        assertTrue(Grade.isValidGrade("Sec 3")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("SEC 3")); // all capital letters
        assertTrue(Grade.isValidGrade("sec 4")); // no capital letters
        assertTrue(Grade.isValidGrade("Sec 4")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("SEC 4")); // all capital letters
        assertTrue(Grade.isValidGrade("sec 5")); // no capital letters
        assertTrue(Grade.isValidGrade("Sec 5")); // mix of capital and small letters
        assertTrue(Grade.isValidGrade("SEC 5")); // all capital letters
    }

    @Test
    public void equals() {
        Grade grade = new Grade("Pri 4");

        // same values -> returns true
        assertTrue(grade.equals(new Grade("Pri 4")));

        // same object -> returns true
        assertTrue(grade.equals(grade));

        // null -> returns false
        assertFalse(grade.equals(null));

        // different types -> returns false
        assertFalse(grade.equals(5.0f));

        // different values -> returns false
        assertFalse(grade.equals(new Grade("Pri 3")));
        assertFalse(grade.equals(new Grade("Sec 3")));
    }
}
