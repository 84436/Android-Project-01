package hcmus.android.gallery1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.assets.filter.basic.FilterPackBasic
import ly.img.android.pesdk.assets.font.basic.FontPackBasic
import ly.img.android.pesdk.assets.frame.basic.FramePackBasic
import ly.img.android.pesdk.assets.overlay.basic.OverlayPackBasic
import ly.img.android.pesdk.assets.sticker.emoticons.StickerPackEmoticons
import ly.img.android.pesdk.assets.sticker.shapes.StickerPackShapes
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.PhotoEditorSaveSettings
import ly.img.android.pesdk.ui.activity.PhotoEditorBuilder
import ly.img.android.pesdk.ui.model.state.*
import ly.img.android.pesdk.ui.utils.PermissionRequest
import ly.img.android.serializer._3.IMGLYFileWriter
import java.io.File
import java.io.IOException

// PhotoEditor Activity; adapted from sample code
class EditImageActivity : AppCompatActivity(), PermissionRequest.Response {

    private lateinit var uri: Uri

    companion object {
        const val PESDK_RESULT = 1
        const val GALLERY_RESULT = 2
    }

    // Important permission request for Android 6.0 and above, don't forget to add this!
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun permissionGranted() {}

    override fun permissionDenied() {
        /* TODO: The Permission was rejected by the user. The Editor was not opened,
         * Show a hint to the user and try again. */
    }

    // Create a empty new SettingsList and apply the changes on this referance.
    // If you include our asset Packs and use our UI you also need to add them to the UI,
    // otherwise they are only available for the backend (like Serialisation)
    // See the specific feature sections of our guides if you want to know how to add our own Assets.
    private fun createPESDKSettingsList() =
        PhotoEditorSettingsList()
            .configure<UiConfigFilter> {
                it.setFilterList(FilterPackBasic.getFilterPack())
            }
            .configure<UiConfigText> {
                it.setFontList(FontPackBasic.getFontPack())
            }
            .configure<UiConfigFrame> {
                it.setFrameList(FramePackBasic.getFramePack())
            }
            .configure<UiConfigOverlay> {
                it.setOverlayList(OverlayPackBasic.getOverlayPack())
            }
            .configure<UiConfigSticker> {
                it.setStickerLists(
                    StickerPackEmoticons.getStickerCategory(),
                    StickerPackShapes.getStickerCategory()
                )
            }
            .configure<PhotoEditorSaveSettings> {
                it.setOutputToGallery(Environment.DIRECTORY_DCIM)
            }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uri = Uri.parse(intent.getStringExtra("uri"))
        openEditor(uri)
    }

    private fun openEditor(inputImage: Uri?) {
        val settingsList = createPESDKSettingsList()

        settingsList.configure<LoadSettings> {
            it.source = inputImage
        }

        settingsList[LoadSettings::class].source = inputImage

        PhotoEditorBuilder(this)
            .setSettingsList(settingsList)
            .startActivityForResult(this, PESDK_RESULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (resultCode == RESULT_OK && requestCode == GALLERY_RESULT) {
            // Open Editor with some uri in this case with an image selected from the system gallery.
            openEditor(intent?.data)

        } else if (resultCode == RESULT_OK && requestCode == PESDK_RESULT) {
            // Editor has saved an Image.
            val data = intent?.let { EditorSDKResult(it) }

            Log.i("PESDK", "Source image is located here ${data?.sourceUri}")
            Log.i("PESDK", "Result image is located here ${data?.resultUri}")

            // TODO: Do something with the result image

            // OPTIONAL: read the latest state to save it as a serialisation
            val lastState = data?.settingsList
            try {
                if (lastState != null) {
                    IMGLYFileWriter(lastState).writeJson(
                        File(
                            Environment.getExternalStorageDirectory(),
                            "serialisationReadyToReadWithPESDKFileReader.json"
                        )
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Unconditional exit
            finish()

        } else if (resultCode == RESULT_CANCELED && requestCode == PESDK_RESULT) {
            // Editor was canceled
            val data = intent?.let { EditorSDKResult(it) }

            val sourceURI = data?.sourceUri
            // TODO: Do something with the source...

            // Unconditional exit
            finish()
        }
    }

}
