package wol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
public class ConsoleExecutioner implements Executioner {
    ConsoleInterface userInt;
    private Collection<String> possibleWords;
    private int remainingGuesses;
    private char invalidChar;
    private Collection<Character> guessedLetters;
    private String secretWord;
    private boolean gameOver;

    public ConsoleExecutioner(ConsoleInterface ui) {

        userInt = ui;
        // Default constructor for Cheating Executioner
        possibleWords = new HashSet<>();
        guessedLetters = new ArrayList<>();
        gameOver = false;
    }


    public void newGame(Collection<String> words, int maxIncorrectGuesses, char invalidChar) {
        // Prepares the executioner for a new game with the given collection of potential secret words.
        possibleWords.clear();
        possibleWords.addAll(words);
        remainingGuesses = maxIncorrectGuesses;
        this.invalidChar = invalidChar;
        guessedLetters.clear();
        secretWord = null;
        gameOver = false;
    }


    public int incorrectGuessesRemaining() {
        // Returns the number of incorrect letter guesses the condemned can make before losing.
        return remainingGuesses;
    }

    public Collection<Character> guessedLetters() {
        // Returns a collection of the letters the condemned has already guessed. This collection must be in sorted order. The letters in this collection must be in upper case.
        return guessedLetters;
    }

    public String formattedSecretWord() {
        // Return a specially formatted version of the secret word. Correctly guessed letters are filled in with upper case characters. Hidden letters are represented by the given invalidChar.
        if (secretWord == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(Character.toUpperCase(c))) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(invalidChar);
            }
        }
        return sb.toString();
    }

    public int countOfPossibleWords() {
        // Returns the count of words that could still be the secret word. This number depends on the list of words given in the constructor, the guesses made by the condemned, and the responses to those guesses given by the executioner.
        return possibleWords.size();
    }

    public boolean letterAlreadyGuessed(char letter) {
        // Returns true if the given letter has already been guessed by the condemned.
        return guessedLetters.contains(Character.toUpperCase(letter));
    }

    public int registerAGuess(char letter) {
        // Convert the guess to uppercase
        letter = Character.toUpperCase(letter);

        // Check if the letter has already been guessed
//        if (guessedLetters.contains(letter)) {
//            userInt.displayGameState();
//            return -1;
//        }

        // Add the guessed letter to the collection of guessed letters
        guessedLetters.add(letter);

        // Divide the possible words into word families based on the guessed letter
        ArrayList<ArrayList<String>> wordFamilies = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            wordFamilies.add(new ArrayList<String>());
        }
        for (String word : possibleWords) {
            int index = letter - 'A';
            if (word.indexOf(letter) != -1) {
                index = letter - 'A';
            } else if (word.indexOf(Character.toLowerCase(letter)) != -1) {
                index = Character.toLowerCase(letter) - 'a';
            }
            wordFamilies.get(index).add(word);
        }

        // Select the largest word family
        ArrayList<String> largestWordFamily = wordFamilies.get(0);
        for (int i = 1; i < 26; i++) {
            if (wordFamilies.get(i).size() > largestWordFamily.size()) {
                largestWordFamily = wordFamilies.get(i);
            }
        }

        // Remove any words from the possible word list that are not in the largest word family
        possibleWords.retainAll(largestWordFamily);

        // Decrement the remaining guess count if the guessed letter does not appear in the word family
        if (largestWordFamily.indexOf(letter) == -1 && largestWordFamily.indexOf(Character.toLowerCase(letter)) == -1) {
            remainingGuesses--;
        }

        // Check if the game is over
        if (remainingGuesses == 0 || possibleWords.size() == 1) {
            gameOver = true;
        }

        // Return the number of occurrences of the given letter in the largest word family
        int count = 0;
        for (String word : largestWordFamily) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    count++;
                }
            }
        }
        return count;
    }

    public String revealSecretWord() {
        // Select any still possible word as the secret word
        secretWord = possibleWords.iterator().next();
        return secretWord.toUpperCase();
    }

    public boolean isGameOver() {
        // Returns true if the game is over
        return gameOver;
    }
}
