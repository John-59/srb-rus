package com.trainer.srb.rus.feature.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.utils.IAppVersionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    appVersionProvider: IAppVersionProvider,
    dictionary: IDictionary
): ViewModel() {

    val appVersion = appVersionProvider.version

    val totalTranslationsCount = dictionary.totalTranslationsCount.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    val userTranslationsCount = dictionary.userTranslationCount.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    val learningTranslationsCount = dictionary.learningTranslationsCount.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    val learnedTranslationsCount = dictionary.learnedTranslationsCount.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    private val _isLearningCurveManualVisible = MutableStateFlow(false)
    val isLearningCurveManualVisible = _isLearningCurveManualVisible.asStateFlow()

    private val _isDictionaryManualVisible = MutableStateFlow(false)
    val isDictionaryManualVisible = _isDictionaryManualVisible.asStateFlow()

    fun showLearningCurveManual() {
        _isLearningCurveManualVisible.value = true
    }

    fun hideLearningCurveManual() {
        _isLearningCurveManualVisible.value = false
    }

    fun showDictionaryManual() {
        _isDictionaryManualVisible.value = true
    }

    fun hideDictionaryManual() {
        _isDictionaryManualVisible.value = false
    }
}