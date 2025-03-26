package seedu.address.testutil;

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

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns a delete command string for adding the {@code person}.
     */
    public static String getDeleteCommand(Person person) {
        return DeleteCommand.COMMAND_WORD + " " + getNameAndPhoneDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        sb.append(PREFIX_ROLE + person.getRole().toString() + " ");
        sb.append(PREFIX_GRADE + person.getGrade().toString() + " ");
        sb.append(PREFIX_CLASS + person.getStudentClass().toString() + " ");
        sb.append(PREFIX_RELATIVE_NAME + person.getRelativeName().toString() + " ");
        sb.append(PREFIX_RELATIVE_PHONE + person.getRelativePhone().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getNameAndPhoneDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG).append(" ");
            } else {
                tags.forEach(tag -> sb.append(PREFIX_TAG).append(tag.tagName).append(" "));
            }
        }
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.getType()).append(" "));
        descriptor.getGrade().ifPresent(grade -> sb.append(PREFIX_GRADE).append(grade.toString()).append(" "));
        descriptor.getStudentClass()
                .ifPresent(studentClass -> sb.append(PREFIX_CLASS).append(studentClass.value).append(" "));
        descriptor.getRelativeName().ifPresent(kin -> sb.append(PREFIX_RELATIVE_NAME).append(kin.fullName).append(" "));
        descriptor.getRelativePhone()
                .ifPresent(phone -> sb.append(PREFIX_RELATIVE_PHONE).append(phone.value).append(" "));
        return sb.toString();
    }
}
