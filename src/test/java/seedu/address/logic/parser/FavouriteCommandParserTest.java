package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavouriteCommand;




public class FavouriteCommandParserTest {

    private final FavouriteCommandParser parser = new FavouriteCommandParser();

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE);

    @Test
    public void parse_validIndex_returnsFavouriteCommand() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = Integer.toString(targetIndex.getOneBased());
        FavouriteCommand expectedCommand = new FavouriteCommand(targetIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as index
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }
}
