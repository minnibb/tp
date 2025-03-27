package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

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

        String content = trimmedArgs.substring(3).trim();
        String[] parts = content.split(" ", 2);

        if (parts.length < 1) {
            throw new ParseException("Error: Missing grouping category. "
                    + "Usage: group by ROLE/CLASS/GRADE/FAVOURITE [specified_criteria]");
        }
        String category = parts[0].toUpperCase();


        List<String> validCategories = Arrays.asList("ROLE", "CLASS", "GRADE", "FAVOURITE");

        if (!validCategories.contains(category)) {
            throw new ParseException("Error: Invalid category. Allowed: ROLE, CLASS, GRADE, FAVOURITE");
        }


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
