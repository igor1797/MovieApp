package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.networking.MovieApiService
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService
): MovieRepository {

    override fun getMoviesByType(
        movieType: String,
        language: String
    ): Flow<Resource<GetMoviesResponse>> =
        makeNetworkRequest {
            movieApiService.getMoviesByType(movieType, language)
        }

    override fun searchMovies(
        searchQuery: String,
        language: String
    ): Flow<Resource<GetMoviesResponse>> =
        makeNetworkRequest {
            movieApiService.searchMovies(searchQuery, language)
        }

    override fun getPrimaryInformationAboutMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<MovieDetails>> =
        makeNetworkRequest {
            movieApiService.getPrimaryInformationAboutMovie(movieId, language)
        }

    override fun getCastAndCrewForAMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<GetCreditsResponse>> =
        makeNetworkRequest {
            movieApiService.getCastAndCrewForAMovie(movieId, language)
        }

    override fun rateMovie(movieId: Int, sessionId: String, ratingValue: Number) =
        makeNetworkRequest {
            movieApiService.rateMovie(movieId, sessionId, RatingValue(ratingValue))
        }

    override fun getAccountStatesForMovie(movieId: Int, sessionId: String) =
        makeNetworkRequest {
            movieApiService.getAccountStatesForMovie(movieId, sessionId)
        }

    override fun getImagesThatBelongToMovie(movieId: Int): Flow<Resource<GetImagesResponse>> =
        makeNetworkRequest {
            movieApiService.getImagesThatBelongToMovie(movieId)
        }
}