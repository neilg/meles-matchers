package io.meles.matchers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.experimental.theories.ParametersSuppliedBy;

@ParametersSuppliedBy(DateTimeSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dates {

    int random() default 100;
}
