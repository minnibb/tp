package seedu.address.logic.parser;

import static seedu.address.commons.core.index.Index.fromOneBased;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;

public class NoteCommandParserTest {
    private static final String NOTE_TEXT = "Test note";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);
    private NoteCommandParser parser = new NoteCommandParser();
    @Test
    public void parse_validArgs_returnsNoteCommand() {
        // valid index and note
        assertParseSuccess(parser, "1 nt/Test note", new NoteCommand(INDEX_FIRST_PERSON, NOTE_TEXT));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // negative index
        assertParseFailure(parser, "-5 nt/Test note", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 nt/Test note", MESSAGE_INVALID_FORMAT);

        // invalid characters in index
        assertParseFailure(parser, "abc nt/Test note", MESSAGE_INVALID_FORMAT);

        // no index
        assertParseFailure(parser, "nt/Test note", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyNote_throwsParseException() {
        // empty note
        assertParseFailure(parser, "1 nt/", MESSAGE_INVALID_FORMAT);
        // missing prefix
        assertParseFailure(parser, "1 Test note", MESSAGE_INVALID_FORMAT);
    }
}
