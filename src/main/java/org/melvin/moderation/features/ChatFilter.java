package org.melvin.moderation.features;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.melvin.moderation.events.RenderHandler;

public class ChatFilter {

    private static final ChatRule[] RULES = {
            new ChatRule("milf", "3h", "mute", "mci"),
            new ChatRule("dilf", "3h", "mute", "mci"),
            new ChatRule("stfu", "3h", "mute", "mci"),
            new ChatRule("mofo", "3h", "mute", "mci"),
            new ChatRule("mfs", "3h", "mute", "mci"),
            new ChatRule("gfys", "3h", "mute", "mci"),
            new ChatRule("gtfo", "3h", "mute", "mci"),
            new ChatRule("soab", "3h", "mute", "mci"),
            new ChatRule("kkk", "3h", "mute", "mci"),
            new ChatRule("dtf", "3h", "mute", "mci"),
            new ChatRule("bkl", "3h", "mute", "mci"),
            new ChatRule("bsdk", "3h", "mute", "mci"),
            new ChatRule("jhatu", "3h", "mute", "mci"),
            new ChatRule("tmkc", "3h", "mute", "mci"),
            new ChatRule("bbc", "3h", "mute", "mci"),
            new ChatRule("aand", "6h", "mute", "mci"),
            new ChatRule("behenchod", "6h", "mute", "mci"),
            new ChatRule("bhadwa", "6h", "mute", "mci"),
            new ChatRule("bhosda", "6h", "mute", "mci"),
            new ChatRule("bhosdike", "6h", "mute", "mci"),
            new ChatRule("bhosdiwala", "6h", "mute", "mci"),
            new ChatRule("bhosadchod", "6h", "mute", "mci"),
            new ChatRule("bur", "6h", "mute", "mci"),
            new ChatRule("chuchi", "6h", "mute", "mci"),
            new ChatRule("gaand", "6h", "mute", "mci"),
            new ChatRule("gandu", "6h", "mute", "mci"),
            new ChatRule("haramjada", "6h", "mute", "mci"),
            new ChatRule("lulli", "6h", "mute", "mci"),
            new ChatRule("lauda", "6h", "mute", "mci"),
            new ChatRule("lund", "6h", "mute", "mci"),
            new ChatRule("lodu", "6h", "mute", "mci"),
            new ChatRule("madarchod", "6h", "mute", "mci"),
            new ChatRule("mooth", "6h", "mute", "mci"),
            new ChatRule("randi", "6h", "mute", "mci"),
            new ChatRule("raand", "6h", "mute", "mci"),
            new ChatRule("choot", "6h", "mute", "mci"),
            new ChatRule("chut", "6h", "mute", "mci"),
            new ChatRule("drug", "6h", "mute", "mci"),
            new ChatRule("ganja", "6h", "mute", "mci"),
            new ChatRule("charas", "6h", "mute", "mci"),
            new ChatRule("cocaine", "6h", "mute", "mci"),
            new ChatRule("boob", "6h", "mute", "mci"),
            new ChatRule("nipple", "6h", "mute", "mci"),
            new ChatRule("douche", "6h", "mute", "mci"),
            new ChatRule("vape", "6h", "mute", "mci"),
            new ChatRule("juul", "6h", "mute", "mci"),
            new ChatRule("cigar", "6h", "mute", "mci"),
            new ChatRule("stripper", "6h", "mute", "mci"),
            new ChatRule("ass", "6h", "mute", "mci"),
            new ChatRule("anal", "6h", "mute", "mci"),
            new ChatRule("arse", "6h", "mute", "mci"),
            new ChatRule("anus", "6h", "mute", "mci"),
            new ChatRule("rimming", "6h", "mute", "mci"),
            new ChatRule("ballsack", "6h", "mute", "mci"),
            new ChatRule("nutsack", "6h", "mute", "mci"),
            new ChatRule("bitch", "6h", "mute", "mci"),
            new ChatRule("bastard", "6h", "mute", "mci"),
            new ChatRule("blowjob", "6h", "mute", "mci"),
            new ChatRule("handjob", "6h", "mute", "mci"),
            new ChatRule("succ", "6h", "mute", "mci"),
            new ChatRule("sex", "6h", "mute", "mci"),
            new ChatRule("fuck", "6h", "mute", "mci"),
            new ChatRule("cum", "6h", "mute", "mci"),
            new ChatRule("jizz", "6h", "mute", "mci"),
            new ChatRule("orgasm", "6h", "mute", "mci"),
            new ChatRule("boner", "6h", "mute", "mci"),
            new ChatRule("fucked", "6h", "mute", "mci"),
            new ChatRule("masturbate", "6h", "mute", "mci"),
            new ChatRule("wank", "6h", "mute", "mci"),
            new ChatRule("penis", "6h", "mute", "mci"),
            new ChatRule("dick", "6h", "mute", "mci"),
            new ChatRule("cock", "6h", "mute", "mci"),
            new ChatRule("porn", "6h", "mute", "mci"),
            new ChatRule("hentai", "6h", "mute", "mci"),
            new ChatRule("shit", "6h", "mute", "mci"),
            new ChatRule("bitch", "6h", "mute", "mci"),
            new ChatRule("tits", "6h", "mute", "mci"),
            new ChatRule("vagina", "6h", "mute", "mci"),
            new ChatRule("pussy", "6h", "mute", "mci"),
            new ChatRule("twat", "6h", "mute", "mci"),
            new ChatRule("cunt", "6h", "mute", "mci"),
            new ChatRule("clitoris", "6h", "mute", "mci"),
            new ChatRule("urethra", "6h", "mute", "mci"),
            new ChatRule("whore", "6h", "mute", "mci"),
            new ChatRule("prostitute", "6h", "mute", "mci"),
            new ChatRule("thot", "6h", "mute", "mci"),
            new ChatRule("slut", "6h", "mute", "mci"),
            new ChatRule("trafficking", "6h", "mute", "mci"),
            new ChatRule("wanker", "6h", "mute", "mci"),
            new ChatRule("nigger", "40d", "mute", "mji"),
            new ChatRule("nigga", "40d", "mute", "mji"),
            new ChatRule("honkey", "40d", "mute", "mji"),
            new ChatRule("beaner", "40d", "mute", "mji"),
            new ChatRule("chink", "40d", "mute", "mji"),
            new ChatRule("dalit", "40d", "mute", "mji"),
            new ChatRule("retard", "40d", "mute", "mji"),
            new ChatRule("pedo", "40d", "mute", "mji"),
            new ChatRule("pedophile", "40d", "mute", "mji"),
            new ChatRule("paedophile", "40d", "mute", "mji"),
            new ChatRule("molestation", "40d", "mute", "mji"),
            new ChatRule("molester", "40d", "mute", "mji"),
            new ChatRule("gay", "40d", "mute", "mji"),
            new ChatRule("lesbian", "40d", "mute", "mji"),
            new ChatRule("trans", "40d", "mute", "mji"),
            new ChatRule("queer", "40d", "mute", "mji"),
            new ChatRule("jihadi", "40d", "mute", "mji"),
            new ChatRule("malaun", "40d", "mute", "mji"),
            new ChatRule("chuhra", "40d", "mute", "mji"),
            new ChatRule("saai", "40d", "mute", "mji"),
            new ChatRule("autistic", "40d", "mute", "mji"),
            new ChatRule("autism", "40d", "mute", "mji"),
            new ChatRule("bleach", "40d", "mute", "mji"),
            new ChatRule("midget", "40d", "mute", "mji"),
            new ChatRule("castrate", "40d", "mute", "mji"),
            new ChatRule("twink", "40d", "mute", "mji"),
            new ChatRule("circumcise", "40d", "mute", "mji"),
            new ChatRule("fag", "40d", "mute", "mji"),
            new ChatRule("faggot", "40d", "mute", "mji"),
            new ChatRule("lulha", "40d", "mute", "mji"),
            new ChatRule("cuck", "40d", "mute", "mji"),
            new ChatRule("dyke", "40d", "mute", "mji"),
            new ChatRule("incest", "40d", "mute", "mji"),
            new ChatRule("rape", "40d", "mute", "mji"),
            new ChatRule("rapist", "40d", "mute", "mji"),
    };

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {

        if (event.type != 0) return;

        String original = event.message.getUnformattedText();

        String user = extractUser(original);
        String message = extractMessage(original);

        String msg = message.toLowerCase();

        String[] words = msg.split("[^a-z0-9]+");

        for (ChatRule rule : RULES) {

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
