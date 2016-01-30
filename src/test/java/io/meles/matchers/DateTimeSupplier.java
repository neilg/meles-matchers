package io.meles.matchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class DateTimeSupplier extends ParameterSupplier {

    private static final Random RANDOM = new Random();

    @Override
    public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
        final Dates dates = sig.getAnnotation(Dates.class);
        final int randomCount = dates.random();
        final List<PotentialAssignment> assignments = new ArrayList<>();
        assignments.add(minDate());
        assignments.add(maxDate());
        assignments.add(epochDate());
        assignments.addAll(randomDates(randomCount));
        return assignments;
    }

    private PotentialAssignment minDate() {
        return PotentialAssignment.forValue("min date", new DateTime(Long.MIN_VALUE));
    }

    private PotentialAssignment maxDate() {
        return PotentialAssignment.forValue("max date", new DateTime(Long.MAX_VALUE));
    }

    private PotentialAssignment epochDate() {
        return PotentialAssignment.forValue("epoch", new DateTime(0L));
    }

    private Collection<PotentialAssignment> randomDates(int count) {
        final List<PotentialAssignment> randomDates = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            randomDates.add(randomDate());
        }
        return randomDates;
    }

    private PotentialAssignment randomDate() {
        return PotentialAssignment.forValue("random", new DateTime(RANDOM.nextLong()));
    }
}
