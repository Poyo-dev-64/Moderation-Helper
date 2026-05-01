package org.melvin.moderation.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.awt.Desktop;
import java.io.File;

public class OpenScreenshotCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "fm_open";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fm_open <filename>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (args.length < 1) {
            sender.addChatMessage(new ChatComponentText("§cUsage: /fm_open <filename>"));
            return;
        }

        try {
            File file = new File(
                    Minecraft.getMinecraft().mcDataDir,
                    "moderation/screenshots/" + args[0]
            );

            if (!file.exists()) {
                sender.addChatMessage(new ChatComponentText("§cFile not found"));
                return;
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                Runtime.getRuntime().exec(
                        "explorer.exe /select,\"" + file.getAbsolutePath() + "\""
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            sender.addChatMessage(new ChatComponentText("§cFailed to open file"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
