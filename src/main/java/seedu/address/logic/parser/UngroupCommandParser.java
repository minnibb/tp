// UngroupCommandParser.java
package seedu.address.logic.parser;

import seedu.address.logic.commands.UngroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UngroupCommand object
 */
public class UngroupCommandParser implements Parser<UngroupCommand> {

    @Override
    public UngroupCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(
                    String.format("Ungroup command doesn't accept parameters.\nUsage: %s", UngroupCommand.COMMAND_WORD)
            );
        }
        return new UngroupCommand();
    }
}
