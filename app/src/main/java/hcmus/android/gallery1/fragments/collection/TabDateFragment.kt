package hcmus.android.gallery1.fragments.collection

import hcmus.android.gallery1.adapters.CollectionListAdapter
import hcmus.android.gallery1.data.getCollectionsByDate
import hcmus.android.gallery1.fragments.base.CollectionListFragment
import hcmus.android.gallery1.globalContext
import hcmus.android.gallery1.globalPrefs

class TabDateFragment: CollectionListFragment(
    collectionListAdapter = CollectionListAdapter(
        items = globalContext.contentResolver.getCollectionsByDate(),
        isCompactLayout = globalPrefs.getViewMode("date") == "list"
    ),
    tabName = "date"
)
