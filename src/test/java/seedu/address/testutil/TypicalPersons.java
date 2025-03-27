package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHILD_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHILD_PHONE_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAVOURITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAVOURITE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAVOURITE_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withRole("Parent")
            .withGrade("Sec 3")
            .withClass("3K")
            .withRelativeName("Elle Meyer")
            .withRelativePhone("9482224")
            .withFavourite(false).build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withRole("Student")
            .withGrade("Sec 2")
            .withClass("2D")
            .withRelativeName("Daniel Meier")
            .withRelativePhone("87652533")
            .withFavourite(false).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withRole("Staff")
            .withFavourite(false).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withRole("Parent").withGrade("Sec 2").withClass("2D")
            .withRelativeName("Benson Meier")
            .withRelativePhone("98765432")
            .withFavourite(true).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withRole("Student")
            .withGrade("Pri 3")
            .withClass("3K")
            .withRelativeName("Alice Pauline")
            .withRelativePhone("94351253")
            .withFavourite(false).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withRole("Staff")
            .withFavourite(false).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withRole("Parent").withGrade("Sec 2").withClass("2D")
            .withRelativeName("Mason Best")
            .withRelativePhone("98769532")
            .withFavourite(false).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india")
            .withRole("Staff")
            .withFavourite(true).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withRole("Staff")
            .withFavourite(true).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withRole(VALID_ROLE_AMY).withGrade(VALID_GRADE_AMY).withClass(VALID_CLASS_AMY)
            .withRelativeName(VALID_PARENT_AMY).withRelativePhone(VALID_PARENT_PHONE_AMY)
            .withFavourite(VALID_FAVOURITE_AMY).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withRole(VALID_ROLE_BOB).withFavourite(VALID_FAVOURITE_BOB)
            .build();

    public static final Person CHARLES = new PersonBuilder().withName(VALID_NAME_CHARLES).withPhone(VALID_PHONE_CHARLES)
            .withEmail(VALID_EMAIL_CHARLES).withAddress(VALID_ADDRESS_CHARLES)
            .withRole(VALID_ROLE_CHARLES).withGrade(VALID_GRADE_AMY).withClass(VALID_CLASS_AMY)
            .withRelativeName(VALID_CHILD_CHARLES).withRelativePhone(VALID_CHILD_PHONE_CHARLES)
            .withFavourite(VALID_FAVOURITE_CHARLES).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
