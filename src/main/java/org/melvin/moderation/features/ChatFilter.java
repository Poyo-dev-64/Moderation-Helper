package org.melvin.moderation.features;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.melvin.moderation.events.RenderHandler;
import org.melvin.moderation.util.ChatRules;

public class ChatFilter {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {

        if (event.type != 0) return;

        String original = event.message.getUnformattedText();

        String user = extractUser(original);
        String message = extractMessage(original);

        String msg = message.toLowerCase();

        String[] words = msg.split("[^a-z0-9]+");

        for (ChatRules.ChatRule rule : ChatRules.RULES) {

            for (String w : words) {
                if (w.equals(rule.word)) {
                    triggerWarning(user, rule);
                    return;
                }
            }
        }
    }

private String extractUser(String msg) {

    int colon = msg.indexOf(':');
    if (colon == -1) return "Unknown";

    String left = msg.substring(0, colon).replaceAll("§.", "").trim();

    String[] parts = left.split("\\s+");
    return parts.length > 0 ? parts[parts.length - 1] : "Unknown";
}

    private String extractMessage(String msg) {

        int colonIndex = msg.indexOf(':');
        if (colonIndex == -1) return msg;

        return msg.substring(colonIndex + 1).trim();
    }

    private void triggerWarning(String user, ChatRules.ChatRule rule) {

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
}

