package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Class;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ROLE = "Student";
    public static final String DEFAULT_GRADE = "Sec 1";
    public static final String DEFAULT_CLASS = "1A";
    public static final String DEFAULT_PARENT = "Charles Bee";
    public static final boolean DEFAULT_FAVOURITE = false;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Role role;
    private Grade grade;
    private Class studentClass;
    private Name parentName;
    private Favourite favourite;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        role = new Role(DEFAULT_ROLE);
        grade = new Grade(DEFAULT_GRADE);
        studentClass = new Class(DEFAULT_CLASS);
        parentName = new Name(DEFAULT_PARENT);
        favourite = new Favourite(DEFAULT_FAVOURITE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        role = personToCopy.getRole();
        grade = personToCopy.getGrade();
        studentClass = personToCopy.getStudentClass();
        parentName = personToCopy.getParentName();
        favourite = personToCopy.getFavourite();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Student} that we are building.
     */
    public PersonBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    /**
     * Sets the {@code Class} of the {@code Student} that we are building.
     */
    public PersonBuilder withClass(String studentClass) {
        this.studentClass = new Class(studentClass);
        return this;
    }

    /**
     * Sets the {@code Name of Parent} of the {@code Student} that we are building.
     */
    public PersonBuilder withParent(String name) {
        this.parentName = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withFavourite(boolean favourite) {
        this.favourite = new Favourite(favourite);
        return this;
    }

    /**
     * Builds a {@code Person} with the attributes in this instance of {@code PersonBuilder}.
     */
    public Person build() {
        if (role.getType().equals(Role.Type.STUDENT)) {
            return new Person(name, phone, email, address, tags, role, grade, studentClass, parentName, favourite);
        } else {
            return new Person(name, phone, email, address, tags, role, favourite);
        }
    }

}
