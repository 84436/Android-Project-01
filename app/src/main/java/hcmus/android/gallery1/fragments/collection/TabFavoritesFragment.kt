package hcmus.android.gallery1.fragments.collection

import hcmus.android.gallery1.adapters.ItemListAdapter
import hcmus.android.gallery1.data.getFavorites
import hcmus.android.gallery1.fragments.base.ImageListFragment
import hcmus.android.gallery1.globalContext
import hcmus.android.gallery1.globalPrefs

class TabFavoritesFragment : ImageListFragment(
    itemListAdapter = ItemListAdapter(
        items = globalContext.contentResolver.getFavorites(),
        isCompactLayout = globalPrefs.getViewMode("fav") == "list"
    ),
    tabName = "fav"
)
