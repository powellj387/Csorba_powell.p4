package wol;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class WheelOfLies {
    private Lexicon gameLex;
    private ConsoleInterface gameUI;
    private Executioner gameEx;
    private char invalidChar;

    public WheelOfLies(Lexicon lexicon, ConsoleInterface ui,Executioner executioner,char invalidCharacter){
        gameLex = lexicon;
        gameUI = ui;
        gameEx = executioner;
        invalidChar = invalidCharacter;
    }

    public void playGame(){
            boolean playAgain = true;
            while (playAgain) {
                char invalidCharacter = '*';
                //Ask for the length of the word
                int wordLength = gameUI.askForWordLength(gameLex);
                //Ask for the number of allowed guesses
                int numGuessesAllowed = gameUI.askForMaximumMisses();
                //Ask the user if they would like to see the word count
                boolean displayCount = gameUI.askToDisplayWordCount();
                //Prepare the executioner for a new game
                gameEx.newGame(gameLex.wordsOfLength(wordLength), numGuessesAllowed, invalidCharacter);
                //Display number of guesses left
                gameUI.displayGameState(gameEx, displayCount);
                boolean gotTheWord = false;
                while (!gameEx.isGameOver()&&!gotTheWord) {
                    gotTheWord = false;
                    //ask user for a guess
                    char guess = gameUI.askNextGuess(gameEx);
                    //display results of guess
                    gameUI.displayResultsOfGuess(guess, gameEx.registerAGuess(guess));
                    if(gameEx.isGameOver())
                    {
                        gotTheWord = true;
                    }
                    else {
                        //display the game state
                        gameUI.displayGameState(gameEx, displayCount);
                    }
                }
                //Display the game over text
                gameUI.displayGameOver(gameEx.revealSecretWord(), gameEx.countOfPossibleWords() == 1);
                //ask user if they would like to play again
                playAgain = gameUI.playAgain();
            }
    }
}
