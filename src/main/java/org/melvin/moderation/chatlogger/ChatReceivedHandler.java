package org.melvin.moderation.chatlogger;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatReceivedHandler {

    private final ChatLog cl;
    private String lastMessage = "";

    public ChatReceivedHandler(ChatLog cl) {
        this.cl = cl;
    }

    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {

        if (this.cl.configManager.props.getProperty("enabled", "true").equalsIgnoreCase("false")) {
            return;
        }


        if (event.type == 2) return;

        String message = event.message.getUnformattedText();


        if (message.equals(lastMessage)) return;
        lastMessage = message;


        if (message.matches(".*\\d+/\\d+.*(Mana|Health|Defense).*")) return;

        String noformat = message.replaceAll("\\xA7[A-Za-z0-9]", "");

        String[] strs = new String[]{};

        try {
            String propAF = URLDecoder.decode(
                this.cl.configManager.props.getProperty("filters", ""),
                "UTF-8"
            );
            strs = propAF.split("\u0000");
        } catch (UnsupportedEncodingException ignored) {}

        boolean match = strs.length <= 1;

        for (int i = 1; i < strs.length; i++) {
            Pattern p = Pattern.compile(strs[i]);
            Matcher m = p.matcher(noformat);

            if (m.find()) {
                match = true;
                break;
            }
        }

        if (match) {
            this.cl.logs.write(
                message,
                strs.length > 1 ? this.cl.util.getLogFlag("F") : ""
            );
        }
    }
}
