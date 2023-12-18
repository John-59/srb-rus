package com.trainer.srb.rus.feature.addword

import android.net.Uri
import androidx.lifecycle.SavedStateHandle

class AddWordArgs(
    val srbLatValue: String,
    val srbCyrValue: String,
    val rusValue: String
) {
    constructor(savedStateHandle: SavedStateHandle)
        : this(
            srbLatValue = Uri.decode(savedStateHandle.get<String?>(srbLatValueArgName).orEmpty()),
            srbCyrValue = Uri.decode(savedStateHandle.get<String?>(srbCyrValueArgName).orEmpty()),
            rusValue = Uri.decode(savedStateHandle.get<String?>(rusValueArgName).orEmpty())
        )

    companion object {
        const val srbLatValueArgName = "srb_lat"
        const val srbCyrValueArgName = "srb_cyr"
        const val rusValueArgName = "rus"
    }
}