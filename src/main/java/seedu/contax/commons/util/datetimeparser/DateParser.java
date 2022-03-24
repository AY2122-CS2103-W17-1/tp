package seedu.contax.commons.util.datetimeparser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * Provides parsing services for date inputs used in the application.
 */
public class DateParser {

    /**
     * Parses a date input string to a {@code LocalDate} object.
     *
     * @param input The input date string to parse.
     * @return An Optional container that contains a LocalDate object if parsing was successful, or an empty
     *         Optional container if parsing was unsuccessful.
     */
    public static Optional<LocalDate> parseDate(String input) {
        requireNonNull(input);

        return matchStandardFormat(input).flatMap(DateParser::parseStandardFormat)
            .or(() -> matchNaturalFormat(input).flatMap(DateParser::parseNaturalFormat));
    }

    /**
     * Returns a {@link Matcher} object if the input is in the standard date format, contained in an
     * {@code Optional}, and an empty {@code Optional} otherwise.
     *
     * @param input The input date string to match.
     * @return A {@code Matcher} object in an Optional container if the supplied input matches, or an empty
     *         optional otherwise.
     */
    private static Optional<Matcher> matchStandardFormat(String input) {
        Matcher match = DateParserPatternProvider.STANDARD_DATE_PATTERN.matcher(input);
        if (!match.matches()) {
            return Optional.empty();
        }
        return Optional.of(match);
    }

    /**
     * Parses a standard date format match into a {@link LocalDate} object.
     *
     * @param match The match object generated from matching an input string against
     *              {@link DateParserPatternProvider#STANDARD_DATE_PATTERN}.
     * @return A {@code LocalDate} object contained in an {@code Optional} if the input is valid, or an
     *         empty {@code Optional} otherwise.
     */
    private static Optional<LocalDate> parseStandardFormat(Matcher match) {
        try {
            int day = Integer.parseInt(match.group(1));
            int month = Integer.parseInt(match.group(2));
            int year = Integer.parseInt(match.group(3));
            return Optional.of(LocalDate.of(year, month, day));
        } catch (NumberFormatException | DateTimeException ex) {
            return Optional.empty();
        }
    }

    /**
     * Returns a {@link Matcher} object if the input matches a natural date format, contained in an
     * {@code Optional}, and an empty {@code Optional} otherwise.
     * See {@link DateParserPatternProvider#NATURAL_DATE_PATTERN} for more details on the valid natural
     * date patterns.
     *
     * @param input The input date string to match.
     * @return A {@code Matcher} object in an Optional container if the supplied input matches, or an empty
     *         optional otherwise.
     */
    private static Optional<Matcher> matchNaturalFormat(String input) {
        final String paddedLowerCaseInput = " " + input.toLowerCase() + " ";
        Matcher match = DateParserPatternProvider.NATURAL_DATE_PATTERN.matcher(paddedLowerCaseInput);
        if (!match.matches()) {
            return Optional.empty();
        }
        return Optional.of(match);
    }

    /**
     * Parses a natural date format match into a {@link LocalDate} object.
     *
     * @param match The match object generated from matching an input string against
     *              {@link DateParserPatternProvider#NATURAL_DATE_PATTERN}.
     * @return A {@code LocalDate} object contained in an {@code Optional} if the input is valid, or an
     *         empty {@code Optional} otherwise.
     */
    private static Optional<LocalDate> parseNaturalFormat(Matcher match) {
        try {
            int day = Integer.parseInt(match.group(1));
            int month = DateParserPatternProvider.monthStringToDecimal(match.group(2));
            int year = Integer.parseInt(match.group(3));

            if (month < 1) {
                return Optional.empty();
            }

            return Optional.of(LocalDate.of(year, month, day));
        } catch (NumberFormatException | DateTimeException ex) {
            return Optional.empty();
        }
    }
}