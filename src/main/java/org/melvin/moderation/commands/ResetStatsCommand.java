package org.melvin.moderation.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.io.File;

public class ResetStatsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "resetstats";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/resetstats";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir, "moderation_log.txt");

            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("§a[Moderation] Stats reset.")
            );

        } catch (Exception e) {
            e.printStackTrace();

            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText("§c[Moderation] Failed to reset stats.")
            );
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
