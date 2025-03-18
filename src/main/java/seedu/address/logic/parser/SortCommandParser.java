package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

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
            // Default to sorting by name
            return new SortCommand("name", true);
        }
        String[] parts = trimmedArgs.split("\\s+", 2);
        if (!parts[0].startsWith("by/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        String field = parts[0].substring(3).toLowerCase(); // Remove "by/" prefix
        // Determine sort field
        if (!field.equals("name") && !field.equals("date")) {
            throw new ParseException("Invalid sorting field. Please use 'name' or 'date' only.");
        }
        // Determine sort order (default to ascending if not specified)
        boolean isAscending = true;
        if (parts.length > 1) {
            String order = parts[1].toLowerCase();
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
