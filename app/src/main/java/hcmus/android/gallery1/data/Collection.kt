package hcmus.android.gallery1.data

import android.content.ContentResolver
import hcmus.android.gallery1.data.getItems

data class Collection (
    val id: Long,
    var name: String,
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
