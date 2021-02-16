package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.common.Connectivity
import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.rating.RatingValue
import igor.kuridza.dice.movieapp.model.view_state.Error
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.view_state.ViewState
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

    override fun getTvShowsByType(tvShowType: String): Flow<ViewState<GetTvShowsResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getTvShowsByType(tvShowType, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getPrimaryTvShowDetailsById(tvShowId: Int): Flow<ViewState<TvShowDetails>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getPrimaryTvShowDetailsById(tvShowId, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getCastAndCrewForTvShow(tvShowId: Int): Flow<ViewState<GetCreditsResponse>> {
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
    ): Flow<ViewState<MessageResponse>> {
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

    override fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<ViewState<GetImagesResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getImagesThatBelongToTvShow(tvShowId) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun searchTvShows(searchQuery: String): Flow<ViewState<GetTvShowsResponse>> {
        val language = settingsPrefs.getLanguage()
        return if (connectivity.hasNetworkAccess())
            return makeNetworkRequest { tvShowApiService.searchTvShows(searchQuery, language) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun getAccountStatesForTvShow(
        tvShowId: Int,
        sessionId: String
    ): Flow<ViewState<AccountStatesResponse>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { tvShowApiService.getAccountStatesForTvShow(tvShowId, sessionId) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    companion object {
        private const val NO_INTERNET_CONNECTION_MESSAGE = "No internet connection"
    }
}