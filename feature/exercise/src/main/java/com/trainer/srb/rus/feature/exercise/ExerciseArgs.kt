package com.trainer.srb.rus.feature.exercise

import android.net.Uri
import androidx.lifecycle.SavedStateHandle

class ExerciseArgs(
    val exerciseType: ExerciseType
) {
    constructor(savedStateHandle: SavedStateHandle)
        : this(
            exerciseType = try {
                ExerciseType.valueOf(Uri.decode(savedStateHandle.get<String?>(exerciseTypeArgName).orEmpty()))
            } catch (ex: Exception) {
                ExerciseType.UNDEFINED
            }
        )
    companion object {
        const val exerciseTypeArgName = "exercise_type"
    }
}