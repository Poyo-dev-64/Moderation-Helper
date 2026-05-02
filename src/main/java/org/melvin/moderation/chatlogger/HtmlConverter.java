/*
 * Decompiled with CFR 0.152.
 */
package org.melvin.moderation.chatlogger;

import org.melvin.moderation.chatlogger.ChatLog;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HtmlConverter {
    private ChatLog cl;

    public HtmlConverter(ChatLog cl) {
        this.cl = cl;
    }

    public String convert(String message, String flags) {
        SimpleDateFormat dftime = null;
        String curTime = null;
        if (!this.cl.configManager.tsFormat.equals("null")) {
            dftime = new SimpleDateFormat(this.cl.configManager.tsFormat);
            curTime = dftime.format(new Date());
        }
        String prefix = "<code><span>" + (flags.length() > 0 ? "<b>[" : "") + (flags.contains("F") ? "<span style=\"color: red;\" title=\"Filtered message\">F</span>" : "") + (flags.contains("S") ? "<span style=\"color: blue;\" title=\"Control message\">S</span>" : "") + (flags.length() > 0 ? "]</b> " : "") + (this.cl.configManager.tsFormat.equals("null") ? "" : "[" + curTime + "] ");
        String suffix = "</span></code><br>\n";
        String rPar1 = message.replace("<", "&lt;");
        rPar1 = rPar1.replace(">", "&gt;");
        if (this.cl.themes.getCurrentTheme() != 2) {
            String[] theme = this.cl.themes.getTheme(this.cl.themes.getCurrentTheme());
            rPar1 = rPar1.replaceAll("\\xA70", "</span><span style=\"color: #" + theme[0] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA71", "</span><span style=\"color: #" + theme[1] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA72", "</span><span style=\"color: #" + theme[2] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA73", "</span><span style=\"color: #" + theme[3] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA74", "</span><span style=\"color: #" + theme[4] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA75", "</span><span style=\"color: #" + theme[5] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA76", "</span><span style=\"color: #" + theme[6] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA77", "</span><span style=\"color: #" + theme[7] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA78", "</span><span style=\"color: #" + theme[8] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA79", "</span><span style=\"color: #" + theme[9] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7a", "</span><span style=\"color: #" + theme[10] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7b", "</span><span style=\"color: #" + theme[11] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7c", "</span><span style=\"color: #" + theme[12] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7d", "</span><span style=\"color: #" + theme[13] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7e", "</span><span style=\"color: #" + theme[14] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7f", "</span><span style=\"color: #" + theme[15] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7A", "</span><span style=\"color: #" + theme[10] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7B", "</span><span style=\"color: #" + theme[11] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7C", "</span><span style=\"color: #" + theme[12] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7D", "</span><span style=\"color: #" + theme[13] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7E", "</span><span style=\"color: #" + theme[14] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7F", "</span><span style=\"color: #" + theme[15] + ";\">");
            rPar1 = rPar1.replaceAll("\\xA7k", "");
            rPar1 = rPar1.replaceAll("\\xA7l", "");
            rPar1 = rPar1.replaceAll("\\xA7m", "");
            rPar1 = rPar1.replaceAll("\\xA7n", "");
            rPar1 = rPar1.replaceAll("\\xA7o", "");
            rPar1 = rPar1.replaceAll("\\xA7r", "</span><span>");
            rPar1 = rPar1.replaceAll("\\xA7K", "");
            rPar1 = rPar1.replaceAll("\\xA7L", "");
            rPar1 = rPar1.replaceAll("\\xA7M", "");
            rPar1 = rPar1.replaceAll("\\xA7N", "");
            rPar1 = rPar1.replaceAll("\\xA7O", "");
            rPar1 = rPar1.replaceAll("\\xA7R", "</span><span>");
        }
        return prefix + rPar1 + suffix;
    }
}

