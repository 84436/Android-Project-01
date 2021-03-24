package hcmus.android.gallery1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton

class ViewImageActivity : AppCompatActivity() {
    var bottomSheetBehavior: BottomSheetBehavior<BottomNavigationView>? = null
    var bottomSheetExpandButton: ImageButton? = null
    var bottomDrawerDim: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset: splash screen "theme" -> default theme
        setTheme(R.style.Theme_GalleryOne)

        // Hide action bar (title bar)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bdrawer_view_image))
        bottomSheetExpandButton = findViewById(R.id.btn_bdrawer_view_image_expand)
        bottomDrawerDim = findViewById(R.id.bdrawer_view_image_dim)

        // Behavior
        bottomSheetBehavior?.apply { isFitToContents = true }
        bottomSheetBehavior!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomDrawerDim?.visibility = View.GONE
                        bottomSheetExpandButton?.setImageDrawable(ContextCompat.getDrawable(this@ViewImageActivity, R.drawable.ic_bdrawer_up))
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomDrawerDim?.visibility = View.VISIBLE
                        bottomSheetExpandButton?.setImageDrawable(ContextCompat.getDrawable(this@ViewImageActivity, R.drawable.ic_bdrawer_down))
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

    fun closeViewer(view: View) {
        when (view.id) {
            R.id.btn_close_viewer -> finish()
            else -> {}
        }
    }
}
