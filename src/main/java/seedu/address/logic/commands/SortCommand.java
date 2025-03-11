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
 * Sorts all persons in the address book by name in ascending order.
 * Will support sorting by different fields and in different orders.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in the address book "
            + "by name in ascending order.\n"
            + "Example: " + COMMAND_WORD;
            
    public static final String MESSAGE_SUCCESS = "Sorted all persons by name in ascending order";

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
            
            System.out.println("Before sorting - first person: " + persons.get(0).getName());
            
            // Sort the list
            persons.sort(Comparator.comparing(person -> 
                    person.getName().toString().toLowerCase()));
                    
            System.out.println("After sorting - first person: " + persons.get(0).getName());
            
            // Create a new AddressBook with the sorted list
            AddressBook newAddressBook = new AddressBook();
            for (Person person : persons) {
                newAddressBook.addPerson(person);
            }
            
            // Replace the address book
            model.setAddressBook(newAddressBook);
            
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommandResult("Error sorting: " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this 
                || (other instanceof SortCommand); 
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
