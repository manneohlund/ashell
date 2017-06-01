package ashell.annotations.params;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ashell.annotations.operations.Value;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Manne Öhlund on 23/03/17.
 * Copyright © 2017 All rights reserved.
 */

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Param {
    @Value String value();
}
