package hcmus.android.gallery1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat

// private const val ARG_PARAM1 = "param1"

class MainAllPhotosFragment : Fragment() {
    // private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_all_photos, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =  MainMoreFragment().apply {
            arguments = Bundle().apply {
                // putString(ARG_PARAM1, param1)
            }
        }
    }


}
