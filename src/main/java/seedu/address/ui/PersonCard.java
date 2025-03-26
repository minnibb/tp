package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane details;
    @FXML
    private FlowPane family;
    @FXML
    private Label favourite;
    @FXML
    private Label notes;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        ArrayList<String> schoolDetails = getSchoolRelatedInformation(person);
        schoolDetails.forEach(detail -> details.getChildren().add(new Label(detail)));

        ArrayList<String> familyInformation = getFamilyInformation(person);
        familyInformation.forEach(information -> family.getChildren().add(new Label(information)));

        setFavouriteStatus(person);
        if (person.getNotes() != null && !person.getNotes().isEmpty()) {
            notes.setText("Notes: " + person.getNotes());
        } else {
            notes.setText("");
        }
    }

    /**
     * Adds the role, grade and class of a Student into an ArrayList, and adds the role
     * of a Staff or Parent into an ArrayList.
     *
     * @param person Person to be reflected in PersonCard.
     * @return An ArrayList of school-related details.
     */
    public static ArrayList<String> getSchoolRelatedInformation(Person person) {
        ArrayList<String> result = new ArrayList<>();

        Role role = person.getRole();
        result.add(role.toString());

        if (role.getType().equals(Role.Type.STUDENT)) {
            String grade = person.getGrade().toString();
            String studentClass = person.getStudentClass().toString();
            String gradeAndClass = grade + ": " + studentClass;
            result.add(gradeAndClass);
        }

        return result;
    }

    private void setFavouriteStatus(Person person) {
        if (person.getFavourite() != null && person.getFavourite().isFavourite()) {
            favourite.setText("★");
        } else {
            favourite.setText("");
        }
    }

    /**
     * Adds the name of parent of the contact, if any, into an ArrayList.
     *
     * @param person Person to be reflected in PersonCard.
     * @return An empty ArrayList or an ArrayList containing a parent's name.
     */
    public static ArrayList<String> getFamilyInformation(Person person) {
        ArrayList<String> result = new ArrayList<>();

        Role role = person.getRole();
        if (role.getType().equals(Role.Type.STUDENT)) {
            String parentName = person.getRelativeName().fullName;
            String parentPhone = person.getRelativePhone().value;
            result.add(String.format("Parent: %s (%s)", parentName, parentPhone));
        } else if (role.getType().equals(Role.Type.PARENT)) {
            String childName = person.getRelativeName().fullName;
            String childPhone = person.getRelativePhone().value;
            String childClass = person.getStudentClass().value;
            String childGrade = person.getGrade().toString();
            String childInformation = String.format("Child: %s - %s: %s (%s)", childName, childGrade,
                    childClass, childPhone);
            result.add(childInformation);
        }

        return result;
    }
}
