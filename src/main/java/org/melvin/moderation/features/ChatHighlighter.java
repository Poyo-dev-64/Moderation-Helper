package org.melvin.moderation.features;

public class ChatHighlighter {

    private static final String[] KEYWORDS = {"admin", "mod", "help"};

    public static void process(String msg) {

        String lower = msg.toLowerCase();

        for (String keyword : KEYWORDS) {
            if (lower.contains(keyword)) {
                System.out.println("[HIGHLIGHT] " + msg);
                return;
            }
        }
    }
}
