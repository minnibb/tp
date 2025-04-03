package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            // Default to sorting by name ascending if no arguments provided
            return new SortCommand("name", true);
        }
        // Parse the format: by [FIELD] [ORDER]
        String[] parts = trimmedArgs.split("\\s+");
        if (parts.length < 2 || !parts[0].equals("by")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        // Extract the field
        String field = parts[1].toLowerCase();
        // Determine sort field
        if (!field.equals("name") && !field.equals("date")) {
            throw new ParseException("Invalid sorting field. Please use 'name' or 'date' only.");
        }
        // Determine sort order (default to ascending if not specified)
        boolean isAscending = true;
        if (parts.length > 2) {
            String order = parts[2].toLowerCase();
            if (order.equals("asc")) {
                isAscending = true;
            } else if (order.equals("desc")) {
                isAscending = false;
            } else {
                throw new ParseException("Invalid sorting order. Use 'asc' for ascending or 'desc' for descending.");
            }
        }
        return new SortCommand(field, isAscending);
    }
}
