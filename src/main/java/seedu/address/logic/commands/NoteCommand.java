package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a note to an existing person in the address book.
 */
public class NoteCommand extends Command {
    public static final String COMMAND_WORD = "note";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NOTE + "NOTE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NOTE + "Needs extra help with english";
    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Person: %1$s";
    private final Index index;
    private final String note;

    /**
     * @param index of the person in the filtered person list to add note to
     * @param note the note to add
     */
    public NoteCommand(Index index, String note) {
        requireNonNull(index);
        requireNonNull(note);
        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToNote = lastShownList.get(index.getZeroBased());
        Person updatedPerson = personToNote.withNotes(note);
        model.setPerson(personToNote, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_NOTE_SUCCESS, Messages.format(updatedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }
        NoteCommand otherNoteCommand = (NoteCommand) other;
        return index.equals(otherNoteCommand.index)
                && note.equals(otherNoteCommand.note);
    }
}
