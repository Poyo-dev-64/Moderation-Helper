package org.melvin.moderation.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PunishmentLogger {

    private static final File file = new File("moderation_log.txt");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void log(String type, String player, String offense) {
        try {
            if (!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String time = sdf.format(new Date());
            bw.write(time + " | " + type + " | " + player + " | " + offense);
            bw.newLine();

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
