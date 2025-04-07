package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by name or date in ascending or descending order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons saved in the ClassHive "
            + "by the specified field in the specified order.\n"
            + "Parameters: by [FIELD] [ORDER]\n"
            + "FIELD: name, date\n"
            + "ORDER: asc, desc\n"
            + "Example: " + COMMAND_WORD + " by name desc";

    public static final String MESSAGE_SUCCESS_NAME_ASC = "Sorted all persons by name in ascending order";
    public static final String MESSAGE_SUCCESS_NAME_DESC = "Sorted all persons by name in descending order";
    public static final String MESSAGE_SUCCESS_DATE_ASC = "Sorted all persons by date added in ascending order";
    public static final String MESSAGE_SUCCESS_DATE_DESC = "Sorted all persons by date added in descending order";
    public static final String MESSAGE_EMPTY_LIST = "No contacts to sort.";
    public static final String MESSAGE_ERROR_PREFIX = "Error sorting: ";
    public static final String MESSAGE_INVALID_SORT = "Sort can only be used on full contact list. "
            + "Please use the list command before using sort";

    private final String sortField;
    private final boolean isAscending;

    /**
     * Creates a SortCommand with the specified field and order.
     */
    public SortCommand(String sortField, boolean isAscending) {
        this.sortField = sortField;
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Get current contacts
        List<Person> persons = new ArrayList<>(model.getFilteredPersonList());
        if (persons.size() != model.getAddressBook().getPersonList().size()) {
            throw new CommandException(MESSAGE_INVALID_SORT);
        }
        // Skip if empty
        if (persons.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }
        // Create comparator based on sort field
        Comparator<Person> comparator;
        if (sortField.equals("name")) {
            comparator = Comparator.comparing(person ->
                    person.getName().toString().toLowerCase());
        } else { // date
            comparator = Comparator.comparing(Person::getTimeAdded);
        }
        // Reverse if descending order is requested
        if (!isAscending) {
            comparator = comparator.reversed();
        }

        try {
            // Sort the list
            persons.sort(comparator);
            // Create a new AddressBook with the sorted list
            AddressBook newAddressBook = new AddressBook();
            for (Person person : persons) {
                newAddressBook.addPerson(person);
            }
            // Replace the address book
            model.setAddressBook(newAddressBook);
            // Return success message based on sort field and order
            if (sortField.equals("name")) {
                return new CommandResult(isAscending ? MESSAGE_SUCCESS_NAME_ASC : MESSAGE_SUCCESS_NAME_DESC);
            } else { // date
                return new CommandResult(isAscending ? MESSAGE_SUCCESS_DATE_ASC : MESSAGE_SUCCESS_DATE_DESC);
            }
        } catch (RuntimeException e) {
            return new CommandResult(MESSAGE_ERROR_PREFIX + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SortCommand)) {
            return false;
        }
        SortCommand otherSortCommand = (SortCommand) other;
        return isAscending == otherSortCommand.isAscending
                && sortField.equals(otherSortCommand.sortField);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sortField", sortField)
                .add("isAscending", isAscending)
                .toString();
    }
}
