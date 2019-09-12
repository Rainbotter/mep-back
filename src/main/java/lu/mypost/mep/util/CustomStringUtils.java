package lu.mypost.mep.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

public class CustomStringUtils {

    public static String formatNameToId(String name) {
        name = name.replace(" ", "_");
        name = name.replace(".", "_");
        name = StringUtils.stripAccents(name);
        return StringEscapeUtils.escapeJava(name);
    }
}
