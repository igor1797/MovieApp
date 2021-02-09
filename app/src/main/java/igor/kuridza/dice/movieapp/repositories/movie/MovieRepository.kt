package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesByType(movieType: String): Flow<Resource<GetMoviesResponse>>

    fun getPrimaryInformationAboutMovie(movieId: Int): Flow<Resource<MovieDetails>>

    fun getCastAndCrewForAMovie(movieId: Int): Flow<Resource<GetCreditsResponse>>

    fun getImagesThatBelongToMovie(movieId: Int): Flow<Resource<GetImagesResponse>>

    fun searchMovies(searchQuery: String): Flow<Resource<GetMoviesResponse>>

    fun rateMovie(
        movieId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<Resource<MessageResponse>>

    fun getAccountStatesForMovie(
        movieId: Int,
        sessionId: String
    ): Flow<Resource<AccountStatesResponse>>

    fun getTopRatedString(): String

    fun getPopularString(): String

    fun getUpcomingString(): String
}