package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class FavouriteTest {

    @Test
    public void constructor_initializesCorrectly() {
        Favourite fav = new Favourite(true);
        Favourite notFav = new Favourite(false);

        assertTrue(fav.isFavourite());
        assertFalse(notFav.isFavourite());
    }

    @Test
    public void toggle_changesState() {
        Favourite fav = new Favourite(true);
        Favourite notFav = new Favourite(false);

        // Toggling true -> false
        assertFalse(fav.toggle().isFavourite());
        // Toggling false -> true
        assertTrue(notFav.toggle().isFavourite());
    }

    @Test
    public void toString_correctOutput() {
        Favourite fav = new Favourite(true);
        Favourite notFav = new Favourite(false);

        assertEquals("Favourite", fav.toString());
        assertEquals("Not applicable", notFav.toString());
    }

    @Test
    public void equals_sameValuesAreEqual() {
        Favourite fav1 = new Favourite(true);
        Favourite fav2 = new Favourite(true);
        Favourite notFav1 = new Favourite(false);
        Favourite notFav2 = new Favourite(false);

        assertEquals(fav1, fav2);
        assertEquals(notFav1, notFav2);
        assertNotEquals(fav1, notFav1);
    }

}
