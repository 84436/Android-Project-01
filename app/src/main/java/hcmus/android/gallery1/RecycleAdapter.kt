package hcmus.android.gallery1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hcmus.android.gallery1.data.Photo

class RecycleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Photo> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_image,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BlogViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<Photo>) {
        items = blogList
    }

    class BlogViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val blogImage  : ImageView = itemView.findViewById(R.id.blog_image)

        fun bind(blogPost : Photo)
        {

            val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
            Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions).load(blogPost.image).into(blogImage)
        }
    }
}