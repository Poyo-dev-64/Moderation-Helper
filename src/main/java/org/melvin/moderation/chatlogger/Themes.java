/*
 * Decompiled with CFR 0.152.
 */
package org.melvin.moderation.chatlogger;

import org.melvin.moderation.chatlogger.ChatLog;

public class Themes {
    private ChatLog cl;
    private String[][] themes = new String[5][16];
    private int currentTheme;

    public Themes(ChatLog cl) {
        this.cl = cl;
    }

    public int getCurrentTheme() {
        return this.currentTheme;
    }

    public void setCurrentTheme(int theme) {
        this.currentTheme = theme;
    }

    public String[] getTheme(int theme) {
        return this.themes[theme];
    }

    public void initThemes() {
        this.cl.logInfo("Initializing themes...");
        this.themes[0][0] = "000";
        this.themes[0][1] = "00A";
        this.themes[0][2] = "0A0";
        this.themes[0][3] = "0AA";
        this.themes[0][4] = "A00";
        this.themes[0][5] = "A0A";
        this.themes[0][6] = "FA0";
        this.themes[0][7] = "AAA";
        this.themes[0][8] = "555";
        this.themes[0][9] = "55F";
        this.themes[0][10] = "5F5";
        this.themes[0][11] = "5FF";
        this.themes[0][12] = "F55";
        this.themes[0][13] = "F5F";
        this.themes[0][14] = "FF5";
        this.themes[0][15] = "000";
        this.themes[1][0] = "000000";
        this.themes[1][1] = "000071";
        this.themes[1][2] = "007100";
        this.themes[1][3] = "007171";
        this.themes[1][4] = "710000";
        this.themes[1][5] = "710071";
        this.themes[1][6] = "AA7100";
        this.themes[1][7] = "717171";
        this.themes[1][8] = "383838";
        this.themes[1][9] = "3838AA";
        this.themes[1][10] = "38AA38";
        this.themes[1][11] = "38AAAA";
        this.themes[1][12] = "AA3838";
        this.themes[1][13] = "AA38AA";
        this.themes[1][14] = "AAAA38";
        this.themes[1][15] = "000000";
        for (int i = 0; i <= 15; ++i) {
            this.themes[3][i] = "000";
        }
        this.cl.logInfo("Themes initialized.");
    }
}

