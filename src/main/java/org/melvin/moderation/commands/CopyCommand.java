package org.melvin.moderation.commands;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import org.melvin.moderation.events.ChatHandler;

public class CopyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "fm_copy";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fm_copy";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        GuiScreen.setClipboardString(ChatHandler.getLastMessage());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
