package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.review.GetReviewsResponse
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TvShowRepository {
    suspend fun getTvShowsByType(
        tvShowType: String,
        language: String
    ): Flow<Resource<GetTvShowsResponse>>

    suspend fun getPrimaryTvShowDetailsById(
        tvShowId: Int,
        language: String
    ): Flow<Resource<TvShowDetails>>

    suspend fun removeRatingForTvShow(tvShowId: Int): Response<MessageResponse>

    suspend fun rateTvShow(tvShowId: Int, ratingValue: Number): Response<MessageResponse>

    suspend fun getTvShowListForTvShow(
        tvShowId: Int,
        tvShowListType: String,
        language: String
    ): Response<GetTvShowsResponse>

    suspend fun getUserReviewsForTvShow(
        tvShowId: Int,
        language: String
    ): Response<GetReviewsResponse>

    suspend fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<Resource<GetImagesResponse>>
}