package wol;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleInterface {
    Scanner scan;
    PrintStream output;

    public ConsoleInterface(java.util.Scanner scanner,java.io.PrintStream out){
        scan = scanner;
        output = out;
    }

    public int askForWordLength(Lexicon lexicon){

    }

    public int askForMaximumMisses(){

    }

    public boolean askToDisplayWordCount(){

    }

    public char askNextGuess(Executioner executioner){

    }

    public boolean playAgain(){

    }

    public void displayGameState(Executioner executioner, boolean displayWordCount){

    }

    public void displayResultsOfGuess(char guess, int occurrences){

    }

    public void displayGameOver(java.lang.String secretWord, boolean playerWins){

    }

    public java.lang.String selectSecretWord(java.util.Collection<java.lang.String> secretWords){

    }
}
