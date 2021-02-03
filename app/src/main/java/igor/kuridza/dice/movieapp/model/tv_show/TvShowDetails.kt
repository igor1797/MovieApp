package igor.kuridza.dice.movieapp.model.tv_show

import com.google.gson.annotations.SerializedName
import igor.kuridza.dice.movieapp.model.genre.Genre
import igor.kuridza.dice.movieapp.model.person.CreatedBy

data class TvShowDetails(
    val backdrop_path: String,
    @SerializedName("created_by")
    val createdBy: List<CreatedBy>,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    val genres: List<Genre>,
    val id: Int,
    val name: String,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val poster_path: String,
    val status: String,
    val tagline: String,
    val type: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)