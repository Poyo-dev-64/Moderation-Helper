/*
 * Decompiled with CFR 0.152.
 */
package org.melvin.moderation.chatlogger;

import org.melvin.moderation.chatlogger.ChatLog;
import org.melvin.moderation.chatlogger.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static String PROPINFO = "Do not edit \"filters\" manually. Do this using filter commands. For more info, run '//cl filter?' in-game.";
    private ChatLog cl;
    public String[] properties = new String[4];
    private boolean initialized = false;
    public Properties props = null;
    public boolean nospam = false;
    public String tsFormat = "HH:mm:ss";

    public ConfigManager(ChatLog cl) {
        this.cl = cl;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    private String[] genPropertiesFile(String configFileLocation, String[] propChatLog1) {
        this.cl.logInfo("Generating configuration file...");
        Properties prop = new Properties();
        String[] propChatLog = propChatLog1;
        propChatLog[0] = "-1";
        propChatLog[1] = "0";
        propChatLog[2] = "HH:mm:ss";
        propChatLog[3] = "false";
        try {
            prop.setProperty("max_logs", propChatLog[0]);
            prop.setProperty("color_pack", propChatLog[1]);
            prop.setProperty("timestamp_format", propChatLog[2]);
            prop.setProperty("collapse_repetitions", propChatLog[3]);
            this.props = prop;
            prop.store(new FileOutputStream(configFileLocation), PROPINFO);
            this.cl.logInfo("Configuration file successfully generated.");
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
            this.cl.logSevere("FAILED TO GENERATE CONFIGURATION FILE: " + ex1.getMessage());
        }
        return propChatLog;
    }

    public void readPropertiesFile() {
        Properties prop = new Properties();
        try {
            File cfLoc = new File(Util.getConfigFilePath());
            if (cfLoc.exists()) {
                this.cl.logInfo("Reading configuration file...");
                prop.load(new FileInputStream(Util.getConfigFilePath()));
                this.properties[0] = prop.getProperty("max_logs", "-1");
                this.properties[1] = prop.getProperty("color_pack", "0");
                this.properties[2] = prop.getProperty("timestamp_format", "HH:mm:ss");
                this.properties[3] = prop.getProperty("collapse_repetitions", "false");
                prop.setProperty("max_logs", this.properties[0]);
                prop.setProperty("color_pack", this.properties[1]);
                prop.setProperty("timestamp_format", this.properties[2]);
                prop.setProperty("collapse_repetitions", this.properties[3]);
                this.props = prop;
                this.nospam = this.properties[3].equalsIgnoreCase("true");
                prop.store(new FileOutputStream(Util.getConfigFilePath()), PROPINFO);
            } else {
                this.cl.logInfo("Configuration file not found, generating a new one...");
                this.properties = this.genPropertiesFile(Util.getConfigFilePath(), this.properties);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            this.cl.logWarning("Configuration file corrupt or inaccessible! Generating a new one...");
            this.properties = this.genPropertiesFile(Util.getConfigFilePath(), this.properties);
        }
        this.implementProperties();
    }

    public void savePropertiesFile() {
        try {
            this.props.store(new FileOutputStream(Util.getConfigFilePath()), PROPINFO);
        }
        catch (IOException e) {
            this.cl.logSevere("Failed to save properties file!");
            e.printStackTrace();
        }
    }

    private void implementProperties() {
        this.cl.themes.setCurrentTheme(Integer.parseInt(this.properties[1]));
        int maxFileCount = Integer.parseInt(this.properties[0]);
        if (maxFileCount > -1) {
            this.cl.logs.prune(maxFileCount);
        }
        this.initialized = true;
    }
}

