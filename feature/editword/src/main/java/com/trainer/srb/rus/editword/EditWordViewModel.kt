package com.trainer.srb.rus.editword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditWordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val args = EditWordArgs(savedStateHandle)
}