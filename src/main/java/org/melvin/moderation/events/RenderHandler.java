package org.melvin.moderation.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RenderHandler {

    private boolean enabled = true;

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

    public static RenderHandler INSTANCE;

    public RenderHandler() {
        INSTANCE = this;
    }

    public void showFlag(String user, String reason, String command) {
        flagLine1 = "§cCHAT FLAGGED";
        flagLine2 = "§e" + user + " §7→ §c" + reason;
        flagLine3 = "§d" + command;
        flagExpire = System.currentTimeMillis() + 5000;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {

        Minecraft mc = Minecraft.getMinecraft();

        if (!enabled) return;

        long now = System.currentTimeMillis();

        if (now - lastUpdate > UPDATE_INTERVAL) {
            load(mc);
            lastUpdate = now;
        }

        int x = 5;
        int y = 20;

        Gui.drawRect(x, y, x + 140, y + 70, 0x80000000);

        mc.fontRendererObj.drawString("§ePunishment Stats", x + 5, y + 5, 0xFFFFFF);
        mc.fontRendererObj.drawString("Total: " + total, x + 5, y + 20, 0xFFFFFF);
        mc.fontRendererObj.drawString("Mutes: " + mutes, x + 5, y + 30, 0xFF5555);
        mc.fontRendererObj.drawString("Bans: " + bans, x + 5, y + 40, 0xFF0000);
        mc.fontRendererObj.drawString("Tempbans: " + tempbans, x + 5, y + 50, 0xAA00FF);

        if (System.currentTimeMillis() < flagExpire
                && flagLine1 != null
                && flagLine2 != null
                && flagLine3 != null) {

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

            mc.fontRendererObj.drawString(
                    flagLine3,
                    fx - mc.fontRendererObj.getStringWidth(flagLine3) / 2,
                    fy + 24,
                    0xAAAAAA
            );
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
                if (parts.length < 2) continue;

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
}
