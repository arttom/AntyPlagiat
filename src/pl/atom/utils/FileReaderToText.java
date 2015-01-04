package pl.atom.utils;

import java.io.File;

/**
 * Created by Artur Tomaszewski on 2014-12-22.
 */
public interface FileReaderToText {
    public String getTextFromFile(File file);
    public String getTextFromFile(String path);
}
