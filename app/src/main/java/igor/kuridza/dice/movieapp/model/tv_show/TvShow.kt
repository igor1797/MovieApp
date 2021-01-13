package igor.kuridza.dice.movieapp.model.tv_show

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    val name: String,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val popularity: Number,
    @SerializedName("vote_count")
    val voteCount: Int,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Number,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("origin_country")
    val originCountry: List<String>
)
