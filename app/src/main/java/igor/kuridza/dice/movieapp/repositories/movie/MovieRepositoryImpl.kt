package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.common.Connectivity
import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
import igor.kuridza.dice.movieapp.model.view_state.Error
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.networking.MovieApiService
import igor.kuridza.dice.movieapp.prefs.settings.SettingsPrefs
import igor.kuridza.dice.movieapp.common.utils.resource.ResourceHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val settingsPrefs: SettingsPrefs,
    private val resourceHelper: ResourceHelper,
    private val connectivity: Connectivity
): MovieRepository {

    override fun getMoviesByType(movieType: String): Flow<ViewState<GetMoviesResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { movieApiService.getMoviesByType(movieType, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun searchMovies(searchQuery: String): Flow<ViewState<GetMoviesResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { movieApiService.searchMovies(searchQuery, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getPrimaryInformationAboutMovie(movieId: Int): Flow<ViewState<MovieDetails>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest {
                movieApiService.getPrimaryInformationAboutMovie(
                    movieId,
                    language
                )
            }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getCastAndCrewForAMovie(movieId: Int): Flow<ViewState<GetCreditsResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { movieApiService.getCastAndCrewForAMovie(movieId, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun rateMovie(
        movieId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<ViewState<MessageResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest {
                movieApiService.rateMovie(
                    movieId,
                    sessionId,
                    RatingValue(ratingValue)
                )
            }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getAccountStatesForMovie(
        movieId: Int,
        sessionId: String
    ): Flow<ViewState<AccountStatesResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { movieApiService.getAccountStatesForMovie(movieId, sessionId) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getImagesThatBelongToMovie(movieId: Int): Flow<ViewState<GetImagesResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { movieApiService.getImagesThatBelongToMovie(movieId) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getPopularString(): String = resourceHelper.popularString()

    override fun getTopRatedString(): String = resourceHelper.topRatedString()

    override fun getUpcomingString(): String = resourceHelper.upcomingString()

    companion object {
        private const val NO_INTERNET_CONNECTION_MESSAGE = "No internet connection"
    }
}