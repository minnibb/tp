package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by name in ascending or descending order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in the address book "
            + "by name in the specified order.\n"
            + "Parameters: [asc/desc] (optional - default is ascending)\n"
            + "Example: " + COMMAND_WORD + " desc";
            
    public static final String MESSAGE_SUCCESS_ASC = "Sorted all persons by name in ascending order";
    public static final String MESSAGE_SUCCESS_DESC = "Sorted all persons by name in descending order";
    
    private boolean isAscending;

    /**
     * Creates a SortCommand with the specified order.
     */
    public SortCommand(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        
        try {
            // Get current contacts
            List<Person> persons = new ArrayList<>(model.getFilteredPersonList());
            
            // Skip if empty
            if (persons.isEmpty()) {
                return new CommandResult("No contacts to sort.");
            }
            
            // Create comparator
            Comparator<Person> comparator = Comparator.comparing(person -> 
                    person.getName().toString().toLowerCase());
            
            // Reverse if descending order is requested
            if (!isAscending) {
                comparator = comparator.reversed();
            }
            
            // Sort the list
            persons.sort(comparator);
            
            // Create a new AddressBook with the sorted list
            AddressBook newAddressBook = new AddressBook();
            for (Person person : persons) {
                newAddressBook.addPerson(person);
            }
            
            // Replace the address book
            model.setAddressBook(newAddressBook);
            
            // Return success message based on sort order
            return new CommandResult(isAscending ? MESSAGE_SUCCESS_ASC : MESSAGE_SUCCESS_DESC);
            
        } catch (Exception e) {
            return new CommandResult("Error sorting: " + e.getMessage());
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
        return isAscending == otherSortCommand.isAscending;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isAscending", isAscending)
                .toString();
    }
}
