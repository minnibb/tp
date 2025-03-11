package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
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
            // Print debug info
            System.out.println("Before sorting - first person: " + 
                (model.getFilteredPersonList().isEmpty() ? "none" : 
                 model.getFilteredPersonList().get(0).getName()));
            
            // Sort by name in ascending order
            model.updateSortedPersonList(Comparator.comparing(person -> 
                    person.getName().toString().toLowerCase()));
            
            // Force UI refresh by resetting the predicate
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            
            // Print debug info again
            System.out.println("After sorting - first person: " + 
                (model.getFilteredPersonList().isEmpty() ? "none" : 
                 model.getFilteredPersonList().get(0).getName()));
                    
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
