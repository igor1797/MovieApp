package igor.kuridza.dice.movieapp.networking

import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.movie.MovieDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
import retrofit2.Response
import retrofit2.http.*

interface MovieApiService {

    @GET("$MOVIE/{$MOVIE_TYPE}")
    suspend fun getMoviesByType(
        @Path(MOVIE_TYPE) movieType: String,
        @Query(LANGUAGE) language: String
    ): Response<GetMoviesResponse>

    @GET("$MOVIE/{$MOVIE_ID}")
    suspend fun getPrimaryInformationAboutMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String
    ): Response<MovieDetails>

    @GET("$MOVIE/{$MOVIE_ID}/credits")
    suspend fun getCastAndCrewForAMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String
    ): Response<GetCreditsResponse>

    //The value is expected to be between 0.5 and 10.0.
    @POST("$MOVIE/{$MOVIE_ID}/$RATING")
    suspend fun rateMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query("session_id") sessionId: String,
        @Body ratingValueRequest: RatingValue
    ): Response<MessageResponse>

    @GET("$MOVIE/{$MOVIE_ID}/account_states")
    suspend fun getAccountStatesForMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query("session_id") sessionId: String
    ): Response<AccountStatesResponse>

    @GET("$MOVIE/{$MOVIE_ID}/{$MOVIE_LIST_TYPE}")
    suspend fun getListOfMoviesForMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Path(MOVIE_LIST_TYPE) movieListType: String,
        @Query(LANGUAGE) language: String
    ): Response<GetMoviesResponse>

    @GET("$MOVIE/{$MOVIE_ID}/images")
    suspend fun getImagesThatBelongToMovie(
        @Path(MOVIE_ID) movieId: Int
    ): Response<GetImagesResponse>

    @GET("search/$MOVIE")
    suspend fun searchMovies(
        @Query(QUERY) searchQuery: String,
        @Query(LANGUAGE) language: String
    ): Response<GetMoviesResponse>
}