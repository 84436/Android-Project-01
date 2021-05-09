package hcmus.android.gallery1.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import hcmus.android.gallery1.R
import hcmus.android.gallery1.data.getItems
import hcmus.android.gallery1.fragments.base.ImageListFragment
import hcmus.android.gallery1.globalPrefs
import hcmus.android.gallery1.adapters.ItemListAdapter

class TabAllFragment: ImageListFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the root view
        val fragmentView = inflater.inflate(R.layout.fragment_main_all_photos, container, false)

        // Init that RecyclerView
        fragmentView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = ItemListAdapter(
                items = requireContext().contentResolver.getItems(null),
                isCompactLayout = globalPrefs.getViewMode("all") == "list"
            )
            layoutManager = when (globalPrefs.getViewMode("all")) {
                "list"   -> LinearLayoutManager(requireContext())
                "grid_3" -> GridLayoutManager(requireContext(), 3)
                "grid_4" -> GridLayoutManager(requireContext(), 4)
                "grid_5" -> GridLayoutManager(requireContext(), 5)
                else     -> GridLayoutManager(requireContext(), 3)
            }
        }

        return fragmentView
    }
}
