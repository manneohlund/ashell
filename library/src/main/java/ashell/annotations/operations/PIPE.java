package ashell.annotations.operations;

import java.lang.annotation.Retention;

import ashell.annotations.Operation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Manne Öhlund on 23/03/17.
 * Copyright © 2017 All rights reserved.
 */

@Retention(RUNTIME)
@Operation
public @interface PIPE {
    @Value String value() default "|";
}
