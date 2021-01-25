package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.review.GetReviewsResponse
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
        makeNetworkRequest { ->
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

    override suspend fun rateMovie(movieId: Int, ratingValue: Number) =
        movieApiService.rateMovie(movieId, ratingValue)

    override suspend fun removeRatingForMovie(movieId: Int) =
        movieApiService.removeRatingForMovie(movieId)

    override fun getListOfMoviesForMovie(
        movieId: Number,
        movieListType: String,
        language: String
    ): Flow<Resource<GetMoviesResponse>> =
        makeNetworkRequest {
            movieApiService.getListOfMoviesForMovie(movieId.toInt(), movieListType, language)
        }

    override fun getUserReviewsForMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<GetReviewsResponse>> =
        makeNetworkRequest {
            movieApiService.getUserReviewsForMovie(movieId, language)
        }

    override fun getImagesThatBelongToMovie(movieId: Int): Flow<Resource<GetImagesResponse>> =
        makeNetworkRequest {
            movieApiService.getImagesThatBelongToMovie(movieId)
        }
}