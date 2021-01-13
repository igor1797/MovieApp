package igor.kuridza.dice.movieapp.model.review.author

import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    val name: String,
    val username: String,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    val rating: Int?
)
