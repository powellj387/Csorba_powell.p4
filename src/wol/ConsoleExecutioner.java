package wol;

import java.util.*;

public class ConsoleExecutioner implements Executioner {
    ConsoleInterface userInt;
    private final Collection<String> possibleWords;
    private int remainingGuesses;
    private final Collection<Character> guessedLetters;
    private String secretWord;
    private boolean gameOver;
    private char invalidCharacter;

    public ConsoleExecutioner(ConsoleInterface ui) {

        userInt = ui;
        // Default constructor for Cheating Executioner
        possibleWords = new TreeSet<>();
        guessedLetters = new ArrayList<>();
        secretWord = null;
        gameOver = false;
    }


    public void newGame(Collection<String> words, int maxIncorrectGuesses, char invalidChar) {
        // Prepares the executioner for a new game with the given collection of potential secret words.
        possibleWords.clear();
        possibleWords.addAll(words);
        remainingGuesses = maxIncorrectGuesses;
        invalidCharacter = invalidChar;
        guessedLetters.clear();
        secretWord = userInt.selectSecretWord(possibleWords);
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
        StringBuilder formattedWord = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(Character.toUpperCase(c))) {
                formattedWord.append(Character.toUpperCase(c));
            } else {
                formattedWord.append(invalidCharacter);
            }
        }
        return formattedWord.toString();
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
       int occurences = 0;
        //add guess to the list of guessed letters
        guessedLetters.add(Character.toUpperCase(letter));

        //create a map of different patterns stemming from guess
        Map<String, List<String>> wordFamilies = new HashMap<>();
        for (String word : possibleWords) {
            StringBuilder sb = new StringBuilder();
            for (char c : word.toCharArray()) {
                if (c == letter) {
                    sb.append(letter);
                } else {
                    sb.append("_");
                }
            }
            String pattern = sb.toString();
            if (!wordFamilies.containsKey(pattern)) {
                wordFamilies.put(pattern, new ArrayList<>());
            }
            wordFamilies.get(pattern).add(word);
        }

        //creates the pattern for the secret word
        StringBuilder sb = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (c == letter) {
                sb.append(letter);
            } else {
                sb.append("_");
            }
        }
        String secretPattern = sb.toString();

        // Select largest word family
        List<String> closestFamily = Collections.emptyList();
        Set<String> keys = wordFamilies.keySet();
        for (String pattern : keys) {
            if (pattern.equals(secretPattern)) {
                closestFamily = wordFamilies.get(pattern);
            }
        }

        // Remove words not in closest family
        possibleWords.retainAll(closestFamily);

        //checks the secret word to see how many times the letter is present
        for(int i=0;i<secretWord.length();++i){
            if (letter == secretWord.charAt(i)){
                occurences++;
            }
        }
        //decrements the number of remaining guesses if the letter is not present in the secret word
        if(occurences==0){
            remainingGuesses--;
        }

        if(remainingGuesses==0){
            gameOver = true;
        }
        return occurences;
    }

    public String revealSecretWord() {
        // Select any still possible word as the secret word
        return secretWord.toUpperCase();
    }

    public boolean isGameOver() {
        // Returns true if the game is over
        return gameOver;
    }
}
