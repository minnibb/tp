package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsSubstringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstKeywords = Collections.singletonList("first");
        List<String> secondKeywords = Collections.singletonList("second");

        FindCommand findFirstCommand = new FindCommand(firstKeywords);
        FindCommand findSecondCommand = new FindCommand(secondKeywords);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstKeywords);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // Testing for phone number search
        String firstPhoneNumber = "98765432";
        String secondPhoneNumber = "12345678";

        FindCommand findFirstCommandByPhone = new FindCommand(firstPhoneNumber);
        FindCommand findSecondCommandByPhone = new FindCommand(secondPhoneNumber);

        // same object -> returns true
        assertTrue(findFirstCommandByPhone.equals(findFirstCommandByPhone));

        // same values -> returns true
        FindCommand findFirstCommandByPhoneCopy = new FindCommand(firstPhoneNumber);
        assertTrue(findFirstCommandByPhone.equals(findFirstCommandByPhoneCopy));

        // different types -> returns false
        assertFalse(findFirstCommandByPhone.equals(1));

        // null -> returns false
        assertFalse(findFirstCommandByPhone.equals(null));

        // different phone numbers -> returns false
        assertFalse(findFirstCommandByPhone.equals(findSecondCommandByPhone));
    }

    @Test
    public void execute_zeroKeywords_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size());
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        List<String> emptyKeywords = Collections.emptyList();
        FindCommand command = new FindCommand(emptyKeywords);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredPersonList().size(), expectedModel.getFilteredPersonList().size());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("Meier");
        List<String> keywords = Arrays.asList("Meier");
        FindCommand command = new FindCommand(keywords);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_noMatchingKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate("Edward");
        List<String> keywords = Collections.singletonList("Edward");
        FindCommand command = new FindCommand(keywords);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredPersonList().size(), expectedModel.getFilteredPersonList().size());
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = Arrays.asList("keyword");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(keywords);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    @Test
    public void execute_noMatchingNumbers_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneContainsSubstringPredicate predicate = new PhoneContainsSubstringPredicate("000000");
        String number = "000000";
        FindCommand command = new FindCommand(number);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredPersonList().size(), expectedModel.getFilteredPersonList().size());
    }
}
