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

class SecretActivity : AppCompatActivity() {
    var bottomSheetBehavior: BottomSheetBehavior<BottomNavigationView>? = null
    var bottomSheetExpandButton: MaterialButton? = null
    var bottomDrawerDim: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide action bar (title bar)
        supportActionBar?.hide()

        // Set layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        // Fragment: populate containers with fragments (default tab, preference)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SecretFragment>(R.id.secret_fragment_container)
            }
        }

        // Find elements
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bdrawer_secure))
        bottomSheetExpandButton = findViewById(R.id.btn_bottom_sheet_expand)
        bottomDrawerDim = findViewById(R.id.bdrawer_secret_dim)

        // Bottom sheet behavior: skip HALF_COLLAPSED
        bottomSheetBehavior!!.apply { isFitToContents = true }
        bottomSheetBehavior!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomDrawerDim?.visibility = View.GONE
                        bottomSheetExpandButton?.icon = ContextCompat.getDrawable(this@SecretActivity, R.drawable.ic_bdrawer_up)
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomDrawerDim?.visibility = View.VISIBLE
                        bottomSheetExpandButton?.icon = ContextCompat.getDrawable(this@SecretActivity, R.drawable.ic_bdrawer_down)
                    }
                    else -> { }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomDrawerDim?.visibility = View.VISIBLE
                bottomDrawerDim?.alpha = slideOffset / 2f
            }
        })

        bottomDrawerDim?.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        // Button expansion behavior
        bottomSheetExpandButton!!.apply {
            setOnClickListener {
                when (bottomSheetBehavior!!.state) {
                    BottomSheetBehavior.STATE_COLLAPSED     -> bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                    BottomSheetBehavior.STATE_EXPANDED      -> bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    else -> { }
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
