package ashell.annotations.commands;

import ashell.annotations.operations.Value;

/**
 * Created by Manne Öhlund on 26/03/17.
 * Copyright © 2017 All rights reserved.
 */

public @interface TOYBOX {
    @Value String value() default "$";
}
