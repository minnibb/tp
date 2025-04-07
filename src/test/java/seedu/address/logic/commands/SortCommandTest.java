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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    @Test
    public void execute_emptyList_success() throws CommandException {
        ModelStubWithEmptyAddressBook modelStub = new ModelStubWithEmptyAddressBook();
        SortCommand sortCommand = new SortCommand("name", true);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_EMPTY_LIST, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonEmptyListNameAscending_success() throws CommandException {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        SortCommand sortCommand = new SortCommand("name", true);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_SUCCESS_NAME_ASC, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonEmptyListNameDescending_success() throws CommandException {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        SortCommand sortCommand = new SortCommand("name", false);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_SUCCESS_NAME_DESC, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonEmptyListDateAscending_success() throws CommandException {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        SortCommand sortCommand = new SortCommand("date", true);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_SUCCESS_DATE_ASC, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_nonEmptyListDateDescending_success() throws CommandException {
        ModelStubWithPersons modelStub = new ModelStubWithPersons();
        SortCommand sortCommand = new SortCommand("date", false);

        CommandResult commandResult = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_SUCCESS_DATE_DESC, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        SortCommand nameAscCommand = new SortCommand("name", true);
        SortCommand nameDescCommand = new SortCommand("name", false);
        SortCommand dateAscCommand = new SortCommand("date", true);
        SortCommand dateDescCommand = new SortCommand("date", false);

        // same object -> returns true
        assertTrue(nameAscCommand.equals(nameAscCommand));

        // same values -> returns true
        SortCommand nameAscCommandCopy = new SortCommand("name", true);
        assertTrue(nameAscCommand.equals(nameAscCommandCopy));

        // different types -> returns false
        assertFalse(nameAscCommand.equals(1));

        // null -> returns false
        assertFalse(nameAscCommand.equals(null));

        // different direction -> returns false
        assertFalse(nameAscCommand.equals(nameDescCommand));

        // different field -> returns false
        assertFalse(nameAscCommand.equals(dateAscCommand));

        // different field and direction -> returns false
        assertFalse(nameAscCommand.equals(dateDescCommand));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand("name", true);
        assertTrue(sortCommand.toString().contains("sortField=name"));
        assertTrue(sortCommand.toString().contains("isAscending=true"));
    }

    @Test
    public void execute_exceptionThrown_returnsErrorMessage() throws CommandException {
        ModelStub modelStub = new ModelStub() {
            private final Person person = new PersonBuilder().build();
            @Override
            public ObservableList<Person> getFilteredPersonList() {
                return FXCollections.observableArrayList(person);
            }
            @Override
            public ReadOnlyAddressBook getAddressBook() {
                AddressBook ab = new AddressBook();
                ab.addPerson(person);
                return ab;
            }
            @Override
            public void setAddressBook(ReadOnlyAddressBook addressBook) {
                throw new RuntimeException("Test exception");
            }
        };
        SortCommand sortCommand = new SortCommand("name", true);
        CommandResult result = sortCommand.execute(modelStub);
        assertEquals(SortCommand.MESSAGE_ERROR_PREFIX + "Test exception", result.getFeedbackToUser());
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
