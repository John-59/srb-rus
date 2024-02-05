package com.trainer.srb.rus.feature.editword

import androidx.lifecycle.SavedStateHandle

class EditWordArgs(val latinValueId: Long) {

    constructor(savedStateHandle: SavedStateHandle)
        : this(savedStateHandle.get<Long>(latinValueIdArgName) ?: -1)

    companion object {
        const val latinValueIdArgName = "srb_latin_id"
    }
}