package hcmus.android.gallery1.fragments.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hcmus.android.gallery1.R
import hcmus.android.gallery1.fragments.base.CollectionListFragment

class TabAlbumFragment: CollectionListFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_album, container, false)
    }
}
