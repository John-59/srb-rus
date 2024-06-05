package com.trainer.srb.rus.core.utils

import com.trainer.srb.rus.core.utils.language.Language
import com.trainer.srb.rus.core.utils.language.LanguageDetector
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class LanguageDetectorUnitTest(
    private val text: String,
    private val language: Language
) {
    companion object {
        @JvmStatic
        @Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("kiša", Language.SERBIAN_LAT),
                arrayOf("дождь", Language.RUSSIAN),
                arrayOf("киша", Language.UNKNOWN),
                arrayOf("nema na čemu", Language.SERBIAN_LAT),
                arrayOf("нема на чему", Language.UNKNOWN),
                arrayOf("не за что", Language.UNKNOWN),
                arrayOf("могућност", Language.SERBIAN_CYR),
                arrayOf("mogućnost", Language.SERBIAN_LAT),
                arrayOf("возможность", Language.RUSSIAN)
            )
        }
    }

    @Test
    fun detect() {
        val detectedLanguage = LanguageDetector.tryDefineLanguage(text)
        Assert.assertEquals(language, detectedLanguage)
    }
}