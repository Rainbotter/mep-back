package lu.mypost.mep.util;

import org.apache.commons.text.StringEscapeUtils;

public class StringUtils {

    public static String formatNameToId(String name) {
        name = name.replace(" ", "_");
        name = name.replace(".", "_");
        return StringEscapeUtils.escapeJava(name);
    }
}
