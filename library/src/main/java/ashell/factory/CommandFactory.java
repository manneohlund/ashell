package ashell.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ashell.annotations.Command;
import ashell.annotations.Operation;
import ashell.annotations.commands.CMD;
import ashell.annotations.operations.EXIT;
import ashell.annotations.operations.Value;
import ashell.annotations.params.Param;
import ashell.utils.CommandUtils;
import ashell.utils.ReflectionUtils;
import ashell.utils.StringUtils;
import ashell.utils.ValueResolver;

/**
 * Created by Manne Öhlund on 26/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class CommandFactory {
    public static List<String> build(Method method, Object[] args) {
        Annotation[] annotations = method.getAnnotations();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        // Result command list
        List<String> commands = new ArrayList<>();

        // Build command from annotations
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(Operation.class)) {
                // Add operation
                Object o = ReflectionUtils.invoke(annotation, Value.class);
                commands.add(o.toString());
            } else if (annotationType.isAnnotationPresent(Command.class)) {
                // Add cmd
                if (!(annotation instanceof CMD)) {                                                 //TODO: 29/03/17 Make a list of EXCEPTIONS
                    commands.add(CommandUtils.getName(annotation));
                }

                // Invoke PARAMS methods
                Method[] methods = annotationType.getMethods();
                for (Method m : methods) {
                    if (m.isAnnotationPresent(Value.class)) {
                        Object value = ReflectionUtils.invokeParam(m, annotation);
                        commands.add(value.toString());
                    }
                }
            }
        }

        // Populate with params annotations
        for (int j = 0; j < parameterAnnotations.length; j++) {
            for (int k = 0; k < parameterAnnotations[j].length; k++) {
                Annotation paramAnnotation = parameterAnnotations[j][k];
                if (paramAnnotation.annotationType().isAssignableFrom(Param.class)) {
                    //System.out.println(parameterAnnotations[j][k]);
                    Object arg = args[j];
                    String param = ValueResolver.resolve(parameterAnnotations[j], arg);
                    for (int i = 0; i < commands.size(); i++) {
                        String paramId = "{" + ((Param)paramAnnotation).value() + "}";
                        if (commands.get(i).contains(paramId)) {
                            commands.set(i, commands.get(i).replace(paramId, param));
                        }
                        // VALIDATE??
                    }
                }
            }
        }

        /* VALIDATE
        if (i == commands.size() -1) {
            throw new RuntimeException(String.format("Param id '%s' not found in command '%s'", paramId, StringUtils.listToString(commands)));
        }
         */

        // Print
        System.out.println("\n####### COMMAND #######");
        System.out.println(StringUtils.listToString(commands));

        // Echo command done
        if (!(annotations[annotations.length-1] instanceof EXIT)) {
            commands.add("\n echo 'CMD_DONE'");
            commands.add("&& echo 'CMD_DONE' >&2");
        }

        return commands;
    }
}
