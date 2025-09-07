package com.example.data.settings

import android.content.Context
import com.example.core.settings.Settings
import com.example.core.settings.boolean
import com.example.core.settings.int
import com.example.core.settings.string


internal class ConfigSettings(
    private val context: Context,
) {
    private val settings = Settings(context = context, "AppConfig")

    //Example
    var supportURL by settings.string("supportURL", "")
}
