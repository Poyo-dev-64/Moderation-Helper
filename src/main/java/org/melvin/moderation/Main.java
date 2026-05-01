package org.melvin.moderation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.melvin.moderation.features.ChatFilter;
import org.melvin.moderation.commands.FMPunishCommand;
import org.melvin.moderation.commands.CopyCommand;
import org.melvin.moderation.events.ChatHandler;
import org.melvin.moderation.events.RenderHandler;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(modid = "moderation", name = "Moderation Helper", version = "0.41")
public class Main {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        ClientCommandHandler.instance.registerCommand(new FMPunishCommand());
        ClientCommandHandler.instance.registerCommand(new CopyCommand());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new ChatFilter());
        System.out.println("Moderation Mod Loaded");
    }
}
