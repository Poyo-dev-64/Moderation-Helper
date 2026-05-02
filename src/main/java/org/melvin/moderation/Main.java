package org.melvin.moderation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.ClientCommandHandler;

import org.apache.logging.log4j.Logger;

import org.melvin.moderation.commands.FMPunishCommand;
import org.melvin.moderation.commands.CopyCommand;
import org.melvin.moderation.commands.OpenScreenshotCommand;
import org.melvin.moderation.commands.ResetStatsCommand;
import org.melvin.moderation.events.ChatHandler;
import org.melvin.moderation.events.RenderHandler;
import org.melvin.moderation.features.ChatFilter;
import org.melvin.moderation.chatlogger.ChatLog;
import org.melvin.moderation.util.KeybindHandler;

@Mod(modid = "moderation", name = "Moderation Helper", version = "0.52")
public class Main {

    public ChatLog chatLog;
    private Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        chatLog = new ChatLog();
        chatLog.init(logger);
        KeybindHandler.init();

        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new ChatFilter());
        MinecraftForge.EVENT_BUS.register(chatLog.getChatHandler());
        MinecraftForge.EVENT_BUS.register(new KeybindHandler());

        ClientCommandHandler.instance.registerCommand(new FMPunishCommand());
        ClientCommandHandler.instance.registerCommand(new CopyCommand());
        ClientCommandHandler.instance.registerCommand(new OpenScreenshotCommand());
        ClientCommandHandler.instance.registerCommand(chatLog.getCommand());
        ClientCommandHandler.instance.registerCommand(new ResetStatsCommand());

        System.out.println("Moderation Mod Loaded");
    }
}
