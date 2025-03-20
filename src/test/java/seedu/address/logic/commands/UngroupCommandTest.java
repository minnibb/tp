package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Role;

public class UngroupCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_resetFilter_showsAllPersons() {
        model.updateFilteredPersonList(person -> person.getRole().equals(new Role("Student")));

        assertCommandSuccess(
                new UngroupCommand(),
                model,
                UngroupCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_emptyFilter_remainsUnchanged() {
        assertCommandSuccess(
                new UngroupCommand(),
                model,
                UngroupCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }
    @Test
    void hashCode_equalsClassHashCode() {

        UngroupCommand command = new UngroupCommand();
        assertEquals(
                command.getClass().hashCode(),
                command.hashCode(),
                "should be equal"
        );
    }

}
