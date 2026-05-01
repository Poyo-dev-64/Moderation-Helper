package org.melvin.moderation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.ClientCommandHandler;

import org.melvin.moderation.commands.FMPunishCommand;
import org.melvin.moderation.commands.CopyCommand;
import org.melvin.moderation.commands.OpenScreenshotCommand;
import org.melvin.moderation.events.ChatHandler;
import org.melvin.moderation.events.RenderHandler;
import org.melvin.moderation.features.ChatFilter;

@Mod(modid = "moderation", name = "Moderation Helper", version = "0.4")
public class Main {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new ChatFilter());

        ClientCommandHandler.instance.registerCommand(new FMPunishCommand());
        ClientCommandHandler.instance.registerCommand(new CopyCommand());
        ClientCommandHandler.instance.registerCommand(new OpenScreenshotCommand());

        System.out.println("Moderation Mod Loaded");
    }
}
