package wol;

import java.util.*;

public class CheatingExecutioner implements Executioner{
    private final Collection<String> possibleWords;
    private int remainingGuesses;
    private final Collection<Character> guessedLetters;
    private String secretWord;
    private boolean gameOver;
    private char invalidCharacter;

    public CheatingExecutioner(){
        // Default constructor for Cheating Executioner
        possibleWords = new TreeSet<>();
        guessedLetters = new ArrayList<>();
        secretWord = null;
        gameOver = false;
    }

    @Override
    public void newGame(Collection<String> words, int maxIncorrectGuesses, char invalidChar) {
        // Prepares the executioner for a new game with the given collection of potential secret words.
        possibleWords.clear();
        possibleWords.addAll(words);
        remainingGuesses = maxIncorrectGuesses;
        invalidCharacter = invalidChar;
        guessedLetters.clear();
        secretWord = null;
        gameOver = false;
    }

    @Override
    public int incorrectGuessesRemaining() {
        return remainingGuesses;
    }

    @Override
    public Collection<Character> guessedLetters() {
        return guessedLetters;
    }

    @Override
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

    @Override
    public int countOfPossibleWords() {
        return possibleWords.size();
    }

    @Override
    public boolean letterAlreadyGuessed(char letter) {
        return guessedLetters.contains(Character.toUpperCase(letter));
    }

    private Map<String, List<String>> patternMap(char letter) {
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
        return wordFamilies;
    }

    private List<String> largestFamily(Map<String, List<String>> map){
        // Select largest word family
        int mostWords = -1;
        List<String> largestFamily = new ArrayList<>(Collections.emptyList());
        //goes through the entire map looking for the pattern with the most words
        for( var entry : map.entrySet() ){
            // see if this family has the most words
            if( entry.getValue().size() > mostWords ){
                mostWords = entry.getValue().size();
                largestFamily.clear();
                largestFamily.addAll(entry.getValue());
            }
        }
        return largestFamily;
    }


    @Override
    public int registerAGuess(char letter) {
        int occurences = 0;
        //add guess to the list of guessed letters
        guessedLetters.add(Character.toUpperCase(letter));

        //gets map of different patterns stemming from guess
        Map<String, List<String>> familyMap = patternMap(letter);

        //finds the pattern with the largest number of words
        List<String> wordsToKeep = largestFamily(familyMap);

        // Remove words not in the largest family
        possibleWords.retainAll(wordsToKeep);

        //checks the secret word to see how many times the letter is present
        boolean letterPresent = false;
        for(String value:possibleWords){
            for(char c:value.toCharArray()){
                if (c == letter){
                    letterPresent = true;
                    occurences++;
                }
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

    @Override
    public String revealSecretWord() {
        return secretWord.toUpperCase();
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }
}
