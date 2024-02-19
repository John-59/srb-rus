package com.trainer.srb.rus.core.utils

interface IAppVersionProvider {
    val version: String
    val predefinedDatabaseVersion: Int
}