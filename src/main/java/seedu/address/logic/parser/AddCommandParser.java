package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIVE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIVE_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Class;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_ROLE, PREFIX_GRADE, PREFIX_CLASS, PREFIX_RELATIVE_NAME, PREFIX_RELATIVE_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ROLE,
                PREFIX_GRADE, PREFIX_CLASS, PREFIX_RELATIVE_NAME, PREFIX_RELATIVE_PHONE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()); // checks if the role is valid here

        Favourite favourite = new Favourite(false);

        if (!role.getType().equals(Role.Type.STAFF)) {
            return parseNonStaff(argMultimap, name, phone, email, address, tagList, role, favourite);
        }

        // person to be added is of type STAFF
        Person person = new Person(name, phone, email, address, tagList, role, favourite);
        return new AddCommand(person);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * for adding students and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parseNonStaff(ArgumentMultimap argMultimap, Name name, Phone phone, Email email,
                                   Address address, Set<Tag> tagList, Role role,
                                   Favourite favourite) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GRADE, PREFIX_CLASS, PREFIX_RELATIVE_NAME, PREFIX_RELATIVE_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Grade grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get());
        Class studentClass = ParserUtil.parseClass(argMultimap.getValue(PREFIX_CLASS).get());

        Name relativeName = ParserUtil.parseRelativeName(argMultimap.getValue(PREFIX_RELATIVE_NAME).get());
        Phone relativePhone = ParserUtil.parseRelativePhone(argMultimap.getValue(PREFIX_RELATIVE_PHONE).get());
        Person person = new Person(name, phone, email, address, tagList, role, grade, studentClass,
                relativeName, relativePhone, favourite);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
