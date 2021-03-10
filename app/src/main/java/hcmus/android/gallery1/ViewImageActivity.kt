package hcmus.android.gallery1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ViewImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset: splash screen "theme" -> default theme
        setTheme(R.style.Theme_GalleryOne)

        // Hide action bar (title bar)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        // Bottom sheet half expanded state: https://stackoverflow.com/a/59305687
        val bottomSheet = findViewById<FrameLayout>(R.id.bottom_sheet_image_details)
        BottomSheetBehavior.from(bottomSheet).apply {
            isFitToContents = false
            halfExpandedRatio = 0.6f
        }
    }

    fun closeViewer(view: View) {
        when (view.id) {
            R.id.btn_close_imageview -> finish()
            else -> {}
        }
    }
}
