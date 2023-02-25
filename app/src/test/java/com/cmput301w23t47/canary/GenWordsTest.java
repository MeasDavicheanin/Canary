package com.cmput301w23t47.canary;
import com.cmput301w23t47.canary.model.GenWords;

import org.junit.Test;
public class GenWordsTest {
    @Test
    public void testGenWords() {
       GenWords genWords= new GenWords();
       String word = genWords.getWord();
       assert(word.length() > 0);
    }
}
