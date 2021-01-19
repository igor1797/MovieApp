package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.networking.TvShowApiService
import kotlinx.coroutines.flow.Flow

class TvShowRepositoryImpl(
    private val tvShowApiService: TvShowApiService
): TvShowRepository {

    override suspend fun getTvShowsByType(
        tvShowType: String,
        language: String
    ): Flow<Resource<GetTvShowsResponse>> =
        makeNetworkRequest(tvShowType, language) { _tvShowType, _language ->
            tvShowApiService.getTvShowsByType(_tvShowType, _language)
        }

    override suspend fun getPrimaryTvShowDetailsById(
        tvShowId: Int,
        language: String
    ): Flow<Resource<TvShowDetails>> =
        makeNetworkRequest(tvShowId, language) { _tvShowId, _language ->
            tvShowApiService.getPrimaryTvShowDetailsById(_tvShowId, _language)
        }

    override suspend fun removeRatingForTvShow(tvShowId: Int) =
        tvShowApiService.removeRatingForTvShow(tvShowId)

    override suspend fun rateTvShow(tvShowId: Int, ratingValue: Number) =
        tvShowApiService.rateTvShow(tvShowId, ratingValue)

    override suspend fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<Resource<GetImagesResponse>> =
        makeNetworkRequest(tvShowId) { _tvShowId ->
            tvShowApiService.getImagesThatBelongToTvShow(_tvShowId)
        }

    override suspend fun getTvShowListForTvShow(
        tvShowId: Int,
        tvShowListType: String,
        language: String
    ) =
        tvShowApiService.getTvShowListForTvShow(tvShowId, tvShowListType, language)

    override suspend fun getUserReviewsForTvShow(tvShowId: Int, language: String) =
        tvShowApiService.getUserReviewsForTvShow(tvShowId, language)
}