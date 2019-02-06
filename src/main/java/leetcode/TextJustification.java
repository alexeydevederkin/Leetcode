package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
    Given an array of words and a width maxWidth, format the text such that each line
    has exactly maxWidth characters and is fully (left and right) justified.

    You should pack your words in a greedy approach; that is, pack as many words as you can in each line.
    Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.

    Extra spaces between words should be distributed as evenly as possible.
    If the number of spaces on a line do not divide evenly between words,
    the empty slots on the left will be assigned more spaces than the slots on the right.

    For the last line of text, it should be left justified and no extra space is inserted between words.
*/

public class TextJustification {

    private static void addSpaces(StringBuilder builder, int numberOfSpaces) {
        for (int i = 0; i < numberOfSpaces; i++)
            builder.append(" ");
    }

    private static List<String> justify(String[] words, int maxWidth) {
        List<String> justifiedLines = new ArrayList<>();

        StringBuilder lineBuilder;
        int currentLineWidth;
        int wordsInLine;
        int extraSpaces;
        int lineStart = 0;

        while (lineStart < words.length) {
            lineBuilder = new StringBuilder();
            currentLineWidth = 0;

            for (int i = lineStart; i < words.length; i++) {
                if (currentLineWidth + words[i].length() > maxWidth) {
                    // "+1" - the last space goes inside
                    extraSpaces = maxWidth - currentLineWidth + 1;
                    wordsInLine = i - lineStart;

                    if (wordsInLine == 1) {
                        lineBuilder.append(words[lineStart]);
                        addSpaces(lineBuilder, extraSpaces);
                    } else {
                        // regular line
                        int gaps = wordsInLine - 1;
                        int space = 1 + extraSpaces / gaps;
                        int bigSpacesCount = gaps;
                        if (extraSpaces % gaps != 0) {
                            space += 1;
                            bigSpacesCount = extraSpaces % gaps;
                        }

                        for (int wordIdx = 0; wordIdx < wordsInLine; wordIdx++) {
                            lineBuilder.append(words[lineStart + wordIdx]);
                            if (wordIdx < bigSpacesCount) {
                                addSpaces(lineBuilder, space);
                            } else if (wordIdx != wordsInLine - 1) {
                                addSpaces(lineBuilder, space-1);
                            }
                        }
                    }

                    justifiedLines.add(lineBuilder.toString());
                    lineStart += wordsInLine;
                    break;

                } else {
                    currentLineWidth += words[i].length() + 1;

                    if (i == words.length - 1) {
                        // last line - left justified
                        wordsInLine = words.length - lineStart;
                        for (int wordIdx = 0; wordIdx < wordsInLine-1; wordIdx++) {
                            lineBuilder.append(words[lineStart + wordIdx]);
                            lineBuilder.append(" ");
                        }
                        lineBuilder.append(words[words.length-1]);
                        addSpaces(lineBuilder, maxWidth - lineBuilder.length());
                        justifiedLines.add(lineBuilder.toString());
                        lineStart = words.length;
                    }
                }
            }
        }

        return justifiedLines;
    }

    public static void main(String[] args) {
        justify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16).forEach(System.out::println);
        //justify(new String[]{"a", "b", "c", "d", "e"}, 1).forEach(System.out::println);
        //justify(new String[]{"What","must","be","shall","be."}, 12).forEach(System.out::println);
    }
}