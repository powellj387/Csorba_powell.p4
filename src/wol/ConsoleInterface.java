package wol;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInterface {
    Scanner scan;
    PrintStream output;

    public ConsoleInterface(java.util.Scanner scanner,java.io.PrintStream out){
        scan = scanner;
        output = out;
    }

    public int askForWordLength(Lexicon lexicon){
        int size = 0;
        boolean validInput = false;
        while (!validInput) {
            output.print("Enter word length: ");
                size = Integer.parseInt(scan.nextLine());
                if (size <= 0) {
                    output.println("Please enter a positive integer.");
                } else if (lexicon.wordsOfLength(size).size()==0) {
                    output.println("There are no words of length " + size + " in the lexicon.");
                } else {
                    validInput = true;
                }
            }
        return size;
    }

    public int askForMaximumMisses(){
        int maxMisses = 0;
        boolean validInput = false;
        while (!validInput) {
            output.print("Enter number of guesses: ");
            maxMisses = Integer.parseInt(scan.nextLine());
            if (maxMisses <= 0) {
                output.println("Please enter a positive integer.");
            } else {
                validInput = true;
            }
        }
        return maxMisses;
    }

    public boolean askToDisplayWordCount(){
        boolean wantRunningTotal = false;
        Character decision = null;
        output.print("Would you like to see the word count (Y/N)? ");
        decision = scan.nextLine().charAt(0);

        if (decision.equals('Y')) {
            wantRunningTotal = true;
        }

        return wantRunningTotal;
    }

    public char askNextGuess(Executioner executioner){
        Character guess = null;
        boolean validInput = false;

        while(!validInput){
            output.print("Guess a letter: ");
            guess = scan.nextLine().toLowerCase().charAt(0);
            if(!Character.isLetter(guess)){
                output.println("That is not a letter. Please enter a letter!");
            } else if (executioner.letterAlreadyGuessed(guess)) {
                output.println("You've already guesses the letter" + guess + ". PLease enter a new letter!");
            } else{
                validInput = true;
            }
        }
        return guess;
    }

    public boolean playAgain(){
        boolean wantsToPlay = false;
        Character decision = null;

        output.println("Would you like to play another game(Y/N)? ");
        decision = scan.nextLine().charAt(0);

        if (decision.equals('Y')) {
            wantsToPlay = true;
        }

        return wantsToPlay;
    }

    public void displayGameState(Executioner executioner, boolean displayWordCount){
        output.println("You have"+executioner.incorrectGuessesRemaining()+"guesses left.");

        ArrayList<Character> usedLetters = new ArrayList<>(executioner.guessedLetters());
        output.print("Used letters:");
        for(Character letter: usedLetters){
            output.print(Character.toUpperCase(letter) + " ");
        }

        output.println("Word: " + executioner.formattedSecretWord());

        if(displayWordCount){
            output.println("Word count: " + executioner.countOfPossibleWords());
        }
    }

    public void displayResultsOfGuess(char guess, int occurrences){
        if(occurrences == 0){
            output.println("Sorry there are no"+ guess+"'s");
        }else {
            output.println("There is/are " + occurrences + " " + guess + "'s");
        }
    }

    public void displayGameOver(java.lang.String secretWord, boolean playerWins){
        if(playerWins){
            output.println("Good job!!! The word was "+secretWord);
        }else{
            output.println("You lose! The word was "+secretWord);
        }
    }

    public java.lang.String selectSecretWord(java.util.Collection<java.lang.String> secretWords){
        boolean validWord = false;
        String userWord = null;
        output.println("Possible Secret Words:");
        for(String word:secretWords){
            output.println(word);
        }

        while(!validWord){
            output.println("Please enter one of the passwords: ");
            userWord = scan.nextLine();
            if(secretWords.contains(userWord)){
                validWord = true;
            } else{
                output.println("Please enter one of the passwords: ");
            }
        }
        return userWord;
    }
}
