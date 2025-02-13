package main.com.github.fariadavi.utils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;

import static main.com.github.fariadavi.ScoreManager.NUM_HIGHSCORES;

public class FileHelper {

    public static final String RESOURCES_PATH = "main/resources/";

    public static Image getImage(String imagePath) {
        return new ImageIcon(getResource(imagePath)).getImage();
    }

    private static URL getResource(String path) {
        return FileHelper.class.getClassLoader().getResource(RESOURCES_PATH + path);
    }

    private static InputStream getResourceAsStream(String path) {
        return FileHelper.class.getClassLoader().getResourceAsStream(RESOURCES_PATH + path);
    }

    public static void writeFile(String filePath, String contents) {
        try {
            FileWriter fileWriter = new FileWriter(getResource(filePath).getPath());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contents);
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("error: " + ex.getMessage());
        }
    }

    public static String[] readFile(String filePath) {
        String[] scores = new String[NUM_HIGHSCORES];

        int i = 0;
        try {
            InputStream resourceAsStream = getResourceAsStream(filePath);
            InputStreamReader streamReader = new InputStreamReader(resourceAsStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null && i < NUM_HIGHSCORES)
                scores[i++] = line;

            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("error: " + ex.getMessage());
        }

        return scores;
    }
}
