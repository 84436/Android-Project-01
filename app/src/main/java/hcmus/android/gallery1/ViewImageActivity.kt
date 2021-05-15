package hcmus.android.gallery1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView

import android.widget.TextView

import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import hcmus.android.gallery1.data.Item
import org.w3c.dom.Text

class ViewImageActivity : AppCompatActivity() {
    var bottomSheetBehavior: BottomSheetBehavior<BottomNavigationView>? = null
    var bottomSheetExpandButton: ImageButton? = null
    var bottomDrawerDim: View? = null
    private lateinit var item: Item
    private val CREATE_FILE: Int = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset: splash screen "theme" -> default theme
        setTheme(R.style.Theme_GalleryOne)

        // Hide action bar (title bar)
        supportActionBar?.hide()

        // Hide status bar icons
        setLowProfileUI(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_view_image_nopager)

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

        // Populate
        populateImageAndInfo()

    }


    private fun populateImageAndInfo() {

        val imageHolder = findViewById<ImageView>(R.id.image)



        val itemId = intent.getLongExtra("id", 0)
        val itemFileName = intent.getStringExtra("filename")
        val itemUri = intent.getStringExtra("uri")
        val itemSize=intent.getLongExtra("size",0)
        val itemTime=intent.getLongExtra("time",0)
        val itemResolution=intent.getIntExtra("resolution",0)
        val itemPath=intent.getStringExtra("path")


        item = Item(
            id = itemId,
            fileName = itemFileName!!,
            uri = itemUri!!,
            fileSize=itemSize,
            filePath = itemPath.toString(),
            dateModified = itemTime,
            width = itemResolution

        )

        Glide.with(imageHolder.context)
            .load(item.getUri())
            .error(R.drawable.placeholder_item)
            .into(imageHolder)



        val image_name=findViewById<TextView>(R.id.info_file_name)
        image_name.text=item.fileName


        val image_time=findViewById<TextView>(R.id.info_timestamp)
        image_time.text=item.dateModified.toString()




        val image_fileSize=findViewById<TextView>(R.id.info_file_size)
        image_fileSize.text=item.fileSize.toString() +"Byte"

        val image_filePath=findViewById<TextView>(R.id.info_file_path)
        image_filePath.text=item.filePath




    }

    fun closeViewer(view: View) {
        if (view.id == R.id.btn_close_viewer) {
            setLowProfileUI(false)
            finish()
        }
    }

    fun openEditor(view: View) {
        if (view.id == R.id.btn_edit) {
            setLowProfileUI(false)
            val intent = Intent(this, EditImageActivity::class.java)
            intent.putExtra("uri", item.getUri())
            startActivity(intent)
        }
    }

    fun shareImage(view: View) {
        if (view.id == R.id.btn_share) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, Uri.parse(item.getUri()))
                type = "image/*"
            }
            startActivity(Intent.createChooser(intent, "Send to"))
        }
    }

    fun setAs(view: View) {
        if (view.id == R.id.btn_wallpaper) {
            val intent = Intent().apply {
                action = Intent.ACTION_ATTACH_DATA
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(Uri.parse(item.getUri()), "image/*")
                putExtra("mimeType", "image/*")
            }
            startActivity(Intent.createChooser(intent, "Set as"))
        }
    }

    fun deleteImage(view: View) {
        if (view.id == R.id.btn_delete) {
            contentResolver.delete(Uri.parse(item.getUri()), null, null)
            Toast.makeText(this, "Image deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun copyAsFile(view: View) {
        if (view.id == R.id.btn_copy) {
            /* val intent = Intent().apply {
                action = Intent.ACTION_CREATE_DOCUMENT
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image"
                putExtra(Intent.EXTRA_TITLE, item.fileName)
            }
            startActivityForResult(intent, CREATE_FILE) */
        }
    }

    fun moveAsFile(view: View) {
        if (view.id == R.id.btn_move) {

        }
    }

    // Temporarily turn on "lights out" mode for status bar and navigation bar.
    // This usually means hiding nearly everything and leaving with only the clock and battery status.
    // https://stackoverflow.com/a/44433844
    private fun setLowProfileUI(isLowProfile: Boolean) {
        this.window?.decorView?.systemUiVisibility = if (isLowProfile) {
            View.SYSTEM_UI_FLAG_LOW_PROFILE
        } else {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }


}
