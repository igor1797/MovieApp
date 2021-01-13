package igor.kuridza.dice.movieapp.model.image

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("file_path")
    val filePath: String,
    val height: Int,
    val width: Int,
    @SerializedName("vote_average")
    val voteAverage: Int,
    @SerializedName("vote_count")
    val voteCount: Int
)
