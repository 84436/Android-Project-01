package hcmus.android.gallery1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset: splash screen "theme" -> default theme
        setTheme(R.style.Theme_GalleryOne)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
