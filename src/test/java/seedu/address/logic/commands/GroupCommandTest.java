package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



public class GroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedmodel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private String validRole = "student";


    private Model expectedModel;

    private final String invalidRole = "InvalidRole";

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

}
