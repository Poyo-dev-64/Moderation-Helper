package org.melvin.moderation.commands;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CopyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "fm_copy";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fm_copy <text>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("§cNothing to copy"));
            return;
        }

        String text = String.join(" ", args)
                .replaceAll("^\"|\"$", "");

        GuiScreen.setClipboardString(text);

        sender.addChatMessage(new ChatComponentText("§aCopied command"));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
