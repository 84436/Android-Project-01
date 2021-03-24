package hcmus.android.gallery1

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    // Finding bottom sheet + navbar
    var navbar: BottomNavigationView? = null
    var bottomSheetBehavior: BottomSheetBehavior<BottomNavigationView>? = null
    var bottomSheetSettingsHint: TextView? = null
    var bottomSheetExpandButton: ImageButton? = null
    var bottomDrawerDim: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide action bar (title bar)
        supportActionBar?.hide()

        // Set layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fragment: populate containers with fragments (default tab, preference)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainAllPhotosFragment>(R.id.main_fragment_container)
                add<SettingsFragment>(R.id.tab_more_fragment_container)
            }
        }

        // Default selected tab: first tab ("All")
        var lastSelectedTab: Int = R.id.tab_all

        // Find elements
        navbar = findViewById<BottomNavigationView>(R.id.main_navbar)
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bdrawer_main))
        bottomSheetSettingsHint = findViewById<TextView>(R.id.bottom_sheet_settings_hint)
        bottomSheetExpandButton = findViewById<ImageButton>(R.id.btn_bottom_sheet_expand)
        bottomDrawerDim = findViewById(R.id.bdrawer_dim)

        // Bottom sheet behavior
        bottomSheetBehavior!!.apply {
            isFitToContents = false
            halfExpandedRatio = (490/1000f) // magic
        }

        // https://blog.mindorks.com/android-bottomsheet-in-kotlin
        bottomSheetBehavior!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomDrawerDim?.visibility = View.GONE
                        bottomSheetSettingsHint!!.text = resources.getText(R.string.bdrawer_header_settings_not_expanded)
                        bottomSheetExpandButton!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bdrawer_up))
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        bottomDrawerDim?.visibility = View.VISIBLE
                        bottomSheetSettingsHint!!.text = resources.getText(R.string.bdrawer_header_settings_not_expanded)
                        bottomSheetExpandButton!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bdrawer_down))
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomDrawerDim?.visibility = View.VISIBLE
                        bottomSheetSettingsHint!!.text = resources.getText(R.string.bdrawer_header_settings)
                        bottomSheetExpandButton!!.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_bdrawer_close))
                    }
                    else -> { }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomDrawerDim?.visibility = View.VISIBLE
                bottomDrawerDim?.alpha = slideOffset
            }
        })

        bottomDrawerDim?.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // Button expansion behavior
        bottomSheetExpandButton!!.apply {
            setOnClickListener {
                when (bottomSheetBehavior!!.state) {
                    BottomSheetBehavior.STATE_COLLAPSED     -> bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    BottomSheetBehavior.STATE_EXPANDED      -> bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                                                       else -> { }
                }
            }
        }

        // Navbar behavior
        navbar!!.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.tab_all -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainAllPhotosFragment>(R.id.main_fragment_container)
                    }
                    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    true
                }
                R.id.tab_album -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainAlbumFragment>(R.id.main_fragment_container)
                    }
                    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    true
                }
                R.id.tab_date -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainDateFragment>(R.id.main_fragment_container)
                    }
                    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    true
                }
                R.id.tab_face -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainFaceFragment>(R.id.main_fragment_container)
                    }
                    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    true
                }
                else -> false
            }
        }
    }

    // Handle new photo/new video buttons
    fun handleButton(view: View) {
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        when (view.id) {
            R.id.btn_new_photo -> {
                try {
                    startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))
                }
                catch (e: ActivityNotFoundException) {
                    Log.e("handleButton", "Camera intent failed")
                }
            }
            R.id.btn_new_video -> {
                try {
                    startActivity(Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA))
                }
                catch (e: ActivityNotFoundException) {
                    Log.e("handleButton", "Video intent failed")
                }
            }
            R.id.btn_secret_album -> {
                startActivity(Intent(this, SecretActivity::class.java))
            }
            else -> {}
        }
    }
}
