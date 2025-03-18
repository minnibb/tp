package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UngroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UngroupCommandParserTest {
    private final UngroupCommandParser parser = new UngroupCommandParser();

    @Test
    public void parse_validEmptyArgs_returnsCommand() throws ParseException {
        assertParseSuccess(parser, "", new UngroupCommand());

        assertParseSuccess(parser, "   ", new UngroupCommand());
    }

    @Test
    public void parse_invalidArgs_throwsException() {
        assertParseFailure(
                parser,
                "extra",
                "Ungroup command doesn't accept parameters.\nUsage: ungroup"
        );

        assertParseFailure(
                parser,
                "@",
                "Ungroup command doesn't accept parameters.\nUsage: ungroup"
        );
    }
}
