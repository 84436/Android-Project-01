package hcmus.android.gallery1.fragments.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import hcmus.android.gallery1.R

/* A fragment for viewing image(s) */
class ViewImageFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_view_image, container, false)
        view.findViewById<ViewPager2>(R.id.view_pager)
        return view
    }
}
