package igor.kuridza.dice.movieapp.networking

import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.review.GetReviewsResponse
import retrofit2.Response
import retrofit2.http.*

interface MovieApiService {

    @GET("$MOVIE/{$MOVIE_TYPE}")
    suspend fun getMoviesByType(
        @Path(MOVIE_TYPE) movieType: String,
        @Query(LANGUAGE) language: String
    ): Response<GetMoviesResponse>

    //The value is expected to be between 0.5 and 10.0.
    @POST("$MOVIE/{$MOVIE_ID}/$RATING")
    suspend fun rateMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Body value: Number
    ): Response<MessageResponse>

    @DELETE("$MOVIE/{$MOVIE_ID}/$RATING")
    suspend fun removeRatingForMovie(@Path(MOVIE_ID) movieId: Int): Response<MessageResponse>

    @GET("$MOVIE/{$MOVIE_ID}/{$MOVIE_LIST_TYPE}")
    suspend fun getListOfMoviesForMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Path(MOVIE_LIST_TYPE) movieListType: String,
        @Query(LANGUAGE) language: String
    ): Response<GetMoviesResponse>

    @GET("$MOVIE/{$MOVIE_ID}/reviews")
    suspend fun getUserReviewsForMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String
    ): Response<GetReviewsResponse>

    @GET("$MOVIE/{$MOVIE_ID}/images")
    suspend fun getImagesThatBelongToMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String
    ): Response<GetImagesResponse>
}