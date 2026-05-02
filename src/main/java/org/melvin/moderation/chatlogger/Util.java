/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package org.melvin.moderation.chatlogger;

import org.melvin.moderation.chatlogger.ChatLog;
import java.io.File;
import net.minecraft.client.Minecraft;

public class Util {
    private final ChatLog cl;

    public Util(ChatLog cl) {
        this.cl = cl;
    }

    public static String getChatLogFolderPath() {
        return new File(Minecraft.getMinecraft().mcDataDir, "chatlogs").getAbsolutePath();
    }

    public static String getConfigFilePath() {
        return new File(Minecraft.getMinecraft().mcDataDir, "config/ChatLog.properties").getAbsolutePath();
    }

    public String getLogFlag(String s) {
        return this.cl.configManager.props.getProperty("logflags", "true").equalsIgnoreCase("false") ? "" : s;
    }
}

