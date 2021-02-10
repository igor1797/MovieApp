package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.common.Connectivity
import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.networking.TvShowApiService
import igor.kuridza.dice.movieapp.prefs.settings.SettingsPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowRepositoryImpl(
    private val tvShowApiService: TvShowApiService,
    private val settingsPrefs: SettingsPrefs,
    private val connectivity: Connectivity
): TvShowRepository {

    override fun getTvShowsByType(tvShowType: String): Flow<Resource<GetTvShowsResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getTvShowsByType(tvShowType, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getPrimaryTvShowDetailsById(tvShowId: Int): Flow<Resource<TvShowDetails>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getPrimaryTvShowDetailsById(tvShowId, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getCastAndCrewForTvShow(tvShowId: Int): Flow<Resource<GetCreditsResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getCastAndCrewForTvShow(tvShowId, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }


    override fun rateTvShow(
        tvShowId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<Resource<MessageResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest {
                tvShowApiService.rateTvShow(
                    tvShowId,
                    sessionId,
                    RatingValue(ratingValue)
                )
            }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<Resource<GetImagesResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getImagesThatBelongToTvShow(tvShowId) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun searchTvShows(searchQuery: String): Flow<Resource<GetTvShowsResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            return makeNetworkRequest { tvShowApiService.searchTvShows(searchQuery, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getAccountStatesForTvShow(
        tvShowId: Int,
        sessionId: String
    ): Flow<Resource<AccountStatesResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getAccountStatesForTvShow(tvShowId, sessionId) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    companion object {
        private const val NO_INTERNET_CONNECTION_MESSAGE = "No internet connection"
    }
}