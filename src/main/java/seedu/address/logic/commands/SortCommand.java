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
        
        model.updateSortedPersonList(Comparator.comparing(person -> 
                person.getName().toString().toLowerCase()));
                
        return new CommandResult(MESSAGE_SUCCESS);
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
