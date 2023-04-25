package wol;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws java.io.IOException {
        ArrayList<String> words = new ArrayList<>();
        File file = new File("resources/lexicon.txt");
        Scanner fileScan = new Scanner(file);

        while(fileScan.hasNextLine()) {
            words.add(fileScan.nextLine());
        }

        Lexicon lexicon = new Lexicon(words);
        Scanner scan = new Scanner(System.in);
        PrintStream output = new PrintStream(System.out);
        ConsoleInterface ui = new ConsoleInterface(scan, output);
        Executioner executioner = new CheatingExecutioner();
        char invalidCharacter = '*';
        WheelOfLies gameWheel = new WheelOfLies(lexicon, ui, executioner, invalidCharacter);

        gameWheel.playGame();
    }
}