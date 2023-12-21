package com.trainer.srb.rus.feature.repository

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

data class SerbianToRussianWord(
    @Embedded
    val serbianLat: SerbianLatinWord,
    @Relation(
        parentColumn = "id",
        entityColumn = "latId"
    )
    val serbianCyr: SerbianCyrillicWord?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = SerbianRussianCrossRefTable::class,
            parentColumn = "srbLatWordId",
            entityColumn = "rusWordId"
        )
    )
    val russians: List<RussianWord>
) {
    fun toTranslation(): Translation<Word.Serbian, Word.Russian> {
        return Translation(
            source = Word.Serbian(
                latinId = serbianLat.id,
                cyrillicId = serbianCyr?.id ?: 0,
                latinValue = serbianLat.word,
                cyrillicValue = serbianCyr?.word.orEmpty()
            ),
            translations = russians.map {
                Word.Russian(
                    id = it.id,
                    value = it.word
                )
            }
        )
    }
}
