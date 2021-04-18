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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// private const val ARG_PARAM1 = "param1"

class MainAllPhotosFragment : Fragment() {
    private lateinit var blogAdapter: RecycleAdapter
    // private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addDataSet();
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
        fun newInstance() =  MainAllPhotosFragment().apply {
            arguments = Bundle().apply {
                // putString(ARG_PARAM1, param1)
            }
        }
    }
    private fun addDataSet(){
        val data = DataSource.createDataSet()
        blogAdapter.submitList(data)
    }

    private fun initRecycleView(){
        // Initialize the adapter
        blogAdapter = RecycleAdapter()

        // Find the RecyclerView on main activity, then init (2)
        val myRecyclerView = layoutInflater.inflate(R.layout.fragment_main_all_photos,null)?:return
        myRecyclerView.findViewById<RecyclerView>(R.id.recycler_view)
        myRecyclerView.apply {
            // layoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = GridLayoutManager(this, 4)
            adapter = blogAdapter
        }
    }


}
