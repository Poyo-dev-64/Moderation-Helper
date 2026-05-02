package org.melvin.moderation.chatlogger;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.Collections;
import java.util.List;

public class Command implements ICommand {

    private final ChatLog cl;

    public Command(ChatLog cl) {
        this.cl = cl;
    }

    @Override
    public String getCommandName() {
        return "chatlog";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/chatlog";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
