package ashell.utils;

import java.lang.annotation.Annotation;

/**
 * Created by Manne Öhlund on 24/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class CommandUtils {
    public static String getName(Annotation annotation) {
        return annotation.annotationType().getSimpleName().toLowerCase();
    }
}
