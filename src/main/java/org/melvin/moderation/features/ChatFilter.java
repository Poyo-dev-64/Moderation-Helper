package org.melvin.moderation.features;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.melvin.moderation.events.RenderHandler;

public class ChatFilter {

    private static final ChatRule[] RULES = {
            new ChatRule("badword1", "1h", "mute", "mci"),
            new ChatRule("badword2", "30d", "mute", "mji")
    };

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {

        String original = event.message.getUnformattedText();
        String msg = original.toLowerCase();

        String user = extractUser(original);

        for (ChatRule rule : RULES) {

            if (msg.contains(rule.word)) {

                triggerWarning(user, rule);

                // 🔥 APPLY HIGHLIGHT
                event.message = highlight(event.message, rule.word);

                break;
            }
        }
    }

    private IChatComponent highlight(IChatComponent original, String word) {

        String text = original.getFormattedText();
        String lower = text.toLowerCase();

        int index = lower.indexOf(word);
        if (index == -1) return original;

        String before = text.substring(0, index);
        String match = text.substring(index, index + word.length());
        String after = text.substring(index + word.length());

        ChatComponentText result = new ChatComponentText("");

        result.appendSibling(new ChatComponentText(before));

        // highlighted word
        result.appendSibling(new ChatComponentText("§c§l" + match));

        result.appendSibling(new ChatComponentText(after));

        return result;
    }

    private void triggerWarning(String user, ChatRule rule) {

        String command = "/fmpunish "
                + rule.action + " "
                + user + " "
                + rule.duration + " "
                + rule.code;

        RenderHandler.INSTANCE.showFlag(
                user,
                rule.word,
                command
        );
    }

    private String extractUser(String msg) {

        if (!msg.contains(":")) return "Unknown";

        try {
            String left = msg.split(":")[0].trim();
            String[] parts = left.split(" ");

            return parts.length > 0 ? parts[parts.length - 1] : "Unknown";

        } catch (Exception e) {
            return "Unknown";
        }
    }

    private static class ChatRule {
        String word;
        String duration;
        String action;
        String code;

        ChatRule(String word, String duration, String action, String code) {
            this.word = word;
            this.duration = duration;
            this.action = action;
            this.code = code;
        }
    }
}
