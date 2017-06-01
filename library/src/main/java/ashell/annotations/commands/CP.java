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
 * usage: cp [-R [-H | -L | -P]] [-fi | -n] [-apvX] source_file target_file<br />
 * cp [-R [-H | -L | -P]] [-fi | -n] [-apvX] source_file ... target_directory
 */
@Retention(RetentionPolicy.RUNTIME)
@Command
public @interface CP {
    @Value String value() default "";
}