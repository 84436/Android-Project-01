package hcmus.android.gallery1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset: splash screen "theme" -> default theme
        setTheme(R.style.Theme_GalleryOne)

        // Hide action bar (title bar)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Event handler for bottom nav
        // TODO fix me
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.tab_all -> {
                    Toast.makeText(this, resources.getText(R.string.tab_all), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tab_album -> {
                    Toast.makeText(this, resources.getText(R.string.tab_album), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tab_date -> {
                    Toast.makeText(this, resources.getText(R.string.tab_date), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tab_face -> {
                    Toast.makeText(this, resources.getText(R.string.tab_face), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.tab_settings -> {
                    Toast.makeText(this, resources.getText(R.string.tab_settings), Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}
