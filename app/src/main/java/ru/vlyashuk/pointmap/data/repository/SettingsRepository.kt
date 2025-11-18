package ru.vlyashuk.pointmap.data.repository

import android.content.Context
import androidx.core.content.edit
import ru.vlyashuk.pointmap.ui.theme.AppThemeMode

class SettingsRepository(context: Context) {

    private val prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    fun saveTheme(mode: AppThemeMode) {
        prefs.edit { putString("theme_mode", mode.name) }
    }

    fun loadTheme(): AppThemeMode {
        val value = prefs.getString("theme_mode", AppThemeMode.SYSTEM.name)
        return AppThemeMode.valueOf(value!!)
    }
}