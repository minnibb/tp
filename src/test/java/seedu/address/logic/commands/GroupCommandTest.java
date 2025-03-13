package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;





public class GroupCommandTest {

    private String validRole = "student";


    private final String invalidRole = "InvalidRole";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


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

}
