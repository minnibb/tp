package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Groups contacts by specified role (Student/Parent/Staff).
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Groups contacts by specified role.\n"
            + "Format: group by ROLE\n"
            + "Valid roles: Student, Parent, Staff\n"
            + "Example: " + COMMAND_WORD + " by Student";

    public static final String MESSAGE_SUCCESS = "Results are grouped by: %1$s\n"
            + "%2$d contacts found.";
    public static final String MESSAGE_NO_RESULTS = "No contacts found for specified role.";
    public static final String MESSAGE_INVALID_ROLE = "Invalid role, please use Parent, Student or Staff.";

    private final String role;

    public GroupCommand(String role) {
        this.role = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        if (!Role.isValidRole(role)) {
            throw new CommandException(MESSAGE_INVALID_ROLE);
        }

        List<Person> filteredList = model.getFilteredPersonList().stream()
                .filter(person -> person.getRole().equals(new Role(role)))
                .collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RESULTS);
        }

        model.updateFilteredPersonList(person -> person.getRole().equals(new Role(role)));

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, role, filteredList.size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCommand)) {
            return false;
        }

        GroupCommand otherGroupCommand = (GroupCommand) other;
        return role.equals(otherGroupCommand.role);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("role", role)
                .toString();
    }
}
