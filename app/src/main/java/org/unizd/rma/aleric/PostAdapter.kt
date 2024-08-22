package org.unizd.rma.aleric

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val posts: List<Post>, private val onItemClick: (Post) -> Unit) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position], onItemClick)
    }

    override fun getItemCount(): Int = posts.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.textViewTitle)
        private val body = itemView.findViewById<TextView>(R.id.textViewBody)
        private val seeMoreButton = itemView.findViewById<Button>(R.id.buttonSeeMore)

        fun bind(post: Post, onItemClick: (Post) -> Unit) {
            title.text = post.title
            body.text = post.body

            seeMoreButton.setOnClickListener { onItemClick(post) }
        }
    }
}

