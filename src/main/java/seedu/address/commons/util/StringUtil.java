package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     *   Ignores case, but a partial word match is required.
     *   <br>examples:<pre>
     *       containsSubstringIgnoreCase("Alice Charles", "ali") == true
     *       containsSubstringIgnoreCase("Alice Charles", "rles") == true
     *       containsSubstringIgnoreCase("Bernice Yu", "nice u") == true
     *       </pre>
     * @param sentence The full name to be searched, it cannot be null.
     * @param word The keyword to search within the full name, it cannot be null or empty.
     * @return true If the {@code sentence} contains the {@code word}.
     */
    public static boolean containsSubstringIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String trimmedWord = word.trim();
        checkArgument(!trimmedWord.isEmpty(), "Word parameter cannot be empty");

        String preppedSentence = sentence.toLowerCase();
        String preppedWord = trimmedWord.toLowerCase();

        return preppedSentence.contains(preppedWord);
    }

    /**
     *   Checks if a phone number contains a given substring.
     *   <br>examples:<pre>
     *       containsPhoneSubstring("91234567", "1234") == true
     *       containsPhoneSubstring("87654321", "876") == true
     *       containsPhoneSubstring("90123456", "789") == false
     *       </pre>
     * @param phoneNumber The phone number to be searched, it cannot be null.
     * @param substring The keyword to search within the phone number, it cannot be null or empty.
     * @return true If the {@code phoneNumber} contains the {@code substring}.
     */
    public static boolean containsPhoneSubstring(String phoneNumber, String substring) {
        requireNonNull(phoneNumber);
        requireNonNull(substring);

        String trimmedSubstring = substring.trim();
        checkArgument(!trimmedSubstring.isEmpty(), "Substring parameter cannot be empty");

        return phoneNumber.contains(trimmedSubstring);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
