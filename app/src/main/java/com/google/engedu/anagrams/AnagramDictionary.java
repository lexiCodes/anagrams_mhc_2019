/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random;


public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList; // dictionary
    private HashSet<String> wordSet;
    private HashMap<String,ArrayList<String>> lettersToWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        wordList = new ArrayList<String>();
        wordSet = new HashSet<String>();
        lettersToWord = new HashMap<String,ArrayList<String>>();

        while((line = in.readLine()) != null) {
            String word = line.trim();

            String key = sortLetters(word);

            if (lettersToWord.containsKey(key)) {
                lettersToWord.get(key).add(word);
            }
            else {
                ArrayList<String> aList = new ArrayList<String>();
                aList.add(word);
                lettersToWord.put(key,aList);
            }

            wordSet.add(word);

            wordList.add(word);
        }

        isGoodWord("nonstop", "stop");
    }

    public boolean isGoodWord(String word, String base) {
        if (!wordSet.contains(word) || word.contains(base)) {
            Log.i("Alexis", "it is false");
            return false;
        }

        return true;
    }


    // returns all anagrams of 'word' in dictionary
    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        int leng = targetWord.length();

        for (int i = 0; i < wordList.size(); i++ ) {
            String currentWord = wordList.get(i);

            if (currentWord.length() == leng && sortLetters(currentWord).equals(sortLetters(targetWord))) {
                result.add(currentWord);
            }
        }

        return result;
    }

    // helper function (call it sortLetters) that
    // takes a String and returns another String with the same letters in alphabetical order
    // (e.g. "post" -> "opst").
    public String sortLetters(String originWord) {
        char temp[] = originWord.toCharArray();

        Arrays.sort(temp);

        return new String (temp);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        char ch = 'a';
        while (ch <= 'z') {
            String newWord = word + ch;

            List<String> anagramList = new ArrayList<String>();
            anagramList = getAnagrams(newWord);

            result.addAll(anagramList);
            ch++;
        }

        return result;
    }

    public String pickGoodStarterWord() {
        Random rand = new Random();
        int startPoint = rand.nextInt(10000) + 1;

        while (startPoint <= 10000) {
            if (getAnagramsWithOneMoreLetter(wordList.get(startPoint)).size() > MIN_NUM_ANAGRAMS){
                return wordList.get(startPoint);
            }

            startPoint++;
            if (startPoint > 10000) {
                startPoint = 0;
            }
        }

        // Milestone 2 almost finished; maybe check more later
        // iterating for milestone2 ???
        // Milestone 3 left
        return "stop";
    }
}
