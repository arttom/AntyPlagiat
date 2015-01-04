package pl.atom.utils;

import java.io.*;

/**
 * Created by Artur Tomaszewski on 2014-12-22.
 */
public class TxtFileReaderToText implements FileReaderToText {
    @Override
    public String getTextFromFile(File file) {
        BufferedReader br = null;
        StringBuilder textBuilder= new StringBuilder("");
        try {
            String sCurrentLine;
            Reader reader = new InputStreamReader(new FileInputStream(file), "windows-1250");
            br = new BufferedReader(reader);

            while ((sCurrentLine = br.readLine()) != null) {
                textBuilder.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new String(textBuilder);
    }

    @Override
    public String getTextFromFile(String path) {
        BufferedReader br = null;
        StringBuilder textBuilder= new StringBuilder("");
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                textBuilder.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new String(textBuilder);
    }
}
