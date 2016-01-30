package io.meles.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.joda.time.ReadableInstant;

public final class AfterMatcher extends TypeSafeDiagnosingMatcher<ReadableInstant> {

    private final ReadableInstant rhs;

    public AfterMatcher(final ReadableInstant rhs) {
        if (rhs == null) {
            throw new NullPointerException("null rhs");
        }
        this.rhs = rhs;
    }

    @Factory
    public static AfterMatcher after(final ReadableInstant rhs) {
        return new AfterMatcher(rhs);
    }

    @Override
    protected boolean matchesSafely(final ReadableInstant lhs, final Description mismatchDescription) {
        final boolean matches = lhs.isAfter(rhs);
        if (!matches) {
            mismatchDescription
                    .appendValue(lhs)
                    .appendText(" is not after ")
                    .appendValue(rhs);
        }
        return matches;
    }

    @Override
    public void describeTo(final Description description) {
        description
                .appendText("after ")
                .appendValue(rhs);
    }

}
