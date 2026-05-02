/*
 * Decompiled with CFR 0.152.
 */
package org.melvin.moderation.chatlogger;

import java.io.File;
import java.io.FilenameFilter;

public class LogFilenameFilter
implements FilenameFilter {
    @Override
    public boolean accept(File dir, String filename) {
        return filename.endsWith(".htm");
    }
}

