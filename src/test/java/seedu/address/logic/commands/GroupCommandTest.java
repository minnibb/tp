package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
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
    public void execute_invalidInput() {
        String validRole = "Parent";
        GroupCommand command = new GroupCommand("ROOE", validRole);

        assertCommandFailure(command, model, GroupCommand.MESSAGE_INVALID_GROUP);
    }
    @Test
    public void execute_groupByClass_emptyResults() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        GroupCommand command = new GroupCommand("CLASS", "11111");

        CommandResult result = command.execute(model);

        assertEquals(GroupCommand.MESSAGE_NO_RESULTS, result.getFeedbackToUser());
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

    @Test
    public void isMatchingGroup_validRoleMatching_returnsTrue() {
        Person person = new PersonBuilder().withRole("Student").build();
        GroupCommand command = new GroupCommand("ROLE", "Student");
        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_validRoleNotMatching_returnsFalse() {
        Person person = new PersonBuilder().withRole("Parent").build();
        GroupCommand command = new GroupCommand("ROLE", "Student");
        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_invalidRoleCriteria_returnsFalse() {
        Person person = new PersonBuilder().withRole("Student").build();
        GroupCommand command = new GroupCommand("ROLE", "InvalidRole");
        assertFalse(command.isMatchingGroup(person));
    }


    @Test
    public void isMatchingGroup_validClassMatching_returnsTrue() {
        Person person = new PersonBuilder().withClass("3K").build();
        GroupCommand command = new GroupCommand("CLASS", "3K");
        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_classFieldNull_returnsFalse() {
        Person person = new PersonBuilder().build(); // 默认 class 为 null
        GroupCommand command = new GroupCommand("CLASS", "3K");
        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_invalidClassCriteria_returnsFalse() {
        Person person = new PersonBuilder().withClass("3K").build();
        GroupCommand command = new GroupCommand("CLASS", "InvalidClass");
        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_validGradeMatching_returnsTrue() {
        Person person = new PersonBuilder().withGrade("sec 3").build();
        GroupCommand command = new GroupCommand("GRADE", "sec 3");
        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_gradeFieldNull_returnsFalse() {
        Person person = new PersonBuilder().build(); // 默认 grade 为 null
        GroupCommand command = new GroupCommand("GRADE", "sec 3");
        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_invalidGradeCriteria_returnsFalse() {
        Person person = new PersonBuilder().withGrade("pri 6").build();
        GroupCommand command = new GroupCommand("GRADE", "invalid_grade");
        assertFalse(command.isMatchingGroup(person));
    }


    @Test
    public void isMatchingGroup_unknownCategory_returnsFalse() {
        Person person = new PersonBuilder().build();
        GroupCommand command = new GroupCommand("INVALID_CATEGORY", "any");
        assertFalse(command.isMatchingGroup(person));
    }
    @Test
    public void filter_favouritePerson_true() {
        Person person = new PersonBuilder()
                .withFavourite(true)
                .build();

        boolean isMatched = person.getFavourite() != null && person.getFavourite().isFavourite();
        assertTrue(isMatched);
    }
    @Test
    public void filter_nonFavouritePerson_false() {
        Person person = new PersonBuilder()
                .withFavourite(false)
                .build();

        boolean isMatched = person.getFavourite() != null && person.getFavourite().isFavourite();
        assertFalse(isMatched);
    }



    @Test
    public void filter_defaultFavouriteState_false() {
        Person person = new PersonBuilder().build();

        boolean isMatched = person.getFavourite() != null && person.getFavourite().isFavourite();
        assertFalse(isMatched);
    }
    @Test
    public void equals_sameCategoryAndCriteria_returnsTrue() {
        GroupCommand command1 = new GroupCommand("ROLE", "Student");
        GroupCommand command2 = new GroupCommand("ROLE", "Student");
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_sameCategoryDifferentCriteria_returnsFalse() {
        GroupCommand command1 = new GroupCommand("ROLE", "Student");
        GroupCommand command2 = new GroupCommand("ROLE", "Teacher");
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentCategorySameCriteria_returnsFalse() {
        GroupCommand command1 = new GroupCommand("ROLE", "Student");
        GroupCommand command2 = new GroupCommand("CLASS", "Student");
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentCategoryAndCriteria_returnsFalse() {
        GroupCommand command1 = new GroupCommand("ROLE", "Student");
        GroupCommand command2 = new GroupCommand("CLASS", "2025");
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_favouriteCategoryEmptyCriteria_returnsTrue() {
        GroupCommand command1 = new GroupCommand("FAVOURITE", "");
        GroupCommand command2 = new GroupCommand("FAVOURITE", "");
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_notGroupCommandInstance_returnsFalse() {
        GroupCommand command = new GroupCommand("GRADE", "sec 3");
        String notGroupCommand = "group by GRADE sec 3";
        assertFalse(command.equals(notGroupCommand));
    }

    @Test
    public void equals_null_returnsFalse() {
        GroupCommand command = new GroupCommand("CLASS", "3K");
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_caseInsensitiveCategory_returnsTrue() {
        GroupCommand command1 = new GroupCommand("role", "Student");
        GroupCommand command2 = new GroupCommand("ROLE", "Student");
        assertTrue(command1.equals(command2));
    }
    @Test
    public void execute_caseInsensitiveCategory_success() {
        GroupCommand lowerCommand = new GroupCommand("grade", "sec 3");
        GroupCommand upperCommand = new GroupCommand("GRADE", "sec 3");
        assertTrue(lowerCommand.equals(upperCommand));
    }
    @Test
    public void execute_roleCategoryWithValidCriteria_success() {
        String validRole = "Student";
        GroupCommand upperCommand = new GroupCommand("ROLE", validRole);
        Model expectedModelUpper = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelUpper.updateFilteredPersonList(p -> p.getRole().equals(new Role(validRole)));
        assertCommandSuccess(upperCommand, model,
                String.format(GroupCommand.MESSAGE_SUCCESS, "ROLE", validRole,
                        expectedModelUpper.getFilteredPersonList().size()),
                expectedModelUpper);

        GroupCommand lowerCommand = new GroupCommand("role", validRole);
        assertCommandSuccess(lowerCommand, model,
                String.format(GroupCommand.MESSAGE_SUCCESS, "ROLE", validRole,
                        expectedModelUpper.getFilteredPersonList().size()),
                expectedModelUpper);
    }

    @Test
    public void isMatchingGroup_gradeWithSpaces_returnsTrue() {
        Person person = new PersonBuilder().withGrade("sec 3").build();
        GroupCommand command = new GroupCommand("GRADE", "sec 3");
        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        GroupCommand command = new GroupCommand("ROLE", "Student");
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        GroupCommand command1 = new GroupCommand("ROLE", "Student");
        GroupCommand command2 = new GroupCommand("ROLE", "Student");
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentCategory_returnsFalse() {
        GroupCommand command1 = new GroupCommand("ROLE", "Student");
        GroupCommand command2 = new GroupCommand("CLASS", "Student");
        assertFalse(command1.equals(command2));
    }



    @Test
    public void equals_differentType_returnsFalse() {
        GroupCommand command = new GroupCommand("ROLE", "Student");
        assertFalse(command.equals("Not a Command"));
    }
    @Test
    public void isMatchingGroup_validClass_returnsTrue() {
        Person person = new PersonBuilder().withClass("1A").build();
        GroupCommand command = new GroupCommand("CLASS", "1A");
        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_invalidClass_returnsFalse() {
        Person person = new PersonBuilder().withClass("2B").build();
        GroupCommand command = new GroupCommand("CLASS", "1A");
        assertFalse(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_validGrade_returnsTrue() {
        Person person = new PersonBuilder().withGrade("sec 2").build();
        GroupCommand command = new GroupCommand("GRADE", "sec 2");
        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_invalidGrade_returnsFalse() {
        Person person = new PersonBuilder().withGrade("pri 4").build();
        GroupCommand command = new GroupCommand("GRADE", "sec 2");
        assertFalse(command.isMatchingGroup(person));
    }
    @Test
    public void isMatchingGroup_validFavourite_returnsTrue() {
        Person person = new PersonBuilder().withFavourite(true).build();
        GroupCommand command = new GroupCommand("FAVOURITE", "");
        assertTrue(command.isMatchingGroup(person));
    }

    @Test
    public void isMatchingGroup_invalidFavourite_returnsFalse() {
        Person person = new PersonBuilder().withFavourite(false).build();
        GroupCommand command = new GroupCommand("FAVOURITE", "");
        assertFalse(command.isMatchingGroup(person));
    }
    @Test
    public void groupCommand_validClassCriteria_returnsTrue() {
        GroupCommand command = new GroupCommand("CLASS", "2025");
        assertTrue(Class.isValidClass("2025"));
    }

    @Test
    public void groupCommand_emptyClassCriteria_returnsFalse() {
        GroupCommand command = new GroupCommand("CLASS", "");
        assertFalse(Class.isValidClass(""));
    }

    @Test
    public void groupCommand_categoryNotClass_doesNotCheckClassValidity() {
        GroupCommand command = new GroupCommand("ROLE", "2025");
        assertDoesNotThrow(() -> new GroupCommand("ROLE", "2025"));
    }

}





