package hcmus.android.gallery1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import hcmus.android.gallery1.helpers.PreferenceFacility

class SecretActivity : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<BottomNavigationView>
    private lateinit var bottomSheetExpandButton: MaterialButton
    private lateinit var bottomDrawerDim: View
    private lateinit var viewModeSelector: MaterialButtonToggleGroup
    private lateinit var prefs: PreferenceFacility

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide action bar (title bar)
        supportActionBar?.hide()

        prefs = PreferenceFacility(getPreferences(MODE_PRIVATE))

        // Set layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)
        initBottomSheet()
        initViewModeSelector()

        // Fragment: populate containers with fragments (default tab, preference)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SecretFragment>(R.id.secret_fragment_container)
            }
        }
    }

    private fun initBottomSheet() {
        // Find elements
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bdrawer_secure))
        bottomSheetExpandButton = findViewById(R.id.btn_bottom_sheet_expand)
        bottomDrawerDim = findViewById(R.id.bdrawer_secret_dim)

        // Bottom sheet behavior: skip HALF_COLLAPSED
        bottomSheetBehavior.apply { isFitToContents = true }
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomDrawerDim.visibility = View.GONE
                        bottomSheetExpandButton.icon = ContextCompat.getDrawable(this@SecretActivity, R.drawable.ic_bdrawer_up)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomDrawerDim.visibility = View.VISIBLE
                        bottomSheetExpandButton.icon = ContextCompat.getDrawable(this@SecretActivity, R.drawable.ic_bdrawer_down)
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
    }

    private fun initViewModeSelector() {
        viewModeSelector = findViewById(R.id.viewmode_secret)

        viewModeSelector.check(
            when(prefs.getViewMode("secret")) {
                "list" -> R.id.btn_viewmode_secret_list
                "grid_3" -> R.id.btn_viewmode_secret_grid_3
                "grid_4" -> R.id.btn_viewmode_secret_grid_4
                "grid_5" -> R.id.btn_viewmode_secret_grid_5
                else -> R.id.btn_viewmode_secret_grid_3
            }
        )

        viewModeSelector.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.btn_viewmode_secret_list -> {
                    prefs.setViewMode("secret", "list")
                }
                R.id.btn_viewmode_secret_grid_3 -> {
                    prefs.setViewMode("secret", "grid_3")
                }
                R.id.btn_viewmode_secret_grid_4 -> {
                    prefs.setViewMode("secret", "grid_4")
                }
                R.id.btn_viewmode_secret_grid_5 -> {
                    prefs.setViewMode("secret", "grid_5")
                }
            }
        }
    }

    fun closeSecret(view: View) {
        when (view.id) {
            R.id.btn_close_secret -> finish()
            else -> {}
        }
    }
}
