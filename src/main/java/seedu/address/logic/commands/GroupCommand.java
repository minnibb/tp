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
 * Groups contacts by specified group criteria (Student/Parent/Staff/Favourite).
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Groups contacts by specified role or favourite status.\n"
            + "Format: group by ROLE\n"
            + "Valid roles: Student, Parent, Staff\n"
            + "Valid groupings: Favourite\n"
            + "Example: " + COMMAND_WORD + " by Student"
            + "Example: " + COMMAND_WORD + "by Favourite";

    public static final String MESSAGE_SUCCESS = "Results are grouped by: %1$s\n"
            + "%2$d contacts found.";
    public static final String MESSAGE_NO_RESULTS = "No contacts found for specified group criteria.";
    public static final String MESSAGE_INVALID_GROUP = "Invalid group, please use Parent, Student or Staff,"
            + " or Favourite.";
    public static final String FAVOURITE = "favourite";

    private final String groupCriteria;

    public GroupCommand(String groupCriteria) {
        this.groupCriteria = groupCriteria;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        if (!isValidGroup(groupCriteria)) {
            throw new CommandException(MESSAGE_INVALID_GROUP);
        }

        List<Person> filteredList;

        if (groupCriteria.equalsIgnoreCase(FAVOURITE)) {
            // Group by favourite status
            filteredList = model.getFilteredPersonList().stream()
                    .filter(person -> person.getFavourite() != null && person.getFavourite().isFavourite())
                    .collect(Collectors.toList());
        } else if (Role.isValidRole(groupCriteria)) {
            filteredList = model.getFilteredPersonList().stream()
                    .filter(person -> person.getRole().equals(new Role(groupCriteria)))
                    .collect(Collectors.toList());
        } else {
            throw new CommandException(MESSAGE_INVALID_GROUP);
        }

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RESULTS);
        }

        model.updateFilteredPersonList(this::isMatchingGroup);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, groupCriteria, filteredList.size()));
    }

    /**
     * Checks if the person matches the group criteria (role or favourite).
     */
    public boolean isMatchingGroup(Person person) {
        if (groupCriteria.equalsIgnoreCase(FAVOURITE)) {
            return person.getFavourite() != null && person.getFavourite().isFavourite();
        } else if (Role.isValidRole(groupCriteria)) {
            return person.getRole().equals(new Role(groupCriteria));
        }
        return false;
    }

    /**
     * Checks if the group criteria is valid.
     */
    public boolean isValidGroup(String groupCriteria) {
        return Role.isValidRole(groupCriteria) || groupCriteria.equalsIgnoreCase(FAVOURITE);
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
        return groupCriteria.equals(otherGroupCommand.groupCriteria);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupCriteria", groupCriteria)
                .toString();
    }
}
