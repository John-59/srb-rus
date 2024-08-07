package com.trainer.srb.rus.feature.docs

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.commonmark.node.Document
import org.commonmark.parser.Parser
import javax.inject.Inject

@HiltViewModel
class LearningManualViewModel @Inject constructor(
    reader: IMdReader
): ViewModel() {

    val document = Parser.builder().build()
        .parse(
            reader.read("learning.md")
        ) as Document
}