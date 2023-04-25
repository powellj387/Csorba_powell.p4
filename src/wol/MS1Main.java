package wol;

import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class MS1Main {
    public static void main(java.lang.String[] args) throws java.io.IOException {
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
        Executioner executioner = new ConsoleExecutioner(ui);
        boolean playAgain = true;
        while (playAgain) {
            char invalidCharacter = '*';
            //Ask for the length of the word
            int wordLength = ui.askForWordLength(lexicon);
            //Ask for the number of allowed guesses
            int numGuessesAllowed = ui.askForMaximumMisses();
            //Ask the user if they would like to see the word count
            boolean displayCount = ui.askToDisplayWordCount();
            //Prepare the executioner for a new game
            executioner.newGame(lexicon.wordsOfLength(wordLength), numGuessesAllowed, invalidCharacter);
            //Display number of guesses left
            ui.displayGameState(executioner, displayCount);
            boolean gotTheWord = false;
            while (!executioner.isGameOver()&&!gotTheWord) {
                //ask user for a guess
                char guess = ui.askNextGuess(executioner);
                //display results of guess
                ui.displayResultsOfGuess(guess, executioner.registerAGuess(guess));
                if(executioner.formattedSecretWord().toUpperCase().equals(executioner.revealSecretWord()))
                {
                    gotTheWord = true;
                }
                if (!executioner.isGameOver()&&!gotTheWord) {
                    //display the game state
                    ui.displayGameState(executioner, displayCount);
                }
            }
            //Display the game over text
            ui.displayGameOver(executioner.revealSecretWord(), executioner.countOfPossibleWords() == 1);
            //ask user if they would like to play again
            playAgain = ui.playAgain();
        }
    }
}
