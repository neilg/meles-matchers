package io.meles.matchers;

import static io.meles.matchers.AfterMatcher.after;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import java.util.Random;

import org.hamcrest.StringDescription;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class AfterMatcherTest {

    @DataPoint
    public static final DateTime minDate = new DateTime(Long.MIN_VALUE);
    @DataPoint
    public static final DateTime maxDate = new DateTime(Long.MAX_VALUE);
    @DataPoint
    public static final DateTime epoch = new DateTime(0L);
    @DataPoint
    public static final DateTime now = DateTime.now();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @DataPoints
    public static DateTime[] randomDates() {
        final int len = 100;
        final DateTime[] result = new DateTime[len];
        final Random r = new Random();
        for (int i = 0; i < len; i++) {
            result[i] = new DateTime(r.nextLong());
        }
        return result;
    }

    @Test
    public void rhsIsRequired() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("null rhs");
        after(null);
    }

    @Theory
    public void doesNotMatchSelf(final ReadableInstant instant) {
        assertFalse(after(instant).matches(instant));
    }

    @Theory
    public void lhsAfterRhsMatches(final ReadableInstant lhs, final ReadableInstant rhs) {
        assumeTrue(lhs.isAfter(rhs));

        assertTrue(after(rhs).matches(lhs));
    }

    @Theory
    public void lhsBeforeRhsDoesNotMatch(final ReadableInstant lhs, final ReadableInstant rhs) {
        assumeTrue(lhs.isBefore(rhs));

        assertFalse(after(rhs).matches(lhs));
    }

    @Theory
    public void dateIsUsedInDescription(final ReadableInstant instant) {
        final StringDescription description = new StringDescription();

        after(instant).describeTo(description);

        assertThat(description.toString(), is("after <" + instant.toString() + ">"));
    }

    @Theory
    public void mismatchDescriptionIsEmptyForMatch(final ReadableInstant lhs, final ReadableInstant rhs) {
        final StringDescription mismatchDescription = new StringDescription();

        assumeThat(rhs, is(notNullValue()));
        assumeTrue(after(rhs).matchesSafely(lhs, mismatchDescription));

        assertThat(mismatchDescription.toString(), isEmptyString());
    }

    @Theory
    public void mismatchDescriptionIsProvidedForMismatch(final ReadableInstant lhs, final ReadableInstant rhs) {
        final StringDescription mismatchDescription = new StringDescription();

        assumeThat(rhs, is(notNullValue()));
        assumeFalse(after(rhs).matchesSafely(lhs, mismatchDescription));

        assertThat(mismatchDescription.toString(), is("<" + lhs.toString() + "> is not after <" + rhs.toString() + ">"));
    }

}
