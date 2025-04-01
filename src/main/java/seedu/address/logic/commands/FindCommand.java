package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsSubstringPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive and partial match.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons by either name or phone number.\n"
            + "Search by name (case-insensitive, partial match allowed):\n"
            + "  Example: " + COMMAND_WORD + " alice \n"
            + "Search by phone number (partial match allowed):\n"
            + "  Example: " + COMMAND_WORD + " 91234567\n";

    private final Predicate<Person> predicate;

    /**
     * Constructs a FindCommand that searches by name.
     * @param nameKeywords A list of keywords to search for in names.
     */
    public FindCommand(List<String> nameKeywords) {
        requireNonNull(nameKeywords);
        this.predicate = new NameContainsKeywordsPredicate(nameKeywords);
    }

    /**
     * Constructs a FindCommand that searches by phone number.
     * @param phoneKeyword A substring to search for in phone numbers.
     */
    public FindCommand(String phoneKeyword) {
        requireNonNull(phoneKeyword);
        this.predicate = new PhoneContainsSubstringPredicate(phoneKeyword);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (predicate instanceof NameContainsKeywordsPredicate namePredicate) {
            if (!namePredicate.isValid()) {
                return new CommandResult(Messages.MESSAGE_INVALID_NAME);
            }
        }

        model.updateFilteredPersonList(predicate);

        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(Messages.MESSAGE_NO_RESULTS);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
