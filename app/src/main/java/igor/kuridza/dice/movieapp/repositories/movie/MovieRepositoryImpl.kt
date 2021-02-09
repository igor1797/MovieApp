package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.networking.MovieApiService
import igor.kuridza.dice.movieapp.prefs.settings.SettingsPrefs
import igor.kuridza.dice.movieapp.utils.resource.ResourceHelper
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val settingsPrefs: SettingsPrefs,
    private val resourceHelper: ResourceHelper
): MovieRepository {

    override fun getMoviesByType(movieType: String): Flow<Resource<GetMoviesResponse>> {
        val language = settingsPrefs.getLanguage()
        return makeNetworkRequest { movieApiService.getMoviesByType(movieType, language) }
    }

    override fun searchMovies(searchQuery: String): Flow<Resource<GetMoviesResponse>> {
        val language = settingsPrefs.getLanguage()
        return makeNetworkRequest { movieApiService.searchMovies(searchQuery, language) }
    }

    override fun getPrimaryInformationAboutMovie(movieId: Int): Flow<Resource<MovieDetails>> {
        val language = settingsPrefs.getLanguage()
        return makeNetworkRequest {
            movieApiService.getPrimaryInformationAboutMovie(
                movieId,
                language
            )
        }
    }

    override fun getCastAndCrewForAMovie(movieId: Int): Flow<Resource<GetCreditsResponse>> {
        val language = settingsPrefs.getLanguage()
        return makeNetworkRequest { movieApiService.getCastAndCrewForAMovie(movieId, language) }
    }

    override fun rateMovie(
        movieId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<Resource<MessageResponse>> =
        makeNetworkRequest {
            movieApiService.rateMovie(
                movieId,
                sessionId,
                RatingValue(ratingValue)
            )
        }

    override fun getAccountStatesForMovie(movieId: Int, sessionId: String) =
        makeNetworkRequest { movieApiService.getAccountStatesForMovie(movieId, sessionId) }

    override fun getImagesThatBelongToMovie(movieId: Int): Flow<Resource<GetImagesResponse>> =
        makeNetworkRequest { movieApiService.getImagesThatBelongToMovie(movieId) }

    override fun getPopularString(): String = resourceHelper.popularString()

    override fun getTopRatedString(): String = resourceHelper.topRatedString()

    override fun getUpcomingString(): String = resourceHelper.upcomingString()
}