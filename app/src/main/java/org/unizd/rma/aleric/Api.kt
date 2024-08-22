import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object Api {
private const val POSTS_URL = "https://jsonplaceholder.typicode.com/posts"
private const val COMMENTS_URL = "https://jsonplaceholder.typicode.com/comments?postId="

fun fetchPosts(): String {
    return fetchData(POSTS_URL)
}
    fun fetchComments(postId: Int): String {
        return fetchData("$COMMENTS_URL$postId")
    }

private fun fetchData(urlString: String): String {
    val url = URL(urlString)
    var connection: HttpURLConnection? = null
    var response = ""

    try {
        connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        val inputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }

        response = stringBuilder.toString()

        reader.close()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        connection?.disconnect()
    }

    return response
}
}
