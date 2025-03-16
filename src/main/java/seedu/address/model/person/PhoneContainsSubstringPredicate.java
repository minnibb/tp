package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} contains the given substring.
 */
public class PhoneContainsSubstringPredicate implements Predicate<Person> {
    private final String keyword;

    public PhoneContainsSubstringPredicate(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsPhoneSubstring(person.getPhone().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsSubstringPredicate)) {
            return false;
        }

        PhoneContainsSubstringPredicate otherPhoneContainsSubstringPredicate = (PhoneContainsSubstringPredicate) other;
        return keyword.equals(otherPhoneContainsSubstringPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keyword).toString();
    }
}
