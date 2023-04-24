package wol;

import java.util.Collection;

public class ConsoleExecutioner implements Executioner {
    ConsoleInterface userInt;

    public ConsoleExecutioner(ConsoleInterface ui) {
        userInt = ui;
    }

    @Override
    public void newGame(Collection<String> words, int maxIncorrectGuesses, char invalidChar) {

    }

    @Override
    public int incorrectGuessesRemaining() {
        return 0;
    }

    @Override
    public Collection<Character> guessedLetters() {
        return null;
    }

    @Override
    public String formattedSecretWord() {
        return null;
    }

    @Override
    public int countOfPossibleWords() {
        return 0;
    }

    @Override
    public boolean letterAlreadyGuessed(char letter) {
        return false;
    }

    @Override
    public int registerAGuess(char letter) {
        return 0;
    }

    @Override
    public String revealSecretWord() {
        return null;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
