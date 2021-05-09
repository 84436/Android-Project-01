package hcmus.android.gallery1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hcmus.android.gallery1.R
import hcmus.android.gallery1.data.Collection
import hcmus.android.gallery1.data.Item

class CollectionListAdapter(private val items: List<Collection>, private val isCompactLayout: Boolean = false)
    : RecyclerView.Adapter<CollectionListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val targetItemLayout = if (isCompactLayout) { R.layout.list_collection_compact } else { R.layout.list_collection }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(targetItemLayout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val image = holder.itemView.findViewById<ImageView>(R.id.collection_thumbnail)

        val name = holder.itemView.findViewById<TextView>(R.id.collection_name)
        name.text = item.name

        if (isCompactLayout) {
            val count = holder.itemView.findViewById<TextView>(R.id.collection_count)
            count.text = "${item.items.size}"
        }

        image.setOnClickListener {
            Toast.makeText(image.context, "Image ID = ${item.id}", Toast.LENGTH_SHORT).show()
        }
    }
}