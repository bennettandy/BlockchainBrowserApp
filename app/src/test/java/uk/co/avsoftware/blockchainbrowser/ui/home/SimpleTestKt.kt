package uk.co.avsoftware.blockchainbrowser.ui.home

import org.junit.Assert
import org.junit.Test

class SimpleTestKt {

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
    fun testSolution() {
        Assert.assertEquals("best the is Kotlin", reverseWords("Kotlin is the best "))
        Assert.assertEquals("world Hello!", reverseWords("Hello!  world "))
        Assert.assertEquals("", reverseWords(" "))
    }

    // using fold as reduce can't work on an empty list
    fun reverseWords(s: String): String = s.split("\\s+".toRegex())
        .fold( "" ) { s1: String, s2: String -> "$s2 $s1" }.trim()

}