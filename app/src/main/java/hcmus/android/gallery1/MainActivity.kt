package hcmus.android.gallery1

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hcmus.android.gallery1.helpers.PreferenceFacility
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: PreferenceFacility

    private lateinit var navbar: BottomNavigationView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<BottomNavigationView>
    private lateinit var bottomSheetSettingsHint: TextView
    private lateinit var bottomSheetExpandButton: ImageButton
    private lateinit var bottomDrawerDim: View

    private lateinit var viewModeSelectorAll: MaterialButtonToggleGroup
    private lateinit var viewModeSelectorAlbum: MaterialButtonToggleGroup
    private lateinit var viewModeSelectorDate: MaterialButtonToggleGroup
    private lateinit var viewModeSelectorFace: MaterialButtonToggleGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide action bar (title bar)
        supportActionBar?.hide()

        // Init preference facility
        prefs = PreferenceFacility(getPreferences(MODE_PRIVATE))

        // Set layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomDrawer()
        initViewModeSelectors()

        // Bind onLongClick event to the "About" button in bottom drawer
        findViewById<Button>(R.id.btn_more_about).setOnLongClickListener {
            handleBtnAboutSecret()
            true
        }

        // Fragment: populate containers with fragments (default tab, preference)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainAllPhotosFragment>(R.id.main_fragment_container)
            }
        }
    }

    // Back button on bottom sheet
    override fun onBackPressed() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomDrawerDim.visibility = View.GONE
            bottomSheetExpandButton.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bdrawer_up))
        }
        else {
            super.onBackPressed()
        }
    }

    // Navbar + Bottom sheet
    private fun initBottomDrawer() {
        // Find elements
        navbar = findViewById(R.id.main_navbar)
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bdrawer_main))
        bottomSheetSettingsHint = findViewById(R.id.bottom_sheet_settings_hint)
        bottomSheetExpandButton = findViewById(R.id.btn_bottom_sheet_expand)
        bottomDrawerDim = findViewById(R.id.bdrawer_dim)

        // Bottom sheet behavior
        bottomSheetBehavior.apply {
            isFitToContents = true
            // halfExpandedRatio = (490/1000f) // magic
        }

        // https://blog.mindorks.com/android-bottomsheet-in-kotlin
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomDrawerDim.visibility = View.GONE
                        bottomSheetExpandButton.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bdrawer_up))
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomDrawerDim.visibility = View.VISIBLE
                        bottomSheetExpandButton.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bdrawer_down))
                    }
                    else -> { }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomDrawerDim.visibility = View.VISIBLE
                bottomDrawerDim.alpha = 0.5f * slideOffset
            }
        })

        bottomDrawerDim.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // Button expansion behavior
        bottomSheetExpandButton.apply {
            setOnClickListener {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED     -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    BottomSheetBehavior.STATE_EXPANDED      -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    else -> { }
                }
            }
        }

        // Navbar behavior
        navbar.setOnNavigationItemSelectedListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            supportFragmentManager.commit {
                when (it.itemId) {
                    R.id.tab_all -> replace<MainAllPhotosFragment>(R.id.main_fragment_container)
                    R.id.tab_album -> replace<MainAlbumFragment>(R.id.main_fragment_container)
                    R.id.tab_date -> replace<MainDateFragment>(R.id.main_fragment_container)
                    R.id.tab_face -> replace<MainFaceFragment>(R.id.main_fragment_container)
                }
            }
            setVisibleViewModeSelector(it.itemId)
            true
        }
    }

    // View mode selector
    private fun initViewModeSelectors() {
        viewModeSelectorAll = findViewById(R.id.viewmode_all)
        viewModeSelectorAlbum = findViewById(R.id.viewmode_album)
        viewModeSelectorDate = findViewById(R.id.viewmode_date)
        viewModeSelectorFace = findViewById(R.id.viewmode_face)

        // View mode: all
        setVisibleViewModeSelector(R.id.tab_all)

        // Set initial state from prefs

        viewModeSelectorAll.check(
            when(prefs.getViewMode("all")) {
                "list" -> R.id.btn_viewmode_all_list
                "grid_3" -> R.id.btn_viewmode_all_grid_3
                "grid_4" -> R.id.btn_viewmode_all_grid_4
                "grid_5" -> R.id.btn_viewmode_all_grid_5
                else -> R.id.btn_viewmode_all_grid_3
            }
        )

        viewModeSelectorAlbum.check(
            when(prefs.getViewMode("album")) {
                "list" -> R.id.btn_viewmode_album_list
                "grid_2" -> R.id.btn_viewmode_album_grid_2
                else -> R.id.btn_viewmode_album_grid_2
            }
        )

        viewModeSelectorDate.check(
            when(prefs.getViewMode("date")) {
                "list" -> R.id.btn_viewmode_date_list
                "grid_2" -> R.id.btn_viewmode_date_grid_2
                else -> R.id.btn_viewmode_date_grid_2
            }
        )

        viewModeSelectorFace.check(
            when(prefs.getViewMode("face")) {
                "list" -> R.id.btn_viewmode_face_list
                "grid_2" -> R.id.btn_viewmode_face_grid_2
                else -> R.id.btn_viewmode_face_grid_2
            }
        )

        // Add listeners

        viewModeSelectorAll.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.btn_viewmode_all_list -> {
                    prefs.setViewMode("all", "list")
                }
                R.id.btn_viewmode_all_grid_3 -> {
                    prefs.setViewMode("all", "grid_3")
                }
                R.id.btn_viewmode_all_grid_4 -> {
                    prefs.setViewMode("all", "grid_4")
                }
                R.id.btn_viewmode_all_grid_5 -> {
                    prefs.setViewMode("all", "grid_5")
                }
            }
        }
        viewModeSelectorAlbum.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.btn_viewmode_album_list -> {
                    prefs.setViewMode("album", "list")
                }
                R.id.btn_viewmode_album_grid_2 -> {
                    prefs.setViewMode("album", "grid_2")
                }
            }
        }

        viewModeSelectorDate.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.btn_viewmode_date_list -> {
                    prefs.setViewMode("date", "list")
                }
                R.id.btn_viewmode_date_grid_2 -> {
                    prefs.setViewMode("date", "grid_2")
                }
            }
        }

        viewModeSelectorFace.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.btn_viewmode_face_list -> {
                    prefs.setViewMode("face", "list")
                }
                R.id.btn_viewmode_face_grid_2 -> {
                    prefs.setViewMode("face", "grid_2")
                }
            }
        }
    }

    // View mode selector
    private fun setVisibleViewModeSelector(itemId: Int) {
        viewModeSelectorAll.visibility = View.GONE
        viewModeSelectorAlbum.visibility = View.GONE
        viewModeSelectorDate.visibility = View.GONE
        viewModeSelectorFace.visibility = View.GONE
        when(itemId) {
            R.id.tab_all -> viewModeSelectorAll.visibility = View.VISIBLE
            R.id.tab_album -> viewModeSelectorAlbum.visibility = View.VISIBLE
            R.id.tab_date -> viewModeSelectorDate.visibility = View.VISIBLE
            R.id.tab_face -> viewModeSelectorFace.visibility = View.VISIBLE
            else -> {}
        }
    }

    fun handleBtnNewAlbum(view: View) {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show()
    }

    fun handleBtnNewPhoto(view: View) {
        try {
            startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Failed to open camera (do you have camera app installed?)", Toast.LENGTH_LONG).show()
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun handleBtnNewVideo(view: View) {
        try {
            startActivity(Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA))
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Failed to open camera (do you have camera app installed?)", Toast.LENGTH_LONG).show()
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun handleBtnSetTheme(view: View) {
        MaterialAlertDialogBuilder(this)
                .setTitle(R.string.bdrawer_more_theme)
                .setSingleChoiceItems(resources.getStringArray(R.array.settings_theme), prefs.validThemes.indexOf(prefs.theme)) { _, which ->
                    when (prefs.validThemes[which]) {
                        "follow_system" -> {
                            Toast.makeText(this, "You chose to follow the system", Toast.LENGTH_SHORT).show()
                        }
                        "day" -> {
                            Toast.makeText(this, "You chose day mode", Toast.LENGTH_SHORT).show()
                        }
                        "night" -> {
                            Toast.makeText(this, "You chose night mode", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "This should not happen", Toast.LENGTH_SHORT).show()
                        }
                    }
                    prefs.theme = prefs.validThemes[which]
                }
                .show()
    }

    fun handleBtnSetLanguage(view: View) {
        MaterialAlertDialogBuilder(this)
                .setTitle(R.string.bdrawer_more_language)
                .setSingleChoiceItems(resources.getStringArray(R.array.settings_language), prefs.validLanguages.indexOf(prefs.language)) { _, which ->
                    when (prefs.validLanguages[which]) {
                        "en" -> {
                            Toast.makeText(this, "You chose English", Toast.LENGTH_SHORT).show()
                        }
                        "vi" -> {
                            Toast.makeText(this, "Bạn đã chọn Tiếng Việt", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "This should not happen", Toast.LENGTH_SHORT).show()
                        }
                    }
                    prefs.language = prefs.validLanguages[which]
                }
                .show()
    }

    fun handleBtnAbout(view: View) {
        MaterialAlertDialogBuilder(this)
            .setView(LayoutInflater.from(this).inflate(R.layout.about_dialog, null, false))
            .show()
    }

    private fun handleBtnAboutSecret() {
        startActivity(Intent(this, SecretActivity::class.java))
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}
