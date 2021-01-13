package igor.kuridza.dice.movieapp.networking

import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.review.GetReviewsResponse
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import retrofit2.Response
import retrofit2.http.*

interface TvShowApiService {

    @GET("$TV_SHOW/{$TV_SHOW_TYPE}")
    suspend fun getTvShowsByType(
            @Path(TV_SHOW_TYPE) tvShowType: String,
            @Query(LANGUAGE) language: String
    ): Response<GetTvShowsResponse>

    @DELETE("$TV_SHOW/{$TV_SHOW_ID}/$RATING")
    suspend fun removeRatingForTvShow(@Path(TV_SHOW_ID) tvShowId: Int): Response<MessageResponse>

    @POST("$TV_SHOW/{$TV_SHOW_ID}/$RATING")
    suspend fun rateTvShow(
            @Path(TV_SHOW_ID) tvShowId: Int,
            @Body value: Number
    ): Response<MessageResponse>

    @GET("$TV_SHOW/{$TV_SHOW_ID}/{$TV_SHOW_LIST_TYPE}")
    suspend fun getTvShowListForTvShow(
            @Path(TV_SHOW_ID) tvShowId: Int,
            @Path(TV_SHOW_LIST_TYPE) tvShowListType: String,
            @Query(LANGUAGE) language: String
    ): Response<GetTvShowsResponse>

    @GET("$TV_SHOW/{$TV_SHOW_ID}/reviews")
    suspend fun getUserReviewsForTvShow(
            @Path(TV_SHOW_ID) tvShowId: Int,
            @Query(LANGUAGE) language: String
    ): Response<GetReviewsResponse>

    @GET("$TV_SHOW/{$TV_SHOW_ID}/images")
    suspend fun getImagesThatBelongToTvShow(
            @Path(TV_SHOW_ID) tvShowId: Int,
            @Query(LANGUAGE) language: String
    ): Response<GetImagesResponse>
}