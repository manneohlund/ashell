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
 * usage:	chmod [-fhv] [-R [-H | -L | -P]] [-a | +a | =a  [i][# [ n]]] mode|entry file ... <br />
 * chmod [-fhv] [-R [-H | -L | -P]] [-E | -C | -N | -i | -I] file ...
 */
@Retention(RetentionPolicy.RUNTIME)
@Command
public @interface CHMOD {
    @Value String value() default "";
}