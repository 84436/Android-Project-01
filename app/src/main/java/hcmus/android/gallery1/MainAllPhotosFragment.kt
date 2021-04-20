package hcmus.android.gallery1

import android.content.ActivityNotFoundException
import android.content.Context
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
import hcmus.android.gallery1.helpers.PreferenceFacility

class MainAllPhotosFragment : Fragment() {
    private lateinit var blogAdapter: RecycleAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    // PASS THIS THING; DO NOT RECREATE PER CLASS
    private lateinit var prefs : PreferenceFacility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceFacility(requireContext().getSharedPreferences("MainActivity", Context.MODE_PRIVATE))
        blogAdapter = RecycleAdapter()
        addDataSet()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the root view
        val fragmentView = inflater.inflate(R.layout.fragment_main_all_photos, container, false)

        // Create layout manager (using a dirty refresh)

        // Init that RecyclerView
        fragmentView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = blogAdapter
        }
        return fragmentView
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        blogAdapter.submitList(data)
    }
}
