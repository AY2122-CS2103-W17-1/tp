package seedu.contax.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.contax.testutil.AppointmentBuilder;
import seedu.contax.testutil.PersonBuilder;





public class PersonNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonNameContainsKeywordsPredicate firstPredicate =
                new PersonNameContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonNameContainsKeywordsPredicate secondPredicate =
                new PersonNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonNameContainsKeywordsPredicate firstPredicateCopy =
                new PersonNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonNameContainsKeywordsPredicate predicate =
                new PersonNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new AppointmentBuilder().withName("Play").withPerson(
                new PersonBuilder().withName("Alice").build()
        ).build()));

        // Multiple keywords
        predicate = new PersonNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new AppointmentBuilder().withName("Play")
                .withPerson(
                    new PersonBuilder().withName("Alice Bob").build()
                )
                .build()));

        // Only one matching keyword
        predicate = new PersonNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new AppointmentBuilder().withPerson(
                new PersonBuilder().withName("Alice Bob").build()
        ).withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new AppointmentBuilder().withPerson(
                new PersonBuilder().withName("Alice Bob").build()
        ).withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonNameContainsKeywordsPredicate predicate =
                new PersonNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AppointmentBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new AppointmentBuilder().withName("Play").withPerson(
                new PersonBuilder().withName("Alice Bob").build()
        ).build()));
    }
}



