package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    fun getTvShowsByType(tvShowType: String): Flow<ViewState<GetTvShowsResponse>>

    fun getPrimaryTvShowDetailsById(tvShowId: Int): Flow<ViewState<TvShowDetails>>

    fun getCastAndCrewForTvShow(tvShowId: Int): Flow<ViewState<GetCreditsResponse>>

    fun searchTvShows(searchQuery: String): Flow<ViewState<GetTvShowsResponse>>

    fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<ViewState<GetImagesResponse>>

    fun rateTvShow(
        tvShowId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<ViewState<MessageResponse>>

    fun getAccountStatesForTvShow(
        tvShowId: Int,
        sessionId: String
    ): Flow<ViewState<AccountStatesResponse>>
}