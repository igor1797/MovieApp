package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.networking.TvShowApiService
import kotlinx.coroutines.flow.Flow

class TvShowRepositoryImpl(
    private val tvShowApiService: TvShowApiService
): TvShowRepository {

    override fun getTvShowsByType(
        tvShowType: String,
        language: String
    ): Flow<Resource<GetTvShowsResponse>> =
        makeNetworkRequest {
            tvShowApiService.getTvShowsByType(tvShowType, language)
        }

    override fun getPrimaryTvShowDetailsById(
        tvShowId: Int,
        language: String
    ): Flow<Resource<TvShowDetails>> =
        makeNetworkRequest {
            tvShowApiService.getPrimaryTvShowDetailsById(tvShowId, language)
        }

    override fun getCastAndCrewForTvShow(
        tvShowId: Int,
        language: String
    ): Flow<Resource<GetCreditsResponse>> =
        makeNetworkRequest {
            tvShowApiService.getCastAndCrewForTvShow(tvShowId, language)
        }

    override suspend fun removeRatingForTvShow(tvShowId: Int) =
        tvShowApiService.removeRatingForTvShow(tvShowId)

    override suspend fun rateTvShow(tvShowId: Int, ratingValue: Number) =
        tvShowApiService.rateTvShow(tvShowId, ratingValue)

    override fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<Resource<GetImagesResponse>> =
        makeNetworkRequest {
            tvShowApiService.getImagesThatBelongToTvShow(tvShowId)
        }

    override suspend fun getTvShowListForTvShow(
        tvShowId: Int,
        tvShowListType: String,
        language: String
    ) =
        tvShowApiService.getTvShowListForTvShow(tvShowId, tvShowListType, language)

    override suspend fun getUserReviewsForTvShow(tvShowId: Int, language: String) =
        tvShowApiService.getUserReviewsForTvShow(tvShowId, language)

    override fun searchTvShows(
        searchQuery: String,
        language: String
    ): Flow<Resource<GetTvShowsResponse>> =
        makeNetworkRequest {
            tvShowApiService.searchTvShows(searchQuery, language)
        }
}