/*
 * Decompiled with CFR 0.152.
 */
package org.melvin.moderation.chatlogger;

import org.melvin.moderation.chatlogger.ChatLog;
import org.melvin.moderation.chatlogger.LogFilenameFilter;
import org.melvin.moderation.chatlogger.Util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Logs {
    private final ChatLog cl;
    private FileWriter fw = null;
    private volatile boolean canWrite = false;
    private String lastLine = null;
    private int repetitions = 0;

    public Logs(ChatLog cl) {
        this.cl = cl;
    }

    public static File[] find(String dirName) {
        File dir = new File(dirName);
        return dir.listFiles(new LogFilenameFilter());
    }

    public void prune(int maxCount) {
        this.cl.logInfo("Clearing old logs...");
        File[] storedLogs = Logs.find(Util.getChatLogFolderPath());
        if (storedLogs.length > maxCount) {
            ArrayList<File> toBeDeleted = new ArrayList<File>(0);
            ArrayList<File> allLists = new ArrayList<File>(Arrays.asList(storedLogs));
            for (int filesStoreAfter = storedLogs.length; filesStoreAfter > maxCount; --filesStoreAfter) {
                toBeDeleted.add(allLists.get(0));
                allLists.remove(0);
            }
            for (File f : toBeDeleted) {
                f.delete();
            }
        }
    }

    public void write(String message, String flags) {
        if (this.cl.configManager.nospam && message.equals(this.lastLine)) {
            ++this.repetitions;
            return;
        }
        try {
            File fLoc = new File(Util.getChatLogFolderPath());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String curDate = df.format(new Date());
            String filename = curDate + ".htm";
            String fileLocation = new File(Util.getChatLogFolderPath(), filename).getAbsolutePath();
            if (this.fw == null) {
                fLoc.mkdir();
                this.fw = new FileWriter(fileLocation, true);
                Runtime.getRuntime().addShutdownHook(new Thread(){

                    @Override
                    public void run() {
                        Logs.this.canWrite = false;
                        try {
                            Logs.this.fw.close();
                            Logs.this.cl.logInfo("Successfully closed chat log file.");
                        }
                        catch (IOException e) {
                            Logs.this.cl.logSevere("FAILED TO CLOSE CHAT LOG FILE FOR WRITING: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
                this.canWrite = true;
            }
            String msg = this.cl.htmlConverter.convert(message, flags);
            if (this.canWrite) {
                this.lastLine = message;
                if (this.cl.configManager.nospam && this.repetitions > 0) {
                    this.fw.write("<br><b><code>&nbsp;&nbsp;-> Above message repeated " + this.repetitions + " times</code></b><br><br>\n");
                }
                this.fw.write(msg);
                this.fw.flush();
            }
        }
        catch (IOException e) {
            this.cl.logSevere("FAILED TO WRITE TO CHAT LOG: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

