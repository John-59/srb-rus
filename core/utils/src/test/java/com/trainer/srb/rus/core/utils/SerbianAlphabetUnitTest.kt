package com.trainer.srb.rus.core.utils

import com.trainer.srb.rus.core.utils.language.SerbianAlphabet
import org.junit.Assert
import org.junit.Test

class SerbianAlphabetUnitTest {
    @Test
    fun `cyrillic serbian alphabet has 30 letters`() {
//        println(SerbianAlphabet.cyrillicAsString())
        val expectedLettersCount = 30 * 2 // uppercase and lowercase
        Assert.assertEquals(expectedLettersCount, SerbianAlphabet.cyrillicLetters.count())
    }

    @Test
    fun `latin serbian alphabet has 54 letters`() {
//        println(SerbianAlphabet.latinAsString())
        val expectedLettersCount = 27 * 2 // uppercase and lowercase (60 minus Lj lj Nj nj Dž dž)
        Assert.assertEquals(expectedLettersCount, SerbianAlphabet.latinLetters.count())
    }
}