package org.melvin.moderation.features;

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

                break;
            }
        }
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
