package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    @Test
    public void execute_emptyList_success() {
        ModelStubWithEmptyAddressBook modelStub = new ModelStubWithEmptyAddressBook();
        SortCommand sortCommand = new SortCommand(true);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals("No contacts to sort.", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonEmptyListAscending_success() {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        SortCommand sortCommand = new SortCommand(true);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_SUCCESS_ASC, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonEmptyListDescending_success() {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        SortCommand sortCommand = new SortCommand(false);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_SUCCESS_DESC, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        SortCommand ascendingCommand = new SortCommand(true);
        SortCommand descendingCommand = new SortCommand(false);

        // same object -> returns true
        assertTrue(ascendingCommand.equals(ascendingCommand));

        // same values -> returns true
        SortCommand ascendingCommandCopy = new SortCommand(true);
        assertTrue(ascendingCommand.equals(ascendingCommandCopy));

        // different types -> returns false
        assertFalse(ascendingCommand.equals(1));

        // null -> returns false
        assertFalse(ascendingCommand.equals(null));

        // different direction -> returns false
        assertFalse(ascendingCommand.equals(descendingCommand));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand(true);
        String expected = "SortCommand{isAscending=true}";
        assertTrue(sortCommand.toString().contains("isAscending=true"));
    }

    /**
     * A Model stub with an empty address book.
     */
    private class ModelStubWithEmptyAddressBook extends ModelStub {
        private final AddressBook addressBook = new AddressBook();

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            requireNonNull(addressBook);
        }
    }

    /**
     * A Model stub with a few persons.
     */
    private class ModelStubWithPersons extends ModelStub {
        private final AddressBook addressBook = new AddressBook();
        private final ObservableList<Person> filteredPersons;

        ModelStubWithPersons() {
            Person alice = new PersonBuilder().withName("Alice").build();
            Person bob = new PersonBuilder().withName("Bob").build();
            Person charlie = new PersonBuilder().withName("Charlie").build();
            addressBook.addPerson(bob);
            addressBook.addPerson(charlie);
            addressBook.addPerson(alice);
            filteredPersons = FXCollections.observableArrayList(bob, charlie, alice);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            requireNonNull(addressBook);
        }
    }

    /**
     * A default model stub that has all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
