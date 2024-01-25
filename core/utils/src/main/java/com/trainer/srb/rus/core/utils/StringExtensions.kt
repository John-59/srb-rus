package com.trainer.srb.rus.core.utils

fun String?.getDatabaseVersion(): Int {
    return this?.split('.')?.lastOrNull()?.toIntOrNull() ?: -1
}