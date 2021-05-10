package hcmus.android.gallery1.fragments.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import hcmus.android.gallery1.R
import hcmus.android.gallery1.data.Item

/* A fragment for viewing image(s) */
class ViewImageFragment(private val item: Item): Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLowProfileUI(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        setLowProfileUI(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_image_nopager, container, false)
        val imageHolder = view.findViewById<ImageView>(R.id.image)
        Glide.with(imageHolder.context)
             .load(item.getUri())
             .error(R.drawable.placeholder_item)
             .into(imageHolder)
        return view
    }

    // Temporarily turn on "lights out" mode for status bar and navigation bar.
    // This usually means hiding nearly everything and leaving with only the clock and battery status.
    // https://stackoverflow.com/a/44433844
    private fun setLowProfileUI(isLowProfile: Boolean) {
        activity?.window?.decorView?.systemUiVisibility = if (isLowProfile) {
            View.SYSTEM_UI_FLAG_LOW_PROFILE
        } else {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}
