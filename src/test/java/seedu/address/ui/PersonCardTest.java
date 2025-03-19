package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class PersonCardTest {

    private static final Person NULL_PERSON = null;
    private static final Person VALID_STUDENT = AMY;
    private static final Person VALID_PERSON = BOB;

    @Test
    public void getSchoolRelatedInformation_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PersonCard.getSchoolRelatedInformation(NULL_PERSON));
    }

    @Test
    public void getSchoolRelatedInformation_validStudent_success() {
        ArrayList<String> actual = PersonCard.getSchoolRelatedInformation(VALID_STUDENT);

        ArrayList<String> expected = new ArrayList<>();
        expected.add(VALID_STUDENT.getRole().toString());
        String grade = VALID_STUDENT.getGrade().toString();
        String studentClass = VALID_STUDENT.getStudentClass().value;
        String gradeAndClass = String.format("%s: %s", grade, studentClass);
        expected.add(gradeAndClass);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void getSchoolRelatedInformation_validPerson_success() {
        ArrayList<String> actual = PersonCard.getSchoolRelatedInformation(VALID_PERSON);

        ArrayList<String> expected = new ArrayList<>();
        expected.add(VALID_PERSON.getRole().toString());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void getParentName_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PersonCard.getParentName(NULL_PERSON));
    }

    @Test
    public void getParentName_validStudent_success() {
        ArrayList<String> actual = PersonCard.getParentName(VALID_STUDENT);

        ArrayList<String> expected = new ArrayList<>();
        String parent = String.format("Parent: %s", VALID_STUDENT.getParentName().fullName);
        expected.add(parent);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void getParentName_validPerson_success() {
        ArrayList<String> actual = PersonCard.getParentName(VALID_PERSON);
        ArrayList<String> expected = new ArrayList<>();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

}
