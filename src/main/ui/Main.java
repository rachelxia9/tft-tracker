package ui;

import java.io.FileNotFoundException;

// main class that runs app
// Citation: referenced code from the teller app + fitlifegym projects
// Citation: JsonSerializationDemo from EdX, Phase 2

public class Main {
    public static void main(String[] args) {
        try {
            new TftApp();
        } catch (FileNotFoundException e) {
            System.out.println("Application cannot be run: file not found");
        }
    }
}
