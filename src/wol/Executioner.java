package wol;

public interface Executioner {
    void newGame(java.util.Collection<java.lang.String> words, int maxIncorrectGuesses, char invalidChar);

    int incorrectGuessesRemaining();

    java.util.Collection<java.lang.Character> guessedLetters();

    java.lang.String formattedSecretWord();

    int countOfPossibleWords();

    boolean letterAlreadyGuessed(char letter);

    int registerAGuess(char letter);

    java.lang.String revealSecretWord();

    boolean isGameOver();
}
