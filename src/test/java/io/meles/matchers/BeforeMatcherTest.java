package io.meles.matchers;

import static io.meles.matchers.BeforeMatcher.before;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import org.hamcrest.StringDescription;
import org.joda.time.ReadableInstant;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class BeforeMatcherTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void rhsIsRequired() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("null rhs");
        before(null);
    }

    @Theory
    public void doesNotMatchSelf(final @Dates ReadableInstant instant) {
        assertFalse(before(instant).matches(instant));
    }

    @Theory
    public void lhsBeforeRhsMatches(final @Dates ReadableInstant lhs, final @Dates ReadableInstant rhs) {
        assumeTrue(lhs.isBefore(rhs));

        assertTrue(before(rhs).matches(lhs));
    }

    @Theory
    public void lhsAfterRhsDoesNotMatch(final @Dates ReadableInstant lhs, final @Dates ReadableInstant rhs) {
        assumeTrue(lhs.isAfter(rhs));

        assertFalse(before(rhs).matches(lhs));
    }

    @Theory
    public void dateIsUsedInDescription(final @Dates ReadableInstant instant) {
        final StringDescription description = new StringDescription();

        before(instant).describeTo(description);

        assertThat(description.toString(), is("before <" + instant.toString() + ">"));
    }

    @Theory
    public void mismatchDescriptionIsEmptyForMatch(final @Dates ReadableInstant lhs, final @Dates ReadableInstant rhs) {
        final StringDescription mismatchDescription = new StringDescription();

        assumeThat(rhs, is(notNullValue()));
        assumeTrue(before(rhs).matchesSafely(lhs, mismatchDescription));

        assertThat(mismatchDescription.toString(), isEmptyString());
    }

    @Theory
    public void mismatchDescriptionIsProvidedForMismatch(final @Dates ReadableInstant lhs, final @Dates ReadableInstant rhs) {
        final StringDescription mismatchDescription = new StringDescription();

        assumeThat(rhs, is(notNullValue()));
        assumeFalse(before(rhs).matchesSafely(lhs, mismatchDescription));

        assertThat(mismatchDescription.toString(), is("<" + lhs.toString() + "> is not before <" + rhs.toString() + ">"));
    }

}