package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;





public class GroupCommandTest {

    private String validRole = "student";


    private final String invalidRole = "InvalidRole";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidRole_throwsCommandException() {
        String invalidRole = "invalidRole";
        GroupCommand groupCommand = new GroupCommand(invalidRole);

        assertCommandFailure(groupCommand, model, GroupCommand.MESSAGE_INVALID_ROLE);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        GroupCommand command = new GroupCommand(validRole);
        assertTrue(command.equals(command));
    }

    @Test
    void equals_differentObjectsSameValues_returnsTrue() {
        GroupCommand command1 = new GroupCommand(validRole);
        GroupCommand command2 = new GroupCommand(validRole);
        assertTrue(command1.equals(command2));
    }

    @Test
    void equals_differentRoles_returnsFalse() {
        GroupCommand command1 = new GroupCommand(validRole);
        GroupCommand command2 = new GroupCommand("Teacher");
        assertFalse(command1.equals(command2));
    }

    @Test
    void equals_null_returnsFalse() {
        GroupCommand command = new GroupCommand(validRole);
        assertFalse(command.equals(null));
    }
    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);

        String expected = new ToStringBuilder(editCommand)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();

        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void execute_validParentRole_filtersParentContacts() {
        String validRole = "Parent";
        GroupCommand command = new GroupCommand(validRole);

        Predicate<Person> predicate = person -> person.getRole().equals(new Role(validRole));
        expectedModel.updateFilteredPersonList(predicate);
        int expectedSize = expectedModel.getFilteredPersonList().size();

        assertCommandSuccess(command, model,
                String.format(GroupCommand.MESSAGE_SUCCESS, validRole, expectedSize), expectedModel);
    }


    @Test
    public void execute_caseSensitiveRoleValidation() {
        GroupCommand lowerCaseCommand = new GroupCommand("student");
        GroupCommand upperCaseCommand = new GroupCommand("STUDENT");

        assertFalse(lowerCaseCommand.equals(upperCaseCommand));
    }

    @Test
    public void toString_correctRepresentation() {
        GroupCommand command = new GroupCommand("Student");
        String expected = new ToStringBuilder(command)
                .add("role", "Student")
                .toString();
        assertEquals(expected, command.toString());
    }

}
