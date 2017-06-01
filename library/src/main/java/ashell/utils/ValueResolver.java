package ashell.utils;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ashell.annotations.params.Paths;

/**
 * Created by Manne Öhlund on 28/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class ValueResolver {

    public static String resolve(Annotation[] parameterAnnotations, Object value) {
        StringBuilder stringBuilder = new StringBuilder();
        if (value instanceof Object[]) {
            for (Object o : (Object[]) value) {
                stringBuilder.append(isPaths(parameterAnnotations, o.toString()));
                stringBuilder.append(" ");
            }
        } else if (value instanceof List) {
            for (Object o : (List) value) {
                stringBuilder.append(isPaths(parameterAnnotations, o.toString()));
            }
        } else if (value instanceof Map) {
            Set<Map.Entry> entries = ((Map) value).entrySet();
            for (Iterator<Map.Entry> i = entries.iterator(); i.hasNext(); ) {
                Map.Entry entry = i.next();
                stringBuilder.append(entry.getKey());
                stringBuilder.append(" ");
                stringBuilder.append(entry.getValue());
                stringBuilder.append(" ");
            }
        } else {
            stringBuilder.append(isPaths(parameterAnnotations, value.toString()));
        }

        return stringBuilder.toString().trim();
    }

    public static String isPaths(Annotation[] parameterAnnotations, String value) {
        boolean isPaths = false;
        for (Annotation annotation : parameterAnnotations) {
            if (annotation.annotationType().isAssignableFrom(Paths.class)) {
                isPaths = true;
            }
        }

        if (!isPaths || (value.startsWith("\"") || value.startsWith("'"))) {
            return value;
        } else {
            return String.format("\"%s\"", value);
        }
    }
}
