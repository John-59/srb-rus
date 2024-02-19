package com.trainer.srb.rus.core.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class AppVersionProvider(
    private val context: Context
): IAppVersionProvider {

    override val version: String
        get() {
            val packageManager = context.packageManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                packageManager.getPackageInfo(
                    context.packageName,
                    0
                )
            }.versionName
        }

    override val predefinedDatabaseVersion: Int
        get() {
            return version.getDatabaseVersion()
        }
}