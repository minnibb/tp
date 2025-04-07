package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Class;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Groups contacts by specified group criteria (Student/Parent/Staff/Favourite).
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Groups contacts from the FULL LIST by specified category.\n"
            + "Format: group by ROLE/CLASS/GRADE/FAVOURITE [CRITERIA]\n"
            + "- ROLE requires: Student, Parent, Staff\n"
            + "- CLASS accepts any non-empty value\n"
            + "- GRADE requires: pri 1~6/sec 1~5\n"
            + "- FAVOURITE needs no criteria\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " by ROLE Student\n"
            + "  " + COMMAND_WORD + " by CLASS 1A\n"
            + "  " + COMMAND_WORD + " by GRADE sec 1\n"
            + "  " + COMMAND_WORD + " by FAVOURITE";

    public static final String MESSAGE_SUCCESS = "Results are grouped by: %1$s %2$s\n"
            + "%3$d contacts found.\n";

    public static final String MESSAGE_NO_RESULTS = "No contacts found for specified group criteria.";

    public static final String MESSAGE_INVALID_GROUP = "Invalid group.\n"
            + "Allowed categories: ROLE, CLASS, GRADE, FAVOURITE\n"
            + "Criteria rules:\n"
            + "- ROLE: Student, Parent, Staff (case-insensitive)\n"
            + "- CLASS: Any non-empty text (e.g. 2B, ScienceClub)\n"
            + "- GRADE: pri 1~6/sec 1~5 (e.g. 'pri 3', 'sec 4')\n"
            + "- FAVOURITE: No criteria needed\n"
            + "only one category and one criteria can be selected";

    public static final String FAVOURITE = "favourite";
    private final String category;

    private final String criteria;

    /**
     * @param category
     * @param criteria
     */
    public GroupCommand(String category, String criteria) {
        this.category = category.toUpperCase();
        this.criteria = criteria;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        List<Person> filteredList;

        if (category.equalsIgnoreCase(FAVOURITE)) {
            // Group by favourite status
            filteredList = model.getFilteredPersonList().stream()
                    .filter(person -> person.getFavourite() != null && person.getFavourite().isFavourite())
                    .collect(Collectors.toList());
        } else if (category.equalsIgnoreCase("ROLE") && Role.isValidRole(criteria)) {
            filteredList = model.getFilteredPersonList().stream()
                    .filter(person -> person.getRole().equals(new Role(criteria)))
                    .collect(Collectors.toList());
        } else if (category.equalsIgnoreCase("CLASS") && Class.isValidClass(criteria)) {
            filteredList = model.getFilteredPersonList().stream()
                    .filter(person -> person.getStudentClass() != null
                            && person.getStudentClass().equals(new Class(criteria)))
                    .collect(Collectors.toList());
        } else if (category.equalsIgnoreCase("GRADE") && Grade.isValidGrade(criteria)) {
            filteredList = model.getFilteredPersonList().stream()
                    .filter(person -> person.getGrade() != null && person.getGrade().equals(new Grade(criteria)))
                    .collect(Collectors.toList());

        } else {
            throw new CommandException(MESSAGE_INVALID_GROUP);
        }

        model.updateFilteredPersonList(this::isMatchingGroup);
        filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RESULTS);
        }


        return new CommandResult(
                String.format(MESSAGE_SUCCESS, category, criteria.isEmpty() ? "" : criteria, filteredList.size()));
    }

    /**
     * Checks if the person matches the group criteria (role or favourite).
     */
    public boolean isMatchingGroup(Person person) {

        switch (category) {

        case "FAVOURITE": return person.getFavourite() != null && person.getFavourite().isFavourite();
        case "ROLE": return Role.isValidRole(criteria) && person.getRole().equals(new Role(criteria));

        case "CLASS": return person.getStudentClass() != null
                && Class.isValidClass(criteria) && person.getStudentClass().equals(new Class(criteria));
        case "GRADE": return person.getGrade() != null
                && Grade.isValidGrade(criteria) && person.getGrade().equals(new Grade(criteria));
        default: return false;
        }
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
        return category.equals(otherGroupCommand.category) && criteria.equals(otherGroupCommand.criteria);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("category", category)
                .add("criteria", criteria)
                .toString();
    }
}
