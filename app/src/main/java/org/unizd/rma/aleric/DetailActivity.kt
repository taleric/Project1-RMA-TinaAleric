package org.unizd.rma.aleric

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.util.Log



class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val post = intent.getSerializableExtra("POST") as? Post

        if (post == null) {
            Log.e("DetailActivity", "Post data is missing")
            finish()
            return
        }

        val title = findViewById<TextView>(R.id.textViewTitle)
        val body = findViewById<TextView>(R.id.textViewBody)
        val userId = findViewById<TextView>(R.id.textViewUserId)
        val id = findViewById<TextView>(R.id.textViewId)
        val commentsLayout = findViewById<LinearLayout>(R.id.commentsLayout)

        title.text = post?.title
        body.text = post?.body
        userId.text = "User ID: ${post?.userId}"
        id.text = "Post ID: ${post?.id}"

        post?.comments?.forEach { comment ->
            val commentView = layoutInflater.inflate(R.layout.item_comment, commentsLayout, false)
            val commentName = commentView.findViewById<TextView>(R.id.textViewCommentName)
            val commentEmail = commentView.findViewById<TextView>(R.id.textViewCommentEmail)
            val commentBody = commentView.findViewById<TextView>(R.id.textViewCommentBody)

            commentName.text = comment.name
            commentEmail.text = comment.email
            commentBody.text = comment.body

            commentsLayout.addView(commentView)
        }

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
