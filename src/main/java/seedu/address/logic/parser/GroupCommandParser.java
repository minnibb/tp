package seedu.address.logic.parser;


import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * parse Group command
 */
public class GroupCommandParser implements Parser<GroupCommand> {

    /**
     * @param args
     * @return GroupCommand
     * @throws ParseException
     */
    @Override
    public GroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();


        if (!trimmedArgs.startsWith("by ")) {
            if (trimmedArgs.startsWith("by")) {
                throw new ParseException("Missing space after 'by'. Usage: group by ROLE");
            }
            throw new ParseException("Invalid format. Usage: group by ROLE");
        }

        String role = trimmedArgs.substring(3).trim();

        if (role.contains(" ")) {
            throw new ParseException("Error: Only one role can be selected at a time.");
        }

        return new GroupCommand(role);
    }
}
