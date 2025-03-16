package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "Admin";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid name
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("admin")); // not staff, student or parent

        // valid name
        assertTrue(Role.isValidRole("student")); // no capital letters
        assertTrue(Role.isValidRole("StudENt")); // mix of capital and small letters
        assertTrue(Role.isValidRole("STUDENT")); // all capital letters
        assertTrue(Role.isValidRole("staff")); // no capital letters
        assertTrue(Role.isValidRole("StaFF")); // mix of capital and small letters
        assertTrue(Role.isValidRole("STAFF")); // all capital letters
        assertTrue(Role.isValidRole("parent")); // no capital letters
        assertTrue(Role.isValidRole("PARent")); // mix of capital and small letters
        assertTrue(Role.isValidRole("PARENT")); // all capital letters
    }

    @Test
    public void equals() {
        Role role = new Role("student");

        // same values -> returns true
        assertTrue(role.equals(new Role("student")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("staff")));
        assertFalse(role.equals(new Role("parent")));
    }
}
