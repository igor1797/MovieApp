package igor.kuridza.dice.movieapp.model.movie

import com.google.gson.annotations.SerializedName
import igor.kuridza.dice.movieapp.model.genre.Genre

data class MovieDetails(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    val status: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Number,
    val tagline: String?
)