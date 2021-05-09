package hcmus.android.gallery1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import hcmus.android.gallery1.fragments.MainFragment
import hcmus.android.gallery1.helpers.PreferenceFacility
import java.util.*

lateinit var globalFragmentManager: FragmentManager
lateinit var globalPrefs: PreferenceFacility

const val PERMISSION_REQUEST_CODE = 0x1337

class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Late init but quite early
        globalFragmentManager = supportFragmentManager
        //globalPrefs = PreferenceFacility(getPreferences(MODE_PRIVATE))
        globalPrefs = PreferenceFacility(
            PreferenceManager.getDefaultSharedPreferences(this)
        )

        // Theme and language
        setTheme(globalPrefs.themeR)
        setLanguageOnActivityRestart()

        // Layout
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        // TODO Bottom drawer

        if (checkSelfPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(
                arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }

        // Insert first piece of fragment
        globalFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container, MainFragment())
        }
    }

    // https://stackoverflow.com/a/2900144
    fun setLanguageOnActivityRestart() {
        val newConfig = resources.configuration
        newConfig.setLocale(Locale.ENGLISH)
        resources.updateConfiguration(newConfig, resources.displayMetrics)
    }

    fun changeLanguage(lang: String) {
        if (lang in globalPrefs.validLanguages && globalPrefs.language != lang) {
            globalPrefs.language = lang
            restartSelf()
        }
    }

    fun changeTheme(theme: String) {
        if (theme in globalPrefs.validThemes && globalPrefs.theme != theme) {
            globalPrefs.theme = theme
            restartSelf()
        }
    }

    private fun restartSelf() {
        finish()
        startActivity(Intent(this, this::class.java))
    }
}
