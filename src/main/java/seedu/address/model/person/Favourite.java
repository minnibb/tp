package seedu.address.model.person;

/**
 * Represents a Favourite status in the address book.
 */
public class Favourite {

    private final boolean isFavourite;

    /**
     * Constructs a {@code Favourite} instance.
     *
     * @param isFavourite A boolean value.
     */
    public Favourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    /**
     * Returns whether this person is marked as a favourite.
     */
    public boolean isFavourite() {
        return isFavourite;
    }

    /**
     * Toggles the favourite status.
     */
    public Favourite toggle() {
        return new Favourite(!isFavourite);
    }

    @Override
    public String toString() {
        return isFavourite ? "Favourite" : "Not applicable";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Favourite)) {
            return false;
        }
        Favourite otherFavourite = (Favourite) other;
        return isFavourite == otherFavourite.isFavourite;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isFavourite);
    }

}
