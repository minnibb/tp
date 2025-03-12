package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPersonToDelete_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameToDelete = personToDelete.getName();
        Phone phoneToDelete = personToDelete.getPhone();

        // check that the person to be deleted is in the list
        assertTrue(model.hasPerson(personToDelete));

        DeleteCommand deleteCommand = new DeleteCommand(nameToDelete, phoneToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        // check that the command has successfully been executed
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotInList_throwsCommandException() {
        Person personToDelete = new PersonBuilder(AMY).build();
        Name nameToDelete = personToDelete.getName();
        Phone phoneToDelete = personToDelete.getPhone();

        // check that the person to be deleted is not in the list
        assertFalse(model.hasPerson(personToDelete));

        DeleteCommand deleteCommand = new DeleteCommand(nameToDelete, phoneToDelete);

        // check that the command is not executed
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME_AND_PHONE);
    }

    @Test
    public void execute_nameNotInList_throwsCommandException() {
        Person personInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Phone phoneInList = personInList.getPhone();
        Email emailInList = personInList.getEmail();
        Address addressInList = personInList.getAddress();
        Person personToDelete = new PersonBuilder()
                .withPhone(phoneInList.toString())
                .withEmail(emailInList.toString())
                .withAddress(addressInList.toString())
                .build();

        // check that the person to be deleted is not in the list
        assertFalse(model.hasPerson(personToDelete));

        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName(), personToDelete.getPhone());

        // check that the command is not executed
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME_AND_PHONE);
    }

    @Test
    public void execute_phoneNumberNotInList_throwsCommandException() {
        Person personInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameInList = personInList.getName();
        Email emailInList = personInList.getEmail();
        Address addressInList = personInList.getAddress();
        Person personToDelete = new PersonBuilder()
                .withName(nameInList.toString())
                .withEmail(emailInList.toString())
                .withAddress(addressInList.toString())
                .build();

        // check that the person to be deleted is not in the list
        assertFalse(model.hasPerson(personToDelete));

        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName(), personToDelete.getPhone());

        // check that the command is not executed
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME_AND_PHONE);
    }

    @Test
    public void equals() {
        Person expectedFirstPerson = new PersonBuilder(ALICE).build();
        Person expectedSecondPerson = new PersonBuilder(BOB).build();
        DeleteCommand deleteFirstCommand = new DeleteCommand(expectedFirstPerson.getName(),
                expectedFirstPerson.getPhone());
        DeleteCommand deleteSecondCommand = new DeleteCommand(expectedSecondPerson.getName(),
                expectedSecondPerson.getPhone());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(expectedFirstPerson.getName(),
                expectedFirstPerson.getPhone());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Person expectedPerson = new PersonBuilder(BOB).build();
        Name targetName = expectedPerson.getName();
        Phone targetPhone = expectedPerson.getPhone();
        DeleteCommand deleteCommand = new DeleteCommand(targetName, targetPhone);
        String expected = DeleteCommand.class.getCanonicalName()
                + "{targetName=" + targetName + ", targetPhone=" + targetPhone + "}";
        assertEquals(expected, deleteCommand.toString());
    }

}
