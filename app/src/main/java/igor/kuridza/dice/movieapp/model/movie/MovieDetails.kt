package igor.kuridza.dice.movieapp.model.movie

import com.google.gson.annotations.SerializedName
import igor.kuridza.dice.movieapp.model.*
import igor.kuridza.dice.movieapp.model.collection.Collection
import igor.kuridza.dice.movieapp.model.company.ProductionCompany
import igor.kuridza.dice.movieapp.model.genre.Genre

data class MovieDetails(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val belongs_to_collection: Collection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Number,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Number,
    val vote_count: Int
)