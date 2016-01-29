package io.meles.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.joda.time.ReadableInstant;

public class BeforeMatcher extends TypeSafeDiagnosingMatcher<ReadableInstant> {

    private final ReadableInstant rhs;

    public BeforeMatcher(final ReadableInstant rhs) {
        this.rhs = rhs;
    }

    @Factory
    public static BeforeMatcher before(final ReadableInstant rhs) {
        if (rhs == null) {
            throw new NullPointerException("null rhs");
        }
        return new BeforeMatcher(rhs);
    }

    @Override
    protected boolean matchesSafely(final ReadableInstant lhs, final Description mismatchDescription) {
        final boolean matches = lhs.isBefore(rhs);
        if (!matches) {
            mismatchDescription
                    .appendValue(lhs)
                    .appendText(" is not before ")
                    .appendValue(rhs);
        }
        return matches;
    }

    @Override
    public void describeTo(final Description description) {
        description
                .appendText("before ")
                .appendValue(rhs);
    }
}
