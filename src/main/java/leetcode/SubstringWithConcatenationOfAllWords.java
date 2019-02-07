package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
    You are given a string, s, and a list of words, words, that are all of the same length.
    Find all starting indices of substring(s) in s that is a concatenation of each word
    in words exactly once and without any intervening characters.

    Input:
      s = "barfoothefoobarman",
      words = ["foo","bar"]

    Output: [0,9]

    Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
    The output order does not matter, returning [9,0] is fine too.

    Input:
      s = "wordgoodgoodgoodbestword",
      words = ["word","good","best","word"]

    Output: []
 */

public class SubstringWithConcatenationOfAllWords {

    public List<Integer> findSubstrings(String s, String[] words) {
        List<Integer> list = new ArrayList<>();
        Map<String, Integer> dictionary = new HashMap<>();

        if (words.length == 0)
            return list;

        int i = 0, len = words[0].length();

        if (len == 0) {
            return list;
        }

        for (String w : words) {
            // checking length of words
            if (w.length() != len) {
                return list;
            }

            // increment the number of times word W was seen
            dictionary.put(w, dictionary.getOrDefault(w, 0) + 1);
        }

        Map<String, Integer> currentPass = new HashMap<>();

        // iterate from 0th character to max possible start character
        while (i < s.length() - words.length * len + 1) {
            currentPass.clear();
            int j = i;
            while (j + len <= s.length()) {
                String currentWord = s.substring(j, j + len);

                // increment the number of times word currentWord was seen during current pass
                currentPass.put(currentWord, currentPass.getOrDefault(currentWord ,0) + 1);

                // we should stop current pass if:
                // current word not in dictionary OR it's in dictionary, but fewer times
                if (dictionary.containsKey(currentWord)) {
                    if (dictionary.get(currentWord) < currentPass.get(currentWord))
                        break;
                } else {
                    break;
                }

                j = j + len;
            }

            // if we stopped right after the correct number of characters in dictionary
            // then we have seen all words of dictionary, starting from i-th position
            if ((j - i) == words.length * len)
                list.add(i);

            // move to scanning from next starting position
            i++;
        }

        return list;
    }

    public static void main(String[] args) {
        SubstringWithConcatenationOfAllWords finder = new SubstringWithConcatenationOfAllWords();

        System.out.println(finder.findSubstrings("abd0", new String[] {"ab", "de"}));
        System.out.println(finder.findSubstrings("abde", new String[] {"ab", "de"}));
        System.out.println(finder.findSubstrings("000defabc000abcdef000abdef0defac", new String[] {"abc", "def"}));
        System.out.println(finder.findSubstrings("barfoothefoobarman", new String[] {"foo", "bar"}));
        System.out.println(finder.findSubstrings("wordgoodstudentgoodword", new String[] {"word", "student"}));
    }
}