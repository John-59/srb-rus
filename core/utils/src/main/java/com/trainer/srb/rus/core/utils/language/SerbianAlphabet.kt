package com.trainer.srb.rus.core.utils.language

object SerbianAlphabet {
        val cyrillicLetters = "љЉњЊеЕрРтТзЗуУиИоОпПшШђЂжЖаАсСдДфФгГхХјЈкКлЛчЧћЋџЏцЦвВбБнНмМ"
        .toCharArray()
        .sortedBy {
            it.code
        }

    val latinLetters = "eErRtTzZuUiIoOpPšŠđĐžŽaAsSdDfFgGhHjJkKlLčČćĆcCvVbBnNmM"
        .toCharArray()
        .sortedBy {
            it.code
        }

    fun cyrillicAsString() : String {
        return cyrillicLetters.mapIndexed { index, char ->
            "${index + 1}: $char ${char.code}"
        }.joinToString("\n")
    }

    fun latinAsString(): String {
        return latinLetters.mapIndexed { index, char ->
            "${index + 1}: $char ${char.code}"
        }.joinToString("\n")
    }
}