package org.melvin.moderation.util;

import java.util.HashMap;
import java.util.Map;

public class OffenseMap {

    private static final Map<String, String> offenses = new HashMap<>();

    static {
        offenses.put("mci", "Minor Chat Infraction");
        offenses.put("mji", "Major Chat Infraction");
        offenses.put("at", "Abusive Trading");
    }

    public static String resolve(String key) {
        return offenses.getOrDefault(key.toLowerCase(), key);
    }

    public static void add(String key, String value) {
        offenses.put(key.toLowerCase(), value);
    }
}
