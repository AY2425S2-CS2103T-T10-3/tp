package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_ID = "The person ID provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESAGE_MISSING_PREFIX = "Missing prefix: %1$s";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Id: ")
                .append(person.getId())
                .append("; Name: ")
                .append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Course: ")
                .append(person.getCourse());

        return builder.toString();
    }

    /**
     * Formats the {@code updatedFields} to be displayed as output for Edit command.
     *
     * @param oldId original ID to identify person edited.
     */
    public static String editFormat(Id oldId, List<Pair<String, String>> updatedFields) {
        StringBuilder sb = new StringBuilder();
        sb.append("Original Id: " + oldId + ", ");

        for (Pair<String, String> p : updatedFields) {
            sb.append(p.getKey() + ": " + p.getValue() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // remove last comma

        return sb.toString();
    }

    /**
     * Returns an error message indicating the multiple prefixes.
     *
     * @param presentPrefixes the prefixes that are present multiple times
     */
    public static String getErrorMessageForMultiplePrefixes(Prefix[] presentPrefixes) {
        assert presentPrefixes.length > 1;

        Set<String> presentFields =
                Stream.of(presentPrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS
                + String.join(" ", presentFields);
    }

    /**
     * Returns an error message indicating the missing prefix.
     *
     * @param prefix the prefix that is missing
     */
    public static String getErrorMessageForMissingPrefix(Prefix prefix) {
        return String.format(MESAGE_MISSING_PREFIX, prefix);
    }
}
