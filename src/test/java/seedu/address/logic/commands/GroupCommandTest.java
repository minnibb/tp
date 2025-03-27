package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.testutil.PersonBuilder;


public class GroupCommandTest {

    private String validRole = "student";
    private final String invalidGroupCommand = "InvalidGroup";
    private final String favouriteGroup = GroupCommand.FAVOURITE;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidRole_throwsCommandException() {
        GroupCommand groupCommand = new GroupCommand(invalidGroupCommand);

        CommandException exception = assertThrows(CommandException.class, () -> {
            groupCommand.execute(model);
        });

        assertEquals(GroupCommand.MESSAGE_INVALID_GROUP, exception.getMessage());
    }

    @Test
    void equals_sameObject_returnsTrue() {
        GroupCommand commandRole = new GroupCommand(validRole);
        assertTrue(commandRole.equals(commandRole));

        GroupCommand commandFavourite = new GroupCommand(favouriteGroup);
        assertTrue(commandFavourite.equals(commandFavourite));
    }

    @Test
    void equals_differentObjectsSameValues_returnsTrue() {
        GroupCommand commandRole1 = new GroupCommand(validRole);
        GroupCommand commandRole2 = new GroupCommand(validRole);
        assertTrue(commandRole1.equals(commandRole2));

        GroupCommand commandFavourite1 = new GroupCommand(favouriteGroup);
        GroupCommand commandFavourite2 = new GroupCommand(favouriteGroup);
        assertTrue(commandFavourite1.equals(commandFavourite2));
    }

    @Test
    void equals_differentRoles_returnsFalse() {
        GroupCommand command1 = new GroupCommand(validRole);
        GroupCommand command2 = new GroupCommand("Parent");
        assertFalse(command1.equals(command2));

    }

    @Test
    void equals_null_returnsFalse() {
        GroupCommand commandRole = new GroupCommand(validRole);
        assertFalse(commandRole.equals(null));

        GroupCommand commandFavourite = new GroupCommand(favouriteGroup);
        assertFalse(commandFavourite.equals(null));
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
    public void execute_validFavouriteGroup_filtersFavouriteContacts() {
        GroupCommand command = new GroupCommand(favouriteGroup);
        Predicate<Person> predicate = person -> person.getFavourite() != null && person.getFavourite().isFavourite();
        expectedModel.updateFilteredPersonList(predicate);
        int expectedSize = expectedModel.getFilteredPersonList().size();
        System.out.println(model.getFilteredPersonList());
        System.out.println(expectedModel.getFilteredPersonList());

        assertCommandSuccess(command, model,
                String.format(GroupCommand.MESSAGE_SUCCESS, favouriteGroup, expectedSize), expectedModel);
    }

    @Test
    public void execute_caseSensitiveRoleValidation() {
        GroupCommand lowerCaseCommand = new GroupCommand("student");
        GroupCommand upperCaseCommand = new GroupCommand("STUDENT");

        assertFalse(lowerCaseCommand.equals(upperCaseCommand));
    }

    @Test
    public void execute_caseSensitiveFavouriteValidation() {
        GroupCommand lowerCaseCommand = new GroupCommand("favourite");
        GroupCommand upperCaseCommand = new GroupCommand("FAVOURITE");

        assertFalse(lowerCaseCommand.equals(upperCaseCommand));
    }

    @Test
    public void toString_correctRepresentation() {
        GroupCommand commandRole = new GroupCommand("Student");
        String expectedRole = new ToStringBuilder(commandRole)
                .add("groupCriteria", "Student")
                .toString();
        assertEquals(expectedRole, commandRole.toString());

        GroupCommand commandFavourite = new GroupCommand(favouriteGroup);
        String expectedFavourite = new ToStringBuilder(commandRole)
                .add("groupCriteria", "favourite")
                .toString();
        assertEquals(expectedFavourite, commandFavourite.toString());
    }

    @Test
    public void isMatchingGroup_invalidGroupCriteria_returnsFalse() {
        String invalidGroupCriteria = "InvalidGroup";

        GroupCommand groupCommand = new GroupCommand(invalidGroupCriteria);

        Person person = new PersonBuilder().withRole("student").build();

        assertFalse(groupCommand.isMatchingGroup(person));
    }

}
