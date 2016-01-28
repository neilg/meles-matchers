package io.meles.matchers;

import static io.meles.matchers.AfterMatcher.after;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.StringDescription;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AfterMatcherTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private DateTime now;
    private AfterMatcher matcherUnderTest;

    @Before
    public void setup() {
        now = DateTime.now();
        matcherUnderTest = after(now);
    }

    @Test
    public void afterSelfDoesNotMatch() {
        assertFalse(matcherUnderTest.matches(now));
    }

    @Test
    public void doesNotMatchEarlierTime() {
        assertFalse(matcherUnderTest.matches(now.minusMillis(1)));
    }

    @Test
    public void matchesLaterTime() {
        assertTrue(matcherUnderTest.matches(now.plusMillis(1)));
    }

    @Test
    public void rhsIsRequired() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("null rhs");
        after(null);
    }

    @Test
    public void description() {
        final StringDescription description = new StringDescription();
        matcherUnderTest.describeTo(description);

        assertThat(description.toString(), is("after <" + now.toString() + ">"));
    }

    @Test
    public void mismatchDescription() {
        final StringDescription mismatchDescription = new StringDescription();
        final DateTime lhs = now.minusMillis(1);
        matcherUnderTest.matchesSafely(lhs, mismatchDescription);

        assertThat(mismatchDescription.toString(), is("<" + lhs.toString() + "> is not after <" + now.toString() + ">"));
    }

}