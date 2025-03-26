package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class FavouriteTest {

    @Test
    public void constructor_initializesCorrectly() {
        Favourite favourite = new Favourite(true);
        Favourite notFavourite = new Favourite(false);

        assertTrue(favourite.isFavourite());
        assertFalse(notFavourite.isFavourite());
    }

    @Test
    public void toggle_changesState() {
        Favourite favourite = new Favourite(true);
        Favourite notFavourite = new Favourite(false);

        // Toggling true -> false
        assertFalse(favourite.toggle().isFavourite());
        // Toggling false -> true
        assertTrue(notFavourite.toggle().isFavourite());
    }

    @Test
    public void toString_correctOutput() {
        Favourite favourite = new Favourite(true);
        Favourite notFavourite = new Favourite(false);

        assertEquals("Favourite", favourite.toString());
        assertEquals("Not applicable", notFavourite.toString());
    }

    @Test
    public void equals_sameValuesAreEqual() {
        Favourite favourite1 = new Favourite(true);
        Favourite favourite2 = new Favourite(true);
        Favourite notFavourite1 = new Favourite(false);
        Favourite notFavourite2 = new Favourite(false);

        assertTrue(favourite1.equals(favourite2));
        assertTrue(notFavourite1.equals(notFavourite2));
        assertFalse(favourite1.equals(notFavourite1));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Favourite favourite = new Favourite(false);
        String otherObject = "Not a Favourite";
        assertFalse(favourite.equals(otherObject));
    }

    @Test
    public void hashCode_differentIsFavourite_returnsDifferentHashCode() {
        // Test the hashCode method with different isFavourite values
        Favourite favourite1 = new Favourite(true);
        Favourite favourite2 = new Favourite(false);
        assertNotEquals(favourite1.hashCode(), favourite2.hashCode());
    }

}
