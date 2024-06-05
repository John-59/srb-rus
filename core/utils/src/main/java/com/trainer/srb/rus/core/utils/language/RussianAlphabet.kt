package com.trainer.srb.rus.core.utils.language

object RussianAlphabet {
    val letters = "йЙцЦуУкКеЕёЁнНгГшШщЩзЗхХъЪфФыЫвВаАпПрРоОлЛдДжЖэЭяЯчЧсСмМиИтТьЬбБюЮ"
        .toCharArray()
        .sortedBy {
            it.code
        }

    fun asString(): String {
        return letters.mapIndexed { index, char ->
            "${index + 1}: $char ${char.code}"
        }.joinToString("\n")
    }
}