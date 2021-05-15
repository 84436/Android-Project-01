package hcmus.android.gallery1.data

import android.content.ClipData
import android.content.ContentResolver
import hcmus.android.gallery1.data.getItems

data class Collection (
    val id: Long,
    val name: String,
    val type: String,
    val thumbnailUri: String,
    var itemCount: Int,

    // Lazy-load fields
    var items: List<Item> = emptyList()
)
