package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesByType(movieType: String): Flow<ViewState<GetMoviesResponse>>

    fun getPrimaryInformationAboutMovie(movieId: Int): Flow<ViewState<MovieDetails>>

    fun getCastAndCrewForAMovie(movieId: Int): Flow<ViewState<GetCreditsResponse>>

    fun getImagesThatBelongToMovie(movieId: Int): Flow<ViewState<GetImagesResponse>>

    fun searchMovies(searchQuery: String): Flow<ViewState<GetMoviesResponse>>

    fun rateMovie(
        movieId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<ViewState<MessageResponse>>

    fun getAccountStatesForMovie(
        movieId: Int,
        sessionId: String
    ): Flow<ViewState<AccountStatesResponse>>

    fun getTopRatedString(): String

    fun getPopularString(): String

    fun getUpcomingString(): String
}