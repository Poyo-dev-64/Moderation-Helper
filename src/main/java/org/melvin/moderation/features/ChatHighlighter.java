package org.melvin.moderation.features;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatHighlighter {

    public static IChatComponent highlight(IChatComponent original, String word) {

        String text = original.getFormattedText();
        String lower = text.toLowerCase();

        int index = lower.indexOf(word);
        if (index == -1) return original;

        String before = text.substring(0, index);
        String match = text.substring(index, index + word.length());
        String after = text.substring(index + word.length());

        ChatComponentText result = new ChatComponentText("");

        result.appendSibling(new ChatComponentText(before));
        result.appendSibling(new ChatComponentText("§c§l" + match));
        result.appendSibling(new ChatComponentText(after));

        return result;
    }
}
