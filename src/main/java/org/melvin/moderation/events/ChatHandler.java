package org.melvin.moderation.events;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;

import java.util.HashMap;

public class ChatHandler {

    private static String lastMessage = "";

    private final HashMap<String, String> lastMsg = new HashMap<>();
    private final HashMap<String, Integer> repeatCount = new HashMap<>();
    private final HashMap<String, Long> expireTime = new HashMap<>();

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {

        IChatComponent original = event.message;
        String plain = original.getUnformattedText();

        if (isStaffChat(plain)) return;

        String username = extractUsername(plain);
        if (username == null) return;

        username = sanitize(username);

        long now = System.currentTimeMillis();
        String msg = plain.toLowerCase();

        IChatComponent rebuilt = original.createCopy();

        if (isCharFlood(msg)) {
            triggerFlood(username);
        }

        // DUPLICATE SPAM CHECK
        String prev = lastMsg.get(username);
        int count = repeatCount.getOrDefault(username, 1);
        long expire = expireTime.getOrDefault(username, 0L);

        if (prev != null && prev.equalsIgnoreCase(msg) && now <= expire) {

            count++;
            repeatCount.put(username, count);
            expireTime.put(username, now + 30000);

            if (count >= 2 && count <= 3) {
                rebuilt.appendText(" §7(" + count + ")");
            }

            if (count >= 3) {
                triggerSpam(username);
                repeatCount.put(username, 0);
                expireTime.remove(username);
            }

        } else {

            lastMsg.put(username, msg);
            repeatCount.put(username, 1);
            expireTime.put(username, now + 30000);
        }

        lastMessage = plain;

        ChatComponentText mute = new ChatComponentText(" §7[§cMUTE§7]");
        mute.setChatStyle(new ChatStyle()
                .setChatClickEvent(new ClickEvent(
                        ClickEvent.Action.SUGGEST_COMMAND,
                        "/fmpunish mute " + username + " 1h"
                ))
                .setChatHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ChatComponentText("Suggest mute")
                ))
        );

        ChatComponentText copy = new ChatComponentText(" §7[§bCOPY§7]");
        copy.setChatStyle(new ChatStyle()
                .setChatClickEvent(new ClickEvent(
                        ClickEvent.Action.RUN_COMMAND,
                        "/fm_copy"
                ))
                .setChatHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ChatComponentText("Copy message")
                ))
        );

        rebuilt.appendSibling(mute);
        rebuilt.appendSibling(copy);

        event.message = rebuilt;
    }

    private void triggerSpam(String user) {
        String command = "/fmpunish mute " + user + " 1h mci";
        RenderHandler.INSTANCE.showFlag(user, "spam", command);
    }

    private void triggerFlood(String user) {
        String command = "/fmpunish mute " + user + " 1h mci";
        RenderHandler.INSTANCE.showFlag(user, "chatflood", command);
    }

    private boolean isCharFlood(String msg) {

        int count = 1;

        for (int i = 1; i < msg.length(); i++) {

            if (msg.charAt(i) == msg.charAt(i - 1)) {
                count++;

                if (count >= 10) return true;
            } else {
                count = 1;
            }
        }

        return false;
    }

    public static String getLastMessage() {
        return lastMessage;
    }

    private String sanitize(String input) {
        return input.replaceAll("§.", "").replaceAll("[^a-zA-Z0-9_\\-]", "").trim();
    }

    private boolean isStaffChat(String msg) {
        return msg.contains("STAFF")
                || msg.contains("[SC]")
                || msg.contains("/sc")
                || msg.contains("Vanished Staff")
                || msg.contains("§fVanished Staff")
                || msg.contains("§fOnline Staff")
                || msg.contains("watch dog")
                || msg.contains("report on player")
                || msg.contains("open report menu")
                || msg.contains("watchdog announcement");
    }

    private String extractUsername(String msg) {
        if (!msg.contains(":")) return null;

        String left = msg.split(":")[0];
        String[] parts = left.trim().split(" ");

        return parts.length == 0 ? null : parts[parts.length - 1];
    }
}
