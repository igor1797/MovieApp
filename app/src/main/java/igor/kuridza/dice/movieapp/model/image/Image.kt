package igor.kuridza.dice.movieapp.model.image

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("file_path")
    val filePath: String,
    val height: Int,
    val width: Int,
)
