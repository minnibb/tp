package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final String MESSAGE_CONSTRAINTS = "The Grade and Class attributes should be added for students "
            + "but not for staff and parents.";
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    // Timestamp when this person was added
    private final long timeAdded;

    private final Role role;
    private Grade grade;
    private Name relativeName;
    private Phone relativePhone;
    private Class studentClass;
    private final Favourite favourite;
    private final String notes;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Role role, Favourite favourite) {
        requireAllNonNull(name, phone, email, address, tags, role, favourite);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.timeAdded = System.currentTimeMillis();
        this.role = role;
        this.grade = new Grade("Not applicable");
        this.studentClass = new Class("Not applicable");
        this.relativeName = new Name("Not applicable");
        this.relativePhone = new Phone("Not applicable");
        this.favourite = favourite;
        this.notes = "";
    }
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Role role,
                  Grade grade, Class studentClass, Name relativeName, Phone relativePhone, Favourite favourite) {
        requireAllNonNull(name, phone, email, address, tags, role, grade, studentClass, favourite);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.timeAdded = System.currentTimeMillis();
        this.role = role;
        this.grade = grade;
        this.studentClass = studentClass;
        this.relativeName = relativeName;
        this.relativePhone = relativePhone;
        this.favourite = favourite;
        this.notes = "";
    }

    /**
     * Every field must be present and not null.
     * Constructor with notes field included.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Role role,
                  Grade grade, Class studentClass, Name relativeName, Phone relativePhone,
                  Favourite favourite, String notes) {
        requireAllNonNull(name, phone, email, address, tags, role, grade, studentClass, favourite);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.timeAdded = System.currentTimeMillis();
        this.role = role;
        this.grade = grade;
        this.studentClass = studentClass;
        this.relativeName = relativeName;
        this.relativePhone = relativePhone;
        this.favourite = favourite;
        this.notes = notes == null ? "" : notes;
    }

    /**
     * Every field must be present and not null.
     * Constructor with notes field included for staff.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Role role,
                  Favourite favourite, String notes) {
        requireAllNonNull(name, phone, email, address, tags, role, favourite);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.timeAdded = System.currentTimeMillis();
        this.role = role;
        this.grade = new Grade("Not applicable");
        this.studentClass = new Class("Not applicable");
        this.relativeName = new Name("Not applicable");
        this.relativePhone = new Phone("Not applicable");
        this.favourite = favourite;
        this.notes = notes == null ? "" : notes;
    }

    public Name getName() {
        return name;
    }
    public Phone getPhone() {
        return phone;
    }
    public Email getEmail() {
        return email;
    }
    public Address getAddress() {
        return address;
    }
    public Role getRole() {
        return role;
    }
    /**
     * Returns the timestamp when this person was added.
     */
    public long getTimeAdded() {
        return timeAdded;
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
    public Grade getGrade() {
        return grade;
    }
    public Class getStudentClass() {
        return studentClass;
    }

    public Name getRelativeName() {
        return relativeName;
    }

    public Phone getRelativePhone() {
        return relativePhone;
    }

    public Favourite getFavourite() {
        return favourite;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Creates a new Person with updated notes.
     *
     * @param notes The new notes to be added to the person
     * @return A new Person object with the updated notes
     */
    public Person withNotes(String notes) {
        if (!role.getType().equals(Role.Type.STAFF)) {
            return new Person(name, phone, email, address, tags, role, grade, studentClass,
                    relativeName, relativePhone, favourite, notes);
        }
        return new Person(name, phone, email, address, tags, role, favourite, notes);
    }

    /**
     * Toggles the 'favourite' status of the person. If the person is a student,
     * the method updates the student's favourite status along with their grade and class details.
     * For non-students, only the favourite status is toggled.
     *
     * @return A new Person object with the updated favourite status.
     */
    public Person toggleFavourite() {
        if (!role.getType().equals(Role.Type.STAFF)) {
            return new Person(name, phone, email, address, tags, role, grade, studentClass,
                    relativeName, relativePhone, favourite.toggle(), notes);
        }
        return new Person(name, phone, email, address, tags, role, favourite.toggle(), notes);
    }

    /**
     * Returns true if both persons have the same name and phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        if (otherPerson == null) {
            return false;
        }
        // each unique person is identified by their name and their phone number
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
    }
    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && role.equals(otherPerson.role)
                && grade.equals(otherPerson.grade)
                && studentClass.equals(otherPerson.studentClass)
                && favourite.equals(otherPerson.favourite)
                && relativeName.equals(otherPerson.relativeName)
                && relativePhone.equals(otherPerson.relativePhone)
                && notes.equals(otherPerson.notes);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, role, grade, studentClass,
                relativeName, relativePhone, favourite, notes);
    }

    @Override
    public String toString() {
        if (role.equals(new Role("Student"))) {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("role", role)
                    .add("grade", grade)
                    .add("class", studentClass)
                    .add("parent", relativeName)
                    .add("parent's phone", relativePhone)
                    .add("favourite", favourite)
                    .add("notes", notes)
                    .toString();
        }

        if (role.equals(new Role("Parent"))) {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("role", role)
                    .add("child's grade", grade)
                    .add("child's class", studentClass)
                    .add("child's name", relativeName)
                    .add("child's phone", relativePhone)
                    .add("favourite", favourite)
                    .add("notes", notes)
                    .toString();
        }

        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("role", role)
                .add("favourite", favourite)
                .add("notes", notes)
                .toString();
    }
}
