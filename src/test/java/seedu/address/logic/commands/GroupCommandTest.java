package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Class;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.testutil.PersonBuilder;

public class GroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_groupByGrade_success() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        GroupCommand command = new GroupCommand("GRADE", "sec 3");

        Predicate<Person> expectedPredicate = person ->
                person.getGrade() != null
                        && person.getGrade().equals(new Grade("sec 3"));
        expectedModel.updateFilteredPersonList(expectedPredicate);

        int expectedSize = expectedModel.getFilteredPersonList().size();

        String expectedMessage = String.format(
                GroupCommand.MESSAGE_SUCCESS,
                "GRADE",
                "sec 3",
                expectedSize
        );
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_groupByClass_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        GroupCommand command = new GroupCommand("CLASS", "3K");

        Predicate<Person> expectedPredicate = person ->
                person.getStudentClass() != null
                        && person.getStudentClass().equals(new Class("3K"));
        expectedModel.updateFilteredPersonList(expectedPredicate);

        int expectedSize = expectedModel.getFilteredPersonList().size();

        String expectedMessage = String.format(
                GroupCommand.MESSAGE_SUCCESS,
                "CLASS",
                "3K",
                expectedSize
        );

        assertCommandSuccess(
                command,
                model,
                expectedMessage,
                expectedModel
        );
    }

    @Test
    public void equals_withCriteria() {
        GroupCommand command1 = new GroupCommand("ROLE", "Student");
        GroupCommand command2 = new GroupCommand("ROLE", "Student");
        assertTrue(command1.equals(command2));

        GroupCommand command3 = new GroupCommand("ROLE", "Parent");
        assertFalse(command1.equals(command3));
    }

    @Test
    public void toString_includesCategoryAndCriteria() {
        GroupCommand command = new GroupCommand("CLASS", "2025");
        String expected = new ToStringBuilder(command)
                .add("category", "CLASS")
                .add("criteria", "2025")
                .toString();
        assertEquals(expected, command.toString());
    }
    @Test
    public void execute_validParentRole_filtersParentContacts() {
        String validRole = "Parent";
        GroupCommand command = new GroupCommand("ROLE", validRole);

        Predicate<Person> predicate = person -> person.getRole().equals(new Role(validRole));
        expectedModel.updateFilteredPersonList(predicate);
        int expectedSize = expectedModel.getFilteredPersonList().size();

        assertCommandSuccess(command, model,
                String.format(GroupCommand.MESSAGE_SUCCESS, "ROLE", validRole, expectedSize), expectedModel);
    }
    @Test
    public void equals_differentCriteria_returnsFalse() {
        GroupCommand cmd1 = new GroupCommand("GRADE", "sec 1");
        GroupCommand cmd2 = new GroupCommand("GRADE", "sec 2");
        assertFalse(cmd1.equals(cmd2));
    }
    @Test
    public void execute_groupByFavouriteNoCriteria_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        GroupCommand command = new GroupCommand("FAVOURITE", "");

        Predicate<Person> predicate = person ->
                person.getFavourite() != null
                        && person.getFavourite().isFavourite();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(
                GroupCommand.MESSAGE_SUCCESS,
                "FAVOURITE",
                "",
                expectedModel.getFilteredPersonList().size()
        );

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
    @Test
    public void isMatchingGroup_favourite_returnsCorrectBoolean() {
        Person favouritePerson = new PersonBuilder().withFavourite(true).build();
        Person nonFavouritePerson = new PersonBuilder().withFavourite(false).build();

        GroupCommand command = new GroupCommand("FAVOURITE", "");

        assertTrue(command.isMatchingGroup(favouritePerson));
        assertFalse(command.isMatchingGroup(nonFavouritePerson));
    }

    @Test
    public void isMatchingGroup_roleValidAndMatches_returnsTrue() {
        Person student = new PersonBuilder().withRole("Student").build();
        GroupCommand command = new GroupCommand("ROLE", "Student");

        assertTrue(command.isMatchingGroup(student));
    }


    @Test
    public void isMatchingGroup_classValidAndMatches_returnsTrue() {
        Person person = new PersonBuilder().withClass("3K").build();
        GroupCommand command = new GroupCommand("CLASS", "3K");

        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_classNull_returnsFalse() {
        Person person = new PersonBuilder().build();
        GroupCommand command = new GroupCommand("CLASS", "3K");

        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingClass_invalidCriteria_returnsFalse() {
        Person person = new PersonBuilder().withClass("3K").build();
        GroupCommand command = new GroupCommand("CLASS", "InvalidClass");

        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGrade_validAndMatches_returnsTrue() {
        Person person = new PersonBuilder().withGrade("sec 3").build();
        GroupCommand command = new GroupCommand("GRADE", "sec 3");

        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGrade_gradeNull_returnsFalse() {
        Person person = new PersonBuilder().build();
        GroupCommand command = new GroupCommand("GRADE", "sec 3");

        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGrade_invalidCriteria_returnsFalse() {
        Person person = new PersonBuilder().withGrade("pri 6").build();
        GroupCommand command = new GroupCommand("GRADE", "invalid_grade");

        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_defaultCase_returnsFalse() {
        Person person = new PersonBuilder().build();
        GroupCommand command = new GroupCommand("INVALID_CATEGORY", "any");

        assertFalse(command.isMatchingGroup(person));
    }


}
