package org.unizd.rma.aleric

import java.io.Serializable

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    var comments: List<Comment>? = null
) : Serializable
