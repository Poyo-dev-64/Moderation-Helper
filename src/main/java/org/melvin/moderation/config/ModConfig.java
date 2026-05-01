package org.melvin.moderation.config;

import java.io.*;
import java.util.*;

public class ModConfig {

    public static boolean enabled = true;
    public static boolean chatLogger = true;

    public static List<String> keywords = Arrays.asList("admin", "mod", "help");
    public static List<String> filteredWords = Arrays.asList("spam", "scam");

    private static final File file = new File("moderation.cfg");

    public static void load() {
        if (!file.exists()) return;

    }

    public static void save() {
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("enabled=" + enabled);
            pw.println("chatLogger=" + chatLogger);
        } catch (Exception ignored) {}
    }
}
