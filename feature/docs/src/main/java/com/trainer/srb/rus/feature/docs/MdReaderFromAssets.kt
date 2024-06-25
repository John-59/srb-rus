package com.trainer.srb.rus.feature.docs

import android.content.Context
import java.io.InputStreamReader

class MdReaderFromAssets(
    private val context: Context
): IMdReader {

    override fun read(fileName: String): String {
        return try {
            context.assets.open(fileName).use { stream ->
                InputStreamReader(stream).use { reader ->
                    reader.readText().replace(
                        oldValue = "./img/",
                        newValue = "file:///android_asset/img/",
                        ignoreCase = true
                    )
                }
            }
        } catch (ex: Exception) {
            ""
        }
    }
}