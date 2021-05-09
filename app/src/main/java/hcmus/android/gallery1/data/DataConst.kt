package hcmus.android.gallery1.data

import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi

val DEFAULT_CONTENT_URI = MediaStore.Files.getContentUri("external")

val SELECTION_ONLY_IMAGES_OR_VIDEO = "" +
        "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE}" +
        " OR " +
        "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO}"

val DEFAULT_SORT_ORDER_ITEMS = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

@RequiresApi(Build.VERSION_CODES.Q)
val DEFAULT_SORT_ORDER_COLLECTIONS = "${MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME} DESC"
