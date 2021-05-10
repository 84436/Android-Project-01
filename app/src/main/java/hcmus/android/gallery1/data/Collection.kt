package hcmus.android.gallery1.data

import android.content.ContentResolver
import hcmus.android.gallery1.data.getItems

data class Collection (
    val id: Long,
    val name: String,
    val thumbnailUri: String,
    var itemCount: Int,
    var isPopulated: Boolean = false,

    // Lazy-load fields
    var items: List<Item> = emptyList()
)

{
    fun populate() {
        if (!isPopulated) {
            isPopulated = true
        }
    }
}
