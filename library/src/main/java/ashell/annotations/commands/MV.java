package ashell.annotations.commands;

/**
 * Created by Manne Öhlund on 23/03/17.
 * Copyright © 2017 All rights reserved.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ashell.annotations.Command;
import ashell.annotations.operations.Value;

/**
 * usage: mv [-f | -i | -n] [-v] source target<br />
 * mv [-f | -i | -n] [-v] source ... directory
 */
@Retention(RetentionPolicy.RUNTIME)
@Command
public @interface MV {
    @Value String value() default "";
}