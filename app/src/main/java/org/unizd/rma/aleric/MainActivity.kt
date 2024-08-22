package org.unizd.rma.aleric

import Api.fetchPosts
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        FetchPostsTask().execute()
    }

    inner class FetchPostsTask : AsyncTask<Void, Void, List<Post>?>() {
        override fun doInBackground(vararg params: Void?): List<Post>? {
            return try {
                val postsResponse = fetchPosts()
                val postsType = object : TypeToken<List<Post>>() {}.type
                Gson().fromJson(postsResponse, postsType)
            } catch (e: Exception) {
                Log.e("FetchPostsTask", "Error fetching posts", e)
                null
            }
        }

        override fun onPostExecute(result: List<Post>?) {
            if (result != null) {
                postAdapter = PostAdapter(result) { post ->

                    FetchPostDetailsTask(post).execute()
                }
                recyclerView.adapter = postAdapter
            } else {
                Log.e("MainActivity", "Failed to load posts")
            }
        }
    }

    inner class FetchPostDetailsTask(private val post: Post) : AsyncTask<Void, Void, Post?>() {
        override fun doInBackground(vararg params: Void?): Post? {
            return try {
                val commentsResponse = Api.fetchComments(post.id)
                val commentsType = object : TypeToken<List<Comment>>() {}.type
                val comments: List<Comment> = Gson().fromJson(commentsResponse, commentsType)
                post.comments = comments
                post
            } catch (e: Exception) {
                Log.e("FetchPostDetailsTask", "Error fetching post details", e)
                null
            }
        }

        override fun onPostExecute(result: Post?) {
            if (result != null) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("POST", result)
                startActivity(intent)
            } else {
                Log.e("MainActivity", "Failed to load post details")
            }
        }
    }
}
