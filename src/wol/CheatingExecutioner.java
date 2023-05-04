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


    public int incorrectGuessesRemaining() {
        return remainingGuesses;
    }


    public Collection<Character> guessedLetters() {
        return guessedLetters;
    }


    public String formattedSecretWord() {
        //takes families which have already been kept and places the letters into the secret word accordingly
        StringBuilder formattedWord = new StringBuilder();
        for (char c : possibleWords.iterator().next().toCharArray()) {
            if (guessedLetters.contains(Character.toUpperCase(c))) {
                formattedWord.append(Character.toUpperCase(c));
            } else {
                formattedWord.append(invalidCharacter);
            }
        }
        return formattedWord.toString();
    }


    public int countOfPossibleWords() {
        return possibleWords.size();
    }


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
            if(entry.getValue().size() > mostWords ){
                mostWords = entry.getValue().size();
                largestFamily.clear();
                largestFamily.addAll(entry.getValue());
            }
        }
        if(largestFamily.size() == 1){
            secretWord = largestFamily.get(0);
        }
        return largestFamily;
    }

    //method to find the total number of occurrences and decrements the
    // number of remaining guesses if there are no occurrences
    private int checkForOccurrences (char letter){
        //checks the family to see how many times the letter is present
        int occurences = 0;
            for(char c:possibleWords.iterator().next().toCharArray()){
                if (c == letter){
                    occurences++;
                }
            }
        //decrements the number of remaining guesses if the letter is not present in the word family
        if(occurences==0){
            remainingGuesses--;
        }
        return occurences;
    }


    public int registerAGuess(char letter) {
        //add guess to the list of guessed letters
        guessedLetters.add(Character.toUpperCase(letter));

        //gets map of different patterns stemming from guess
        Map<String, List<String>> familyMap = patternMap(letter);

        //finds the pattern with the largest number of words
        List<String> wordsToKeep = largestFamily(familyMap);

        // Remove words not in the largest family
        possibleWords.retainAll(wordsToKeep);

        //checks for occurrences
        int occurrences = checkForOccurrences(letter);

        //check to see if the game is over
        setGameOver();

        //checks the family to see how many times the letter is present then returns the value
        return occurrences;
    }


    public String revealSecretWord() {
        return secretWord.toUpperCase();
    }

    //private method to get called after every guess to ensure that the player has enough guesses to make another turn
    private void setGameOver(){
        if(remainingGuesses==0){
            secretWord = possibleWords.iterator().next();
            gameOver = true;
        }
    }


    public boolean isGameOver() {
            return gameOver;
    }
}
