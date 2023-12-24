package com.trainer.srb.rus.feature.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val dictionary: IDictionary
): ViewModel() {

    private val learningWordsCount = 1 // 7

    private val _state = MutableStateFlow<LearnState>(LearnState.Initialize)
    val state: StateFlow<LearnState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = LearnState.Initialize
    )

//    private var translations = emptyList<Translation<Word.Serbian, Word.Russian>>()
    private var currentTranslation: Translation<Word.Serbian, Word.Russian>? = null

    init {
        viewModelScope.launch {
//            translations = dictionary.getRandom(learningWordsCount)
//            val firstTranslation = translations.firstOrNull()
            val firstTranslation = dictionary.getRandom(learningWordsCount).firstOrNull()
            if (firstTranslation == null) {
                _state.value = LearnState.Error("Словарь пуст.")
            } else {
                currentTranslation = firstTranslation
                _state.value = LearnState.ShowInSerbianWithTranslation(firstTranslation)
            }
        }
    }

    fun next() {
//        var random = translations.random()
//        while (random == currentTranslation) {
//            random = translations.random()
//        }
//        currentTranslation = random
//        _state.value = LearnState.ShowInSerbianWithTranslation(random)

        viewModelScope.launch {
            var random = dictionary.getRandom(1).firstOrNull()
            if (random == null) {
                _state.value = LearnState.Error("Словарь пуст.")
            } else {
                while (random == currentTranslation) {
                    random = dictionary.getRandom(1).firstOrNull()
                }
                currentTranslation = random
                if (random != null) {
                    _state.value = LearnState.ShowInSerbianWithTranslation(random)
                }
            }
        }
    }
}