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

    override suspend fun getMoviesByType(
        movieType: String,
        language: String
    ): Flow<Resource<GetMoviesResponse>> =
        makeNetworkRequest(movieType, language) { _movieType, _language ->
            movieApiService.getMoviesByType(_movieType, _language)
        }

    override suspend fun getPrimaryInformationAboutMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<MovieDetails>> =
        makeNetworkRequest(movieId, language) { _movieId, _language ->
            movieApiService.getPrimaryInformationAboutMovie(_movieId, _language)
        }

    override suspend fun getCastAndCrewForAMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<GetCreditsResponse>> =
        makeNetworkRequest(movieId, language) { _movieId, _language ->
            movieApiService.getCastAndCrewForAMovie(_movieId, _language)
        }

    override suspend fun rateMovie(movieId: Int, ratingValue: Number) =
        movieApiService.rateMovie(movieId, ratingValue)

    override suspend fun removeRatingForMovie(movieId: Int) =
        movieApiService.removeRatingForMovie(movieId)

    override suspend fun getListOfMoviesForMovie(
        movieId: Number,
        movieListType: String,
        language: String
    ): Flow<Resource<GetMoviesResponse>> =
        makeNetworkRequest(
            movieId,
            movieListType,
            language
        ) { _movieId, _movieListType, _language ->
            movieApiService.getListOfMoviesForMovie(_movieId.toInt(), _movieListType, _language)
        }

    override suspend fun getUserReviewsForMovie(
        movieId: Int,
        language: String
    ): Flow<Resource<GetReviewsResponse>> =
        makeNetworkRequest(movieId, language) { _movieId, _language ->
            movieApiService.getUserReviewsForMovie(_movieId, _language)
        }

    override suspend fun getImagesThatBelongToMovie(movieId: Int): Flow<Resource<GetImagesResponse>> =
        makeNetworkRequest(movieId) { _movieId ->
            movieApiService.getImagesThatBelongToMovie(_movieId)
        }
}