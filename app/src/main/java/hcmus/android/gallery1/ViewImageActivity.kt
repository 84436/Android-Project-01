package hcmus.android.gallery1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset: splash screen "theme" -> default theme
        setTheme(R.style.Theme_GalleryOne)

        // Hide action bar (title bar)
        supportActionBar?.hide()

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

        item = Item(
            id = itemId,
            fileName = itemFileName!!,
            uri = itemUri!!
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
        image_fileSize.text=item.fileSize.toString()

        val image_filePath=findViewById<TextView>(R.id.info_file_path)
        image_filePath.text=item.filePath

    }

    fun closeViewer(view: View) {
        when (view.id) {
            R.id.btn_close_viewer -> finish()
            else -> {}
        }
    }

}
