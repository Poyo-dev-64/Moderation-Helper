/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.command.ICommand
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPostInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  org.apache.logging.log4j.Logger
 */
package org.melvin.moderation.chatlogger;

import org.melvin.moderation.chatlogger.ChatReceivedHandler;
import org.melvin.moderation.chatlogger.Command;
import org.melvin.moderation.chatlogger.ConfigManager;
import org.melvin.moderation.chatlogger.HtmlConverter;
import org.melvin.moderation.chatlogger.Logs;
import org.melvin.moderation.chatlogger.Themes;
import org.melvin.moderation.chatlogger.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.Logger;

public class ChatLog {

    public Themes themes;
    public HtmlConverter htmlConverter;
    public ConfigManager configManager;
    public Logs logs;
    public Minecraft mc;
    public Util util;
    private Logger logger;

    public void init(Logger logger) {
        this.logger = logger;
        this.mc = Minecraft.getMinecraft();

        logInfo("Initializing ChatLog...");

        this.util = new Util(this);
        this.themes = new Themes(this);
        this.themes.initThemes();

        this.htmlConverter = new HtmlConverter(this);
        this.configManager = new ConfigManager(this);
        this.logs = new Logs(this);

        this.configManager.readPropertiesFile();

        logInfo("Initialized.");
    }

    public ChatReceivedHandler getChatHandler() {
        return new ChatReceivedHandler(this);
    }

    public Command getCommand() {
        return new Command(this);
    }

    public void logInfo(String message) {
        if (logger != null) logger.info(message);
    }

    public void logWarning(String message) {
        if (logger != null) logger.warn(message);
    }

    public void logSevere(String message) {
        if (logger != null) logger.error(message);
    }

    public void postToChat(String message) {
        ChatComponentText cp = new ChatComponentText("§8[§cChatLog§8] " + message);
        if (mc != null && mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(cp);
        }
    }

    public void postSimple(String message) {
        ChatComponentText cp = new ChatComponentText(message);
        if (mc != null && mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(cp);
        }
    }
}
