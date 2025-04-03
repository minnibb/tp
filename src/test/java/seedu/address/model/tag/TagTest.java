package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // uses equivalence partitioning

        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // valid tag name
        assertTrue(Tag.isValidTagName("boss")); // one word
        assertTrue(Tag.isValidTagName("Colleague")); // with capital letters
        assertTrue(Tag.isValidTagName("top student")); // more than one word

        // invalid tag name
        assertFalse(Tag.isValidTagName(""));
        assertFalse(Tag.isValidTagName("top student!!"));
    }

}
