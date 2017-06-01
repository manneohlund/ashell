package ashell.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manne Öhlund on 24/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class ReflectionUtils {

    public static List invoke(Method method, Annotation annotation) {
        List params = new ArrayList();
        try {
            for (Object option : (Object[]) method.invoke(annotation)) {
                params.add(option.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return params;
    }

    public static <T>T invokeParam(Method method, T target) {
        try {
            return (T) method.invoke(target);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static Object invoke(Annotation targetAnnotation, Class<? extends Annotation> targetMethodAnnotation) {
        try {
            Method[] methods = targetAnnotation.annotationType().getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(targetMethodAnnotation)) {
                    return m.invoke(targetAnnotation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("Class '%s' does not have a method with '%s' annotation", targetAnnotation, targetMethodAnnotation));
    }
}
