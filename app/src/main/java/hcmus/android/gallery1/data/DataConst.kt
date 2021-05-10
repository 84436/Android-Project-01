package hcmus.android.gallery1.data

import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi

// Default content URI prefix for fetching files
val DEFAULT_CONTENT_URI = MediaStore.Files.getContentUri("external")


// Default WHERE clause for filtering only image/video items
val SELECTION_ONLY_IMAGES_OR_VIDEO = "" +
        "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE}" +
        " OR " +
        "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO}"


// Default ORDER BY clause for items in a collection:
// latest file first
val DEFAULT_SORT_ORDER_ITEMS = "" +
        "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"


// Default ORDER BY clause for collections:
// A-Z on Collection Name; latest file first on Collection First URI
@RequiresApi(Build.VERSION_CODES.Q)
val DEFAULT_SORT_ORDER_COLLECTIONS = "" +
        "${MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME} ASC," +
        "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
