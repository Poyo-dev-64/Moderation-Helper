package org.melvin.moderation.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Calendar;

public class RenderHandler {

    public static RenderHandler INSTANCE;

    private boolean enabled = true;
    private boolean showStats = true;
    private boolean showFlags = true;

    private int total;
    private int mutes;
    private int bans;
    private int tempbans;

    private long lastUpdate = 0;
    private static final long UPDATE_INTERVAL = 1000;

    private String flagLine1;
    private String flagLine2;
    private String flagLine3;
    private long flagExpire = 0;


    private final String[] lastFlags = new String[3];

    private enum Mode { DAY, WEEK, MONTH, ALL }
    private Mode mode = Mode.DAY;

    private int modeX1, modeY1, modeX2, modeY2;

    public RenderHandler() {
        INSTANCE = this;
    }

    public void toggleStats() {
        showStats = !showStats;
    }

    public void toggleFlags() {
        showFlags = !showFlags;
    }

    public void showFlag(String user, String reason, String command) {
        if (!showFlags) return;

        flagLine1 = "§cCHAT FLAGGED";
        flagLine2 = "§e" + user + " §7→ §c" + reason;
        flagLine3 = command;
        flagExpire = System.currentTimeMillis() + 5000;


        lastFlags[2] = lastFlags[1];
        lastFlags[1] = lastFlags[0];
        lastFlags[0] = user;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {

        if (!enabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        long now = System.currentTimeMillis();

        if (now - lastUpdate > UPDATE_INTERVAL) {
            load(mc);
            lastUpdate = now;
        }

        int sx = 5;
        int sy = 5;

        String statsText = showStats ? "§a[STATS]" : "§c[STATS]";
        String flagsText = showFlags ? "§a[FLAGS]" : "§c[FLAGS]";
        mc.fontRendererObj.drawString(statsText + " " + flagsText + "§r", sx, sy, 0xFFFFFF);

        int x = 5;
        int y = 20;

        if (showStats) {
            Gui.drawRect(x, y, x + 150, y + 65, 0x80000000);

            mc.fontRendererObj.drawString("§ePunishment Stats", x + 5, y + 5, 0xFFFFFF);

            String modeText =
                    (mode == Mode.DAY ? "§aD" : "§7D") + "/" +
                    (mode == Mode.WEEK ? "§aW" : "§7W") + "/" +
                    (mode == Mode.MONTH ? "§aM" : "§7M") + "/" +
                    (mode == Mode.ALL ? "§aA" : "§7A");

            String fullMode = "[" + modeText + "]";
            int modeWidth = mc.fontRendererObj.getStringWidth(fullMode);

            int mx = x + 150 - modeWidth - 5;
            int my = y + 5;

            modeX1 = mx;
            modeY1 = my;
            modeX2 = mx + modeWidth;
            modeY2 = my + 10;

            mc.fontRendererObj.drawString(fullMode, mx, my, 0xFFFFFF);

            mc.fontRendererObj.drawString("Total: " + total, x + 5, y + 20, 0xFFFFFF);
            mc.fontRendererObj.drawString("Mutes: " + mutes, x + 5, y + 30, 0xFF5555);
            mc.fontRendererObj.drawString("Bans: " + bans, x + 5, y + 40, 0xFF0000);
            mc.fontRendererObj.drawString("Tempbans: " + tempbans, x + 5, y + 50, 0xAA00FF);


            if (showFlags) {
                int fx = x + 160;
                int fy = y;

                Gui.drawRect(fx, fy, fx + 120, fy + 65, 0x80000000);

                mc.fontRendererObj.drawString("§eRecent Flags", fx + 5, fy + 5, 0xFFFFFF);

                for (int i = 0; i < 3; i++) {
                    if (lastFlags[i] != null) {

                        String name = lastFlags[i];


                        if (mc.fontRendererObj.getStringWidth(name) > 110) {
                            name = name.substring(0, Math.min(name.length(), 12)) + "...";
                        }

                        mc.fontRendererObj.drawString(
                                "§c" + name,
                                fx + 5,
                                fy + 20 + (i * 10),
                                0xFFFFFF
                        );
                    }
                }
            }
        }

        if (showFlags &&
                System.currentTimeMillis() < flagExpire &&
                flagLine1 != null &&
                flagLine2 != null &&
                flagLine3 != null) {

            int fx = mc.displayWidth / 2 / 2;
            int fy = mc.displayHeight / 2 / 2 - 20;

            Gui.drawRect(fx - 90, fy - 10, fx + 90, fy + 40, 0x80000000);

            mc.fontRendererObj.drawString(
                    flagLine1,
                    fx - mc.fontRendererObj.getStringWidth(flagLine1) / 2,
                    fy,
                    0xFF5555
            );

            mc.fontRendererObj.drawString(
                    flagLine2,
                    fx - mc.fontRendererObj.getStringWidth(flagLine2) / 2,
                    fy + 12,
                    0xFFFFFF
            );

            String cmd = "§d" + flagLine3;

            mc.fontRendererObj.drawString(
                    cmd,
                    fx - mc.fontRendererObj.getStringWidth(cmd) / 2,
                    fy + 24,
                    0xAAAAAA
            );
        }
    }

    @SubscribeEvent
    public void onClick(net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent.Post event) {

        if (!(event.gui instanceof net.minecraft.client.gui.GuiChat)) return;

        if (!org.lwjgl.input.Mouse.getEventButtonState()) return;
        if (org.lwjgl.input.Mouse.getEventButton() != 0) return;

        Minecraft mc = Minecraft.getMinecraft();

        int mx = org.lwjgl.input.Mouse.getEventX() * event.gui.width / mc.displayWidth;
        int my = event.gui.height - org.lwjgl.input.Mouse.getEventY() * event.gui.height / mc.displayHeight - 1;

        if (mx >= modeX1 && mx <= modeX2 && my >= modeY1 && my <= modeY2) {
            switch (mode) {
                case DAY: mode = Mode.WEEK; break;
                case WEEK: mode = Mode.MONTH; break;
                case MONTH: mode = Mode.ALL; break;
                case ALL: mode = Mode.DAY; break;
            }
            lastUpdate = 0;
            return;
        }

        if (showFlags &&
                System.currentTimeMillis() < flagExpire &&
                flagLine3 != null) {

            int fx = mc.displayWidth / 2 / 2;
            int fy = mc.displayHeight / 2 / 2 - 20;

            String cmd = flagLine3;
            int width = mc.fontRendererObj.getStringWidth("§d" + cmd);

            int x1 = fx - width / 2;
            int x2 = fx + width / 2;
            int y1 = fy + 24;
            int y2 = y1 + 10;

            if (mx >= x1 && mx <= x2 && my >= y1 && my <= y2) {
                mc.displayGuiScreen(new net.minecraft.client.gui.GuiChat(cmd));
            }
        }
    }

    private void load(Minecraft mc) {

        File file = new File(mc.mcDataDir, "moderation_log.txt");

        total = 0;
        mutes = 0;
        bans = 0;
        tempbans = 0;

        try {
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;

                long timestamp;
                try {
                    timestamp = Long.parseLong(parts[0]);
                } catch (Exception e) {
                    continue;
                }

                if (!matchesTime(timestamp)) continue;

                String type = parts[1].trim();

                if (type.equals("mute") || type.equals("ban") || type.equals("tempban")) {

                    total++;

                    if (type.equals("mute")) mutes++;
                    else if (type.equals("ban")) bans++;
                    else if (type.equals("tempban")) tempbans++;
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean matchesTime(long timestamp) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.setTimeInMillis(timestamp);

        switch (mode) {
            case DAY:
                return now.get(Calendar.YEAR) == then.get(Calendar.YEAR) &&
                        now.get(Calendar.DAY_OF_YEAR) == then.get(Calendar.DAY_OF_YEAR);

            case WEEK:
                return now.get(Calendar.YEAR) == then.get(Calendar.YEAR) &&
                        now.get(Calendar.WEEK_OF_YEAR) == then.get(Calendar.WEEK_OF_YEAR);

            case MONTH:
                return now.get(Calendar.YEAR) == then.get(Calendar.YEAR) &&
                        now.get(Calendar.MONTH) == then.get(Calendar.MONTH);

            case ALL:
                return true;
        }
        return true;
    }
}
