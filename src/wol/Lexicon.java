package wol;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Lexicon {
    private final Set<String> gameWords;

    public Lexicon(java.util.Collection<java.lang.String> words){
        gameWords = new HashSet<>(words);
    }

    public java.util.Collection<java.lang.String> wordsOfLength(int length){
        Collection<String> userLengthWords = new HashSet<>();
        for(String word:gameWords){
            if(word.length() == length){
                userLengthWords.add(word);
            }
        }
        return userLengthWords;
    }
}
