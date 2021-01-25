package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.model.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.review.GetReviewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {

    fun getMoviesByType(
        movieType: String,
        language: String
    ): Flow<Resource<GetMoviesResponse>>

    fun getPrimaryInformationAboutMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<MovieDetails>>

    fun getCastAndCrewForAMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<GetCreditsResponse>>

    fun getListOfMoviesForMovie(
        movieId: Number,
        movieListType: String,
        language: String
    ): Flow<Resource<GetMoviesResponse>>

    fun getUserReviewsForMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<GetReviewsResponse>>

    fun getImagesThatBelongToMovie(movieId: Int): Flow<Resource<GetImagesResponse>>

    fun searchMovies(searchQuery: String, language: String): Flow<Resource<GetMoviesResponse>>

    suspend fun rateMovie(movieId: Int, ratingValue: Number): Response<MessageResponse>

    suspend fun removeRatingForMovie(movieId: Int): Response<MessageResponse>
}