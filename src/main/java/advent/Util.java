package advent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Util {
    private Util() {
    }

    public static List<String> readFiles(String fileName) {
        File file;
        try {
            var pathname = "./src/main/resources/" + fileName + ".txt";
            file = new File(pathname);
        } catch (Exception e) {
            System.out.println("Shit");
            e.printStackTrace();
            return new ArrayList<>();
        }
        List<String> data = new ArrayList<>();
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine());
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.printf("Input File %s has %d lines%n", fileName, data.size());
        return data;
    }
}
