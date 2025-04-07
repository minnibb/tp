package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CHARLES;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class PersonCardTest {

    private static final Person NULL_PERSON = null;
    private static final Person VALID_STUDENT = AMY;
    private static final Person VALID_STAFF = BOB;
    private static final Person VALID_PARENT = CHARLES;

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
    public void getSchoolRelatedInformation_validParent_success() {
        ArrayList<String> actual = PersonCard.getSchoolRelatedInformation(VALID_PARENT);

        ArrayList<String> expected = new ArrayList<>();
        expected.add(VALID_PARENT.getRole().toString());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void getSchoolRelatedInformation_validStaff_success() {
        ArrayList<String> actual = PersonCard.getSchoolRelatedInformation(VALID_STAFF);

        ArrayList<String> expected = new ArrayList<>();
        expected.add(VALID_STAFF.getRole().toString());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void getFamilyInformation_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PersonCard.getFamilyInformation(NULL_PERSON));
    }

    @Test
    public void getFamilyInformation_validStudent_success() {
        ArrayList<String> actual = PersonCard.getFamilyInformation(VALID_STUDENT);

        ArrayList<String> expected = new ArrayList<>();
        String parent = String.format("Parent: %s (%s)", VALID_STUDENT.getRelativeName().fullName,
                VALID_STUDENT.getRelativePhone().value);
        expected.add(parent);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void getFamilyInformation_validParent_success() {
        ArrayList<String> actual = PersonCard.getFamilyInformation(VALID_PARENT);

        ArrayList<String> expected = new ArrayList<>();
        String parent = String.format("Child: %s - %s: %s (%s)", VALID_PARENT.getRelativeName().fullName,
                VALID_PARENT.getGrade().toString(), VALID_PARENT.getStudentClass().value,
                VALID_PARENT.getRelativePhone().value);
        expected.add(parent);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void getFamilyInformation_validStaff_success() {
        ArrayList<String> actual = PersonCard.getFamilyInformation(VALID_STAFF);
        ArrayList<String> expected = new ArrayList<>();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }
    @Test
    public void constructor_setsDateAdded_correctlyFormatsTimestamp() {
        PersonCard personCard = new PersonCard(VALID_STUDENT, 1);
        assertEquals(VALID_STUDENT, personCard.person);
    }
}
