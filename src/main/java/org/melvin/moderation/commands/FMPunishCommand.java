package org.melvin.moderation.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;

import org.melvin.moderation.util.OffenseMap;
import org.melvin.moderation.util.PunishmentLogger;

import java.io.File;

public class FMPunishCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "fmpunish";
    }

    private void takeScreenshot() {

        Minecraft mc = Minecraft.getMinecraft();

        try {
            File dir = new File(mc.mcDataDir, "moderation");
            dir.mkdirs();

            String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")
                    .format(new java.util.Date());

            File file = new File(dir, "fmpunish_" + timestamp + ".png");

            net.minecraft.util.ScreenShotHelper.saveScreenshot(
                    dir,
                    file.getName(),
                    mc.displayWidth,
                    mc.displayHeight,
                    mc.getFramebuffer()
            );

            ChatComponentText msg =
                    new ChatComponentText("§aSaved screenshot as ");

            ChatComponentText path =
                    new ChatComponentText("§b" + file.getName());

            path.setChatStyle(new ChatStyle()
                    .setChatClickEvent(new ClickEvent(
                            ClickEvent.Action.OPEN_FILE,
                            file.getAbsolutePath()
                    ))
                    .setChatHoverEvent(new HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            new ChatComponentText("§eClick to open")
                    ))
            );

            msg.appendSibling(path);
            mc.thePlayer.addChatMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fmpunish <mute|ban|tempban> <name> <time?> <offense>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        takeScreenshot();

        if (args.length < 3) {
            sender.addChatMessage(new ChatComponentText("§cUsage: /fmpunish <mute|ban|tempban> <name> <time?> <offense>"));
            return;
        }

        String type = args[0].toLowerCase();
        String name = args[1];

        String time;
        String offense;

        if (type.equals("ban")) {
            time = null;
            offense = buildOffense(args, 2);
        } else {
            if (args.length < 4) {
                sender.addChatMessage(new ChatComponentText("§cUsage: /fmpunish " + type + " <name> <time> <offense>"));
                return;
            }
            time = args[2];
            offense = buildOffense(args, 3);
        }

        offense = OffenseMap.resolve(offense);

        String command;

        switch (type) {
            case "mute":
                command = "/mute " + name + " " + time + " " + offense;
                break;
            case "tempban":
                command = "/tempban " + name + " " + time + " " + offense;
                break;
            case "ban":
                command = "/ban " + name + " " + offense;
                break;
            default:
                sender.addChatMessage(new ChatComponentText("§cInvalid type"));
                return;
        }

        ChatComponentText prefix = new ChatComponentText("§aExecuting: ");
        ChatComponentText cmd = new ChatComponentText("§f" + command);

        cmd.setChatStyle(new ChatStyle()
                .setChatClickEvent(new ClickEvent(
                        ClickEvent.Action.RUN_COMMAND,
                        "/fm_copy \"" + command + "\""
                ))
                .setChatHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ChatComponentText("§eClick to copy")
                ))
        );

        prefix.appendSibling(cmd);
        sender.addChatMessage(prefix);

        Minecraft.getMinecraft().thePlayer.sendChatMessage(command);
        PunishmentLogger.log(type, name, offense);
    }

    private String buildOffense(String[] args, int start) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
