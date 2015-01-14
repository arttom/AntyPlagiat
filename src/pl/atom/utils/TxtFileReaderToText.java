package pl.atom.utils;


import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Klasa do otwierania plików typu TXT implementująca interfejs FileReaderToText
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
                textBuilder.append(sCurrentLine+"\r\n");
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
        String text="";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            text = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
