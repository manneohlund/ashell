package ashell.utils;

import java.util.List;

/**
 * Created by Manne Öhlund on 24/03/17.
 * Copyright © 2017 All rights reserved.
 */

public class StringUtils {

    public static String listToString(List<String> commands) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < commands.size(); i++) {
            stringBuilder.append(commands.get(i));
            if (i < commands.size()-1) {
                stringBuilder.append(" ");
            }
        }

        return stringBuilder.toString();
    }
}
