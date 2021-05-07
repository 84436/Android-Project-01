package hcmus.android.gallery1.helpers

import android.content.SharedPreferences
import androidx.core.content.edit
import hcmus.android.gallery1.R

class PreferenceFacility(private val prefs: SharedPreferences) {
    // Sanity check
    val validTabs           = arrayOf("all", "album", "date", "face", "secret")
    val validViews          = arrayOf("list", "grid_3", "grid_4", "grid_5")
    val validViewsLimited   = arrayOf("list", "grid_2")
    val validThemes         = arrayOf("follow_system", "day", "night")
    val validLanguages      = arrayOf("en", "vi")

    fun isValidViewMode(tab: String, mode: String): Boolean {
        if (tab !in validTabs) return false
        return when (tab) {
            "all", "secret" -> { mode in validViews }
            else -> { mode in validViewsLimited }
        }
    }

    // First-run flag
    var firstRun: Boolean
        get() { return prefs.getBoolean("is_first_run", true) }
        set(value) { prefs.edit(commit = true) { putBoolean("is_first_run", value) } }

    // View mode per tab
    fun getViewMode(tab: String): String {
        if (tab in validTabs) {
            return prefs.getString("view_mode_$tab", null) ?: ""
        }
        return ""
    }
    fun setViewMode(tab: String, newMode: String) {
        if (isValidViewMode(tab, newMode)) {
            prefs.edit(commit = true) { putString("view_mode_$tab", newMode) }
        }
    }

    // Theme
    var theme: String
        get() { return prefs.getString("theme", "follow_system") as String }
        set(value) { prefs.edit(commit = true) { putString("theme", value) } }

    // Language
    var language: String
        get() { return prefs.getString("language", "en") as String }
        set(value) { prefs.edit(commit = true) { putString("language", value) } }

    // Theme (fetch actual resource ID)
    val themeR: Int
        get() {
            return when (theme) {
                "day" -> R.style.Theme_GalleryOne
                "night" -> R.style.Theme_GalleryOne
                else -> R.style.Theme_GalleryOne // fallback
            }
        }
}
