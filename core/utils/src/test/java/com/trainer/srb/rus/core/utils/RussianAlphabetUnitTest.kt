package com.trainer.srb.rus.core.utils

import com.trainer.srb.rus.core.utils.language.RussianAlphabet
import org.junit.Assert.assertEquals
import org.junit.Test

class RussianAlphabetUnitTest {
    @Test
    fun `russian alphabet has 33 letters`() {
//        println(RussianAlphabet.asString())
        val expectedLettersCount = 33 * 2 // uppercase and lowercase
        assertEquals(expectedLettersCount, RussianAlphabet.letters.count())
    }
}