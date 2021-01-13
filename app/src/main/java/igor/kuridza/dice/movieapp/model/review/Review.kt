package igor.kuridza.dice.movieapp.model.review

import com.google.gson.annotations.SerializedName
import igor.kuridza.dice.movieapp.model.review.author.AuthorDetails

data class Review(
    val author: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)
