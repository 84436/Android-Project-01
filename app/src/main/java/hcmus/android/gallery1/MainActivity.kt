package hcmus.android.gallery1

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide action bar (title bar)
        supportActionBar?.hide()

        // View layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainAllPhotosFragment>(R.id.main_fragment_container)
            }
        }

        // Event handler for bottom nav
        findViewById<BottomNavigationView>(R.id.main_navbar).setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.tab_all -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainAllPhotosFragment>(R.id.main_fragment_container)
                    }
                    true
                }
                R.id.tab_album -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainAlbumFragment>(R.id.main_fragment_container)
                    }
                    true
                }
                R.id.tab_date -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainDateFragment>(R.id.main_fragment_container)
                    }
                    true
                }
                R.id.tab_face -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainFaceFragment>(R.id.main_fragment_container)
                    }
                    true
                }
                R.id.tab_more -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MainMoreFragment>(R.id.main_fragment_container)
                    }
                    true
                }
                else -> false
            }
        }
    }

    // Handle new photo/new video buttons
    fun handleButton(view: View) {
        when (view.id) {
            R.id.btn_new_photo -> {
                val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                try {
                    startActivity(intent)
                }
                catch (e: ActivityNotFoundException) {
                    Log.e("Main.All+PartialBar", "Camera intent failed")
                }
            }
            R.id.btn_new_video -> {
                val intent = Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA)
                try {
                    startActivity(intent)
                }
                catch (e: ActivityNotFoundException) {
                    Log.e("Main.All+PartialBar", "Video intent failed")
                }
            }
            else -> {}
        }
    }
}
