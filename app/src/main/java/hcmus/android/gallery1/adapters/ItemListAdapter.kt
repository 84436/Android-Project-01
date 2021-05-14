package hcmus.android.gallery1.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hcmus.android.gallery1.R
import hcmus.android.gallery1.ViewImageActivity
import hcmus.android.gallery1.data.Item
//import hcmus.android.gallery1.fragments.image.ViewImageFragment
import hcmus.android.gallery1.globalFragmentManager

class ItemListAdapter(private val items: List<Item>, private val isCompactLayout: Boolean = false)
    : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val targetItemLayout = if (isCompactLayout) { R.layout.list_item_compact } else { R.layout.list_item }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(targetItemLayout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val image = holder.itemView.findViewById<ImageView>(R.id.item_thumbnail)
        Log.e("", item.getUri())
        Glide.with(image.context)
             .load(item.getUri())
             .error(R.drawable.placeholder_item)
             .transition(DrawableTransitionOptions.withCrossFade(250)) // ms
             .into(image)

        if (isCompactLayout) {
            val name = holder.itemView.findViewById<TextView>(R.id.item_name)
            name.text = item.fileName
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ViewImageActivity::class.java).apply {
                putExtra("id", item.id)
                putExtra("filename", item.fileName)
                putExtra("uri", item.getUri())
                putExtra("size", items.size)
            }
            it.context.startActivity(intent)
        }
    }
}
