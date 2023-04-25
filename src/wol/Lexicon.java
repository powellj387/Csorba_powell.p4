package wol;

import java.util.*;

public class Lexicon {
    private final Set<String> gameWords;

    public Lexicon(java.util.Collection<java.lang.String> words){
        gameWords = new TreeSet<>(words);
    }

    public java.util.Collection<java.lang.String> wordsOfLength(int length){
        TreeSet<String> userLengthWords = new TreeSet<>();
        for(String word:gameWords){
            if(word.length() == length){
                userLengthWords.add(word);
            }
        }
        return userLengthWords;
    }
}
