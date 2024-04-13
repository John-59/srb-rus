package com.trainer.srb.rus.core.exercise

data object ExerciseUndefined: Exercise() {
    override suspend fun next(): ExerciseStep {
        return ExerciseStep.Finished(emptyList())
    }
}