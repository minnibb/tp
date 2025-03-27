package seedu.address.logic.parser;


import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GroupCommand;

public class GroupCommandParserTest {
    private final GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_validRole_success() throws Exception {
        assertParseSuccess(parser, " by ROLE Student",
                new GroupCommand("ROLE", "Student"));
    }

    @Test
    public void parse_missingSpaceAfterBy_throwsException() {
        assertParseFailure(parser, "byrole",
                "Missing space after 'by'. Usage: group by ROLE");
    }

    @Test
    public void parse_favouriteWithCriteria_throwsException() {
        assertParseFailure(parser, " by FAVOURITE 123",
                "Error: FAVOURITE doesn't requires a specified criteria.");
    }

    @Test
    public void parse_classWithoutCriteria_throwsException() {
        assertParseFailure(parser, " by CLASS",
                "Error: CLASS requires a specified criteria.");
    }
    @Test
    public void parse_missingCategory_throwsParseException() {
        assertParseFailure(parser, "by",
                "Missing space after 'by'. Usage: group by ROLE");
    }


    @Test
    public void parse_gradeWithoutCriteria_throwsParseException() {
        assertParseFailure(parser, "by GRADE",
                "Error: GRADE requires a specified criteria.");
    }

    @Test
    public void parse_favouriteWithCriteria_throwsParseException() {
        assertParseFailure(parser, "by favourite A",
                "Error: " + "FAVOURITE" + " doesn't requires a specified criteria.");
    }
}
