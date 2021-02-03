package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
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


    override fun rateTvShow(
        tvShowId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<Resource<MessageResponse>> =
        makeNetworkRequest {
            tvShowApiService.rateTvShow(tvShowId, sessionId, RatingValue(ratingValue))
        }

    override fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<Resource<GetImagesResponse>> =
        makeNetworkRequest {
            tvShowApiService.getImagesThatBelongToTvShow(tvShowId)
        }

    override fun searchTvShows(
        searchQuery: String,
        language: String
    ): Flow<Resource<GetTvShowsResponse>> =
        makeNetworkRequest {
            tvShowApiService.searchTvShows(searchQuery, language)
        }

    override fun getAccountStatesForTvShow(
        tvShowId: Int,
        sessionId: String
    ): Flow<Resource<AccountStatesResponse>> =
        makeNetworkRequest {
            tvShowApiService.getAccountStatesForTvShow(tvShowId, sessionId)
        }
}