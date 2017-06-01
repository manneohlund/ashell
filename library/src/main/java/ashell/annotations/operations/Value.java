package ashell.annotations.operations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Manne Öhlund on 23/03/17.
 * Copyright © 2017 All rights reserved.
 */

@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Value {
}
