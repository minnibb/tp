package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.CHILD_DESC_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.CHILD_PHONE_DESC_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RELATIVE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RELATIVE_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHILD_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHILD_PHONE_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIVE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIVE_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CHARLES;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Class;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + ROLE_DESC_BOB, new AddCommand(expectedPerson));


        // additional tags for student fields ignored if contact added is not a student
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROLE_DESC_BOB + GRADE_DESC_AMY + CLASS_DESC_AMY + PARENT_DESC_AMY,
                new AddCommand(expectedPersonMultipleTags));

        // multiple tags - all accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROLE_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedStaffString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + ROLE_DESC_BOB;

        String validExpectedParentString = NAME_DESC_CHARLES + PHONE_DESC_CHARLES + EMAIL_DESC_CHARLES
                + ADDRESS_DESC_CHARLES + ROLE_DESC_CHARLES + GRADE_DESC_AMY + CLASS_DESC_AMY + CHILD_DESC_CHARLES
                + CHILD_PHONE_DESC_CHARLES;

        String validExpectedStudentString = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + ROLE_DESC_AMY + GRADE_DESC_AMY + CLASS_DESC_AMY
                + PARENT_DESC_AMY + PARENT_PHONE_DESC_AMY;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, NAME_DESC_BOB + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, NAME_DESC_BOB + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, PHONE_DESC_BOB + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, PHONE_DESC_BOB + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, EMAIL_DESC_BOB + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, EMAIL_DESC_BOB + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        assertParseFailure(parser, ADDRESS_DESC_BOB + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        assertParseFailure(parser, ADDRESS_DESC_BOB + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple roles
        assertParseFailure(parser, ROLE_DESC_AMY + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        assertParseFailure(parser, ROLE_DESC_BOB + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        assertParseFailure(parser, ROLE_DESC_BOB + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple grades
        assertParseFailure(parser, GRADE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        assertParseFailure(parser, GRADE_DESC_AMY + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        // multiple classes
        assertParseFailure(parser, CLASS_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        assertParseFailure(parser, CLASS_DESC_AMY + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        // multiple relative's name
        assertParseFailure(parser, PARENT_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_NAME));

        assertParseFailure(parser, CHILD_DESC_CHARLES + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_NAME));

        // multiple relative's phone
        assertParseFailure(parser, PARENT_PHONE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_PHONE));

        assertParseFailure(parser, CHILD_PHONE_DESC_CHARLES + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_PHONE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedStaffString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + ROLE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE,
                        PREFIX_ROLE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid role
        assertParseFailure(parser, INVALID_ROLE_DESC + validExpectedStaffString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        assertParseFailure(parser, INVALID_ROLE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        assertParseFailure(parser, INVALID_ROLE_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid grade
        assertParseFailure(parser, INVALID_GRADE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        assertParseFailure(parser, INVALID_GRADE_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        // invalid class
        assertParseFailure(parser, INVALID_CLASS_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        assertParseFailure(parser, INVALID_CLASS_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        // invalid parent name
        assertParseFailure(parser, INVALID_RELATIVE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_NAME));

        assertParseFailure(parser, INVALID_RELATIVE_DESC + validExpectedParentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_NAME));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStaffString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        assertParseFailure(parser, validExpectedParentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedStaffString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, validExpectedStudentString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        assertParseFailure(parser, validExpectedParentString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedStaffString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, validExpectedStudentString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        assertParseFailure(parser, validExpectedParentString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedStudentString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        assertParseFailure(parser, validExpectedStaffString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        assertParseFailure(parser, validExpectedParentString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid role
        assertParseFailure(parser, validExpectedStudentString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        assertParseFailure(parser, validExpectedStaffString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        assertParseFailure(parser, validExpectedParentString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid grade
        assertParseFailure(parser, validExpectedStudentString + INVALID_GRADE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        assertParseFailure(parser, validExpectedParentString + INVALID_GRADE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        // invalid class
        assertParseFailure(parser, validExpectedStudentString + INVALID_CLASS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        assertParseFailure(parser, validExpectedParentString + INVALID_CLASS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLASS));

        // invalid family member's name
        assertParseFailure(parser, validExpectedStudentString + INVALID_RELATIVE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_NAME));

        assertParseFailure(parser, validExpectedParentString + INVALID_RELATIVE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_NAME));

        // invalid family member's phone
        assertParseFailure(parser, validExpectedStudentString + INVALID_RELATIVE_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_PHONE));

        assertParseFailure(parser, validExpectedParentString + INVALID_RELATIVE_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RELATIVE_PHONE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedStudent = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ROLE_DESC_AMY
                + GRADE_DESC_AMY + CLASS_DESC_AMY + PARENT_DESC_AMY + PARENT_PHONE_DESC_AMY,
                new AddCommand(expectedStudent));

        // no grade, class and parent for Staffs
        Person expectedPerson = new PersonBuilder(BOB).withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ROLE_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                        + ADDRESS_DESC_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + VALID_ADDRESS_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ROLE_DESC_BOB + VALID_ROLE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                        + VALID_ADDRESS_BOB + VALID_ROLE_BOB, expectedMessage);
    }

    @Test
    public void parse_compulsoryStudentPrefixesPresent_failure() {
        // create new person of type STUDENT
        Person expectedStudent = new PersonBuilder(AMY).withTags(VALID_TAG_FRIEND).withGrade(VALID_GRADE_AMY)
                .withClass(VALID_CLASS_AMY).withRelativeName(VALID_PARENT_AMY)
                .withRelativePhone(VALID_PARENT_PHONE_AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + ROLE_DESC_AMY + GRADE_DESC_AMY
                + CLASS_DESC_AMY + PARENT_DESC_AMY + PARENT_PHONE_DESC_AMY, new AddCommand(expectedStudent));

        // multiple tags - all accepted
        Person expectedStudentMultipleTags = new PersonBuilder(AMY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROLE_DESC_AMY + GRADE_DESC_AMY + CLASS_DESC_AMY + PARENT_DESC_AMY
                        + PARENT_PHONE_DESC_AMY,
                new AddCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_compulsoryParentPrefixesPresent_failure() {
        // create new person of type PARENT
        Person expectedParent = new PersonBuilder(CHARLES).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CHARLES + PHONE_DESC_CHARLES
                + EMAIL_DESC_CHARLES + ADDRESS_DESC_CHARLES + ROLE_DESC_CHARLES + GRADE_DESC_AMY
                + CLASS_DESC_AMY + CHILD_DESC_CHARLES + CHILD_PHONE_DESC_CHARLES, new AddCommand(expectedParent));

        // multiple tags - all accepted
        Person expectedParentMultipleTags = new PersonBuilder(CHARLES)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser,
                NAME_DESC_CHARLES + PHONE_DESC_CHARLES + EMAIL_DESC_CHARLES + ADDRESS_DESC_CHARLES
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND+ ROLE_DESC_CHARLES + GRADE_DESC_AMY
                        + CLASS_DESC_AMY + CHILD_DESC_CHARLES + CHILD_PHONE_DESC_CHARLES,
                new AddCommand(expectedParentMultipleTags));
    }

    @Test
    public void parse_missingStudentPrefixes_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing grade prefix
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + ROLE_DESC_AMY + CLASS_DESC_AMY + PARENT_DESC_AMY + PARENT_PHONE_DESC_AMY,
                expectedMessage);

        // missing class prefix
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + ROLE_DESC_AMY + GRADE_DESC_AMY + VALID_CLASS_AMY + PARENT_DESC_AMY + PARENT_PHONE_DESC_AMY,
                expectedMessage);

        // missing parent name prefix
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + ROLE_DESC_AMY + GRADE_DESC_AMY + CLASS_DESC_AMY + VALID_PARENT_AMY + PARENT_PHONE_DESC_AMY,
                expectedMessage);

        // missing parent phone prefix
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + ROLE_DESC_AMY + GRADE_DESC_AMY + CLASS_DESC_AMY + PARENT_DESC_AMY + VALID_PARENT_PHONE_AMY,
                expectedMessage);
    }

    @Test
    public void parse_missingParentPrefixes_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing grade prefix
        assertParseFailure(parser, NAME_DESC_CHARLES + PHONE_DESC_CHARLES + EMAIL_DESC_CHARLES
                        + ADDRESS_DESC_CHARLES + ROLE_DESC_CHARLES + CLASS_DESC_AMY
                        + CHILD_DESC_CHARLES + CHILD_PHONE_DESC_CHARLES,
                expectedMessage);

        // missing class prefix
        assertParseFailure(parser, NAME_DESC_CHARLES + PHONE_DESC_CHARLES + EMAIL_DESC_CHARLES
                        + ADDRESS_DESC_CHARLES + ROLE_DESC_CHARLES + GRADE_DESC_AMY + VALID_CLASS_AMY
                        + CHILD_DESC_CHARLES + CHILD_PHONE_DESC_CHARLES,
                expectedMessage);

        // missing child name prefix
        assertParseFailure(parser, NAME_DESC_CHARLES + PHONE_DESC_CHARLES + EMAIL_DESC_CHARLES
                        + ADDRESS_DESC_CHARLES + ROLE_DESC_CHARLES + GRADE_DESC_AMY + CLASS_DESC_AMY
                        + VALID_CHILD_CHARLES + CHILD_PHONE_DESC_CHARLES,
                expectedMessage);

        // missing child phone prefix
        assertParseFailure(parser, NAME_DESC_CHARLES + PHONE_DESC_CHARLES + EMAIL_DESC_CHARLES
                        + ADDRESS_DESC_CHARLES + ROLE_DESC_CHARLES + GRADE_DESC_AMY + CLASS_DESC_AMY
                        + CHILD_DESC_CHARLES + VALID_CHILD_PHONE_CHARLES,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ROLE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ROLE_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ROLE_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ROLE_DESC_BOB, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + ROLE_DESC_BOB, Tag.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + VALID_TAG_FRIEND + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS);

        // invalid grade
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + VALID_TAG_FRIEND + ROLE_DESC_AMY + INVALID_GRADE_DESC + CLASS_DESC_AMY
                + PARENT_DESC_AMY + PARENT_PHONE_DESC_AMY, Grade.MESSAGE_CONSTRAINTS);

        // invalid class
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + VALID_TAG_FRIEND + ROLE_DESC_AMY + GRADE_DESC_AMY + INVALID_CLASS_DESC
                + PARENT_DESC_AMY + PARENT_PHONE_DESC_AMY, Class.MESSAGE_CONSTRAINTS);

        // invalid family member's name
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + VALID_TAG_FRIEND + ROLE_DESC_AMY + GRADE_DESC_AMY + CLASS_DESC_AMY
                + INVALID_RELATIVE_DESC + PARENT_PHONE_DESC_AMY, Name.MESSAGE_CONSTRAINTS);

        // invalid family member's phone
        assertParseFailure(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + VALID_TAG_FRIEND + ROLE_DESC_AMY + GRADE_DESC_AMY + CLASS_DESC_AMY
                + PARENT_DESC_AMY + INVALID_RELATIVE_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + ROLE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ROLE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
