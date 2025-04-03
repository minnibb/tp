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
                throw new ParseException("Missing space after 'by'. Usage: group by ROLE/CLASS/GRADE/FAVOURITE [specific criteria]");
            }
            throw new ParseException("Invalid format. Usage: group by ROLE/CLASS/GRADE/FAVOURITE [specific criteria]");
        }

        String content = trimmedArgs.substring(3).trim();
        String[] parts = content.split(" ", 2);


        String category = parts[0].toUpperCase();


        String criteria = (parts.length == 2) ? parts[1].trim() : "";

        if (!category.equals("FAVOURITE") && criteria.isEmpty()) {
            throw new ParseException("Error: " + category + " requires a specified criteria.");
        }
        if (category.equals("FAVOURITE") && !criteria.isEmpty()) {
            throw new ParseException("Error: " + category + " doesn't requires a specified criteria.");
        }

        return new GroupCommand(category, criteria);
    }
}
