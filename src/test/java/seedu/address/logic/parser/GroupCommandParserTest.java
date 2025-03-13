package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GroupCommand;

public class GroupCommandParserTest {

    private static final String VALID_ROLE_STUDENT = "Student";
    private static final String VALID_ROLE_PARENT = "Parent";
    private static final String VALID_ROLE_STAFF = "Staff";

    private final GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_validInput_success() {
        assertParseSuccess(parser, "  by   " + VALID_ROLE_PARENT + "  ",
                new GroupCommand(VALID_ROLE_PARENT));

    }

    @Test
    public void parse_missingByClause_failure() {
        assertParseFailure(parser, VALID_ROLE_STUDENT,
                "Invalid format. Usage: group by ROLE");

        assertParseFailure(parser, "b " + VALID_ROLE_STUDENT,
                "Invalid format. Usage: group by ROLE");
    }


    @Test
    public void parse_emptyRole_failure() {
        assertParseFailure(parser, "by",
                "Missing space after 'by'. Usage: group by ROLE");

    }

    @Test
    public void parse_extraParameters_failure() {
        assertParseFailure(parser, "by Student 123",
                "Error: Only one role can be selected at a time.");

        assertParseFailure(parser, "by Student by Parent",
                "Error: Only one role can be selected at a time.");
    }

    @Test
    public void parse_caseSensitivityTest() {
        assertParseSuccess(parser, "by student",
                new GroupCommand("student"));

        assertParseSuccess(parser, "by StUdEnT",
                new GroupCommand("StUdEnT"));
    }
}
