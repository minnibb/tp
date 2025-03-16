package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsSubstringPredicateTest {

    @Test
    public void equals() {
        // Same keyword -> returns true
        PhoneContainsSubstringPredicate firstPredicate = new PhoneContainsSubstringPredicate("123");
        PhoneContainsSubstringPredicate secondPredicate = new PhoneContainsSubstringPredicate("123");

        assertTrue(firstPredicate.equals(secondPredicate));

        // Different keyword -> returns false
        secondPredicate = new PhoneContainsSubstringPredicate("456");
        assertFalse(firstPredicate.equals(secondPredicate));

        // Different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_phoneDoesNotContainSubstring_returnsFalse() {
        // Non-matching substring
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("678");
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Phone number does not contain the substring
        predicate = new PhoneContainsSubstringPredicate("789");
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345").build()));
    }

    @Test
    public void test_phoneContainsSubstring_returnsTrue() {
        // Exact match
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("123");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        // Substring match
        predicate = new PhoneContainsSubstringPredicate("45");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "123";
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate(keyword);

        String expected = PhoneContainsSubstringPredicate.class.getCanonicalName() + "{keywords=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
