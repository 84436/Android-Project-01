package hcmus.android.gallery1.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hcmus.android.gallery1.R
import hcmus.android.gallery1.adapters.CollectionListAdapter
import hcmus.android.gallery1.adapters.ItemListAdapter
import hcmus.android.gallery1.data.getCollections
import hcmus.android.gallery1.data.getItems
import hcmus.android.gallery1.fragments.base.CollectionListFragment
import hcmus.android.gallery1.globalPrefs

class TabAlbumFragment: CollectionListFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the root view
        val fragmentView = inflater.inflate(R.layout.fragment_main_album, container, false)

        // Init that RecyclerView
        fragmentView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = CollectionListAdapter(
                items = requireContext().contentResolver.getCollections(),
                isCompactLayout = globalPrefs.getViewMode("album") == "list"
            )
            layoutManager = when (globalPrefs.getViewMode("album")) {
                "list"   -> LinearLayoutManager(requireContext())
                "grid_2" -> GridLayoutManager(requireContext(), 2)
                else     -> GridLayoutManager(requireContext(), 2)
            }
        }

        return fragmentView
    }
}
