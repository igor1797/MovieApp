package igor.kuridza.dice.movieapp.repositories.movie

import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.review.GetReviewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {

    suspend fun getMoviesByType(movieType: String, language: String): Flow<Resource<GetMoviesResponse>>

    suspend fun rateMovie(movieId: Int, ratingValue: Number): Response<MessageResponse>

    suspend fun removeRatingForMovie(movieId: Int):  Response<MessageResponse>

    suspend fun getListOfMoviesForMovie(movieId: Number, movieListType: String, language: String): Flow<Resource<GetMoviesResponse>>

    suspend fun getUserReviewsForMovie(movieId: Int, language: String):  Flow<Resource<GetReviewsResponse>>

    suspend fun getImagesThatBelongToMovie(movieId: Int, language: String): Response<GetImagesResponse>
}