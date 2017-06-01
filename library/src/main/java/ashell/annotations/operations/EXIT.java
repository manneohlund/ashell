package ashell.annotations.operations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ashell.annotations.Operation;

/**
 * Created by Manne Öhlund on 23/03/17.
 * Copyright © 2017 All rights reserved.
 */

@Retention(RetentionPolicy.RUNTIME)
@Operation
public @interface EXIT {
    @Value String value() default "exit";
}
