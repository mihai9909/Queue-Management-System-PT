package BusinessLogic;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    public static void writeLine(String line){
        try {
            FileWriter myWriter = new FileWriter("Logs.txt",true);
            myWriter.write(line);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void deleteFileContents(){
        try {
            FileWriter fw = new FileWriter("Logs.txt");
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
