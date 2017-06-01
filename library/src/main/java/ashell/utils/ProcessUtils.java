package ashell.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Manne Öhlund on 26/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class ProcessUtils {
    public static ProcessBuilder getDefaultProcessBuilder(String... commands) {
        return getDefaultProcessBuilder(Arrays.asList(commands));
    }

    public static ProcessBuilder getDefaultProcessBuilder(List<String> commands) {
        return new ProcessBuilder(commands);
    }

    public static long getPid(Process p) {
        long pid = -1;
        synchronized (p) {
            try {
                if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
                    Field f = p.getClass().getDeclaredField("pid");
                    f.setAccessible(true);
                    pid = f.getLong(p);
                    f.setAccessible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pid;
    }
}
