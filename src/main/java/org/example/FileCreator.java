package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class FileCreator {

    public static void createFiles(){
        for(int i = 1; i < 11; i++){
            String filename = "src/data/file" + i + ".txt";
            String fileContent = "Message " + i;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
                writer.write(fileContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
