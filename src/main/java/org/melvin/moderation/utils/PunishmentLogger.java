package org.melvin.moderation.util;

import net.minecraft.client.Minecraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class PunishmentLogger {

    private static File getFile() {
        return new File(Minecraft.getMinecraft().mcDataDir, "moderation_log.txt");
    }

    public static void log(String type, String player, String offense) {
        try {
            File file = getFile();
            if (!file.exists()) file.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

            long time = System.currentTimeMillis();
            bw.write(time + "|" + type + "|" + player + "|" + offense);
            bw.newLine();

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
