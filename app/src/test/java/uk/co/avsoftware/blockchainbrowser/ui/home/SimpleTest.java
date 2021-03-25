package uk.co.avsoftware.blockchainbrowser.ui.home;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SimpleTest {

    /*
     * Given an input string s, reverse the order of the words.
     *
     * Note that s may contain leading or trailing spaces or multiple spaces between two words.
     * The returned string should only have a single space separating the words. Do not include any extra spaces.
     *
     * String split isn't available
     *
     */

    @Test
    public void testSolution() {

        assertEquals("best the is Kotlin", reverseWords("Kotlin is the best "));
        assertEquals("world Hello!", reverseWords("Hello!  world "));
        assertEquals("", reverseWords(" "));
    }

        public String reverseWords (String s){
            return Arrays.stream(s.split("\\s+")).reduce((s1, s2) -> s2 + " " + s1).orElse("");
        }
    }


