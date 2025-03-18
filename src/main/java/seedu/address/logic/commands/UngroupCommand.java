// UngroupCommand.java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Resets grouping/filtering to show all persons.
 */
public class UngroupCommand extends Command {
    public static final String COMMAND_WORD = "ungroup";
    public static final String MESSAGE_SUCCESS = "Showing all contacts (no grouping).";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UngroupCommand;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
