package igor.kuridza.dice.movieapp.model.movie

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
