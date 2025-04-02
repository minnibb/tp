package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Class;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    public static final String MESSAGE_PARSE_STAFF_ERROR = "Prefixes for grade, class, relative's name "
            + "and relative's phone should not be present when changing to the Staff role.";

    public static final String MESSAGE_PARSE_NON_STAFF_ERROR = "Prefixes for grade, class, relative's name "
            + "and relative's phone must be present when changing to the Student or Parent role.";

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_ROLE, PREFIX_GRADE, PREFIX_CLASS, PREFIX_RELATIVE_NAME, PREFIX_RELATIVE_PHONE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_ROLE, PREFIX_GRADE, PREFIX_CLASS, PREFIX_RELATIVE_NAME, PREFIX_RELATIVE_PHONE);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            editPersonDescriptor.setGrade(ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get()));
        }
        if (argMultimap.getValue(PREFIX_CLASS).isPresent()) {
            editPersonDescriptor.setStudentClass(ParserUtil.parseClass(argMultimap.getValue(PREFIX_CLASS).get()));
        }
        if (argMultimap.getValue(PREFIX_RELATIVE_NAME).isPresent()) {
            editPersonDescriptor
                    .setRelativeName(ParserUtil.parseRelativeName(argMultimap.getValue(PREFIX_RELATIVE_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_RELATIVE_PHONE).isPresent()) {
            editPersonDescriptor
                    .setRelativePhone(ParserUtil.parseRelativePhone(argMultimap.getValue(PREFIX_RELATIVE_PHONE).get()));
        }

        // parse role the last so that it can override any changes in grade, class, relative's name
        // and relative's phone if necessary
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            parseRole(argMultimap, editPersonDescriptor);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses the {@code Role} of the edited Person.
     */
    private EditPersonDescriptor parseRole(ArgumentMultimap argMultimap,
                                           EditPersonDescriptor editPersonDescriptor) throws ParseException {

        boolean isGradePresent = argMultimap.getValue(PREFIX_GRADE).isPresent();
        boolean isClassPresent = argMultimap.getValue(PREFIX_CLASS).isPresent();
        boolean isRelativeNamePresent = argMultimap.getValue(PREFIX_RELATIVE_NAME).isPresent();
        boolean isRelativePhonePresent = argMultimap.getValue(PREFIX_RELATIVE_PHONE).isPresent();

        Role newRole = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());

        if (newRole.getType().equals(Role.Type.STAFF)) {
            // grade, class, relative's name and relative's phone should not be present
            if (isGradePresent || isClassPresent || isRelativeNamePresent || isRelativePhonePresent) {
                throw new ParseException(MESSAGE_PARSE_STAFF_ERROR);
            }
            editPersonDescriptor.setRole(newRole);
            editPersonDescriptor.setGrade(new Grade("Not applicable"));
            editPersonDescriptor.setStudentClass(new Class("Not applicable"));
            editPersonDescriptor.setRelativeName(new Name("Not applicable"));
            editPersonDescriptor.setRelativePhone(new Phone("Not applicable"));
            return editPersonDescriptor;
        }

        // new role is not of type STAFF
        if (!(isGradePresent && isClassPresent && isRelativeNamePresent && isRelativePhonePresent)) {
            throw new ParseException(MESSAGE_PARSE_NON_STAFF_ERROR);
        } else {
            editPersonDescriptor.setRole(newRole);
        }

        return editPersonDescriptor;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
