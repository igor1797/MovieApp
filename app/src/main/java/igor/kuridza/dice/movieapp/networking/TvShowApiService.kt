package igor.kuridza.dice.movieapp.networking

import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import retrofit2.Response
import retrofit2.http.*

interface TvShowApiService {

    @GET("$TV_SHOW/{$TV_SHOW_TYPE}")
    suspend fun getTvShowsByType(
        @Path(TV_SHOW_TYPE) tvShowType: String,
        @Query(LANGUAGE) language: String
    ): Response<GetTvShowsResponse>

    @GET("$TV_SHOW/{$TV_SHOW_ID}")
    suspend fun getPrimaryTvShowDetailsById(
        @Path(TV_SHOW_ID) tvShowId: Int,
        @Query(LANGUAGE) language: String
    ): Response<TvShowDetails>

    @GET("$TV_SHOW/{$TV_SHOW_ID}/credits")
    suspend fun getCastAndCrewForTvShow(
        @Path(TV_SHOW_ID) tvShowId: Int,
        @Query(LANGUAGE) language: String
    ): Response<GetCreditsResponse>

    @POST("$TV_SHOW/{$TV_SHOW_ID}/$RATING")
    suspend fun rateTvShow(
        @Path(TV_SHOW_ID) tvShowId: Int,
        @Query("session_id") sessionId: String,
        @Body ratingValueRequest: RatingValue
    ): Response<MessageResponse>

    @GET("$TV_SHOW/{$TV_SHOW_ID}/account_states")
    suspend fun getAccountStatesForTvShow(
        @Path(TV_SHOW_ID) tvSHowId: Int,
        @Query("session_id") sessionId: String
    ): Response<AccountStatesResponse>

    @GET("$TV_SHOW/{$TV_SHOW_ID}/images")
    suspend fun getImagesThatBelongToTvShow(
        @Path(TV_SHOW_ID) tvShowId: Int
    ): Response<GetImagesResponse>

    @GET("search/$TV_SHOW")
    suspend fun searchTvShows(
        @Query(QUERY) searchQuery: String,
        @Query(LANGUAGE) language: String
    ): Response<GetTvShowsResponse>
}