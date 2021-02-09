package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.model.person.GetCreditsResponse
import igor.kuridza.dice.movieapp.model.image.GetImagesResponse
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.rating.AccountStatesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.tv_show.GetTvShowsResponse
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    fun getTvShowsByType(tvShowType: String): Flow<Resource<GetTvShowsResponse>>

    fun getPrimaryTvShowDetailsById(tvShowId: Int): Flow<Resource<TvShowDetails>>

    fun getCastAndCrewForTvShow(tvShowId: Int): Flow<Resource<GetCreditsResponse>>

    fun searchTvShows(searchQuery: String): Flow<Resource<GetTvShowsResponse>>

    fun getImagesThatBelongToTvShow(tvShowId: Int): Flow<Resource<GetImagesResponse>>

    fun rateTvShow(
        tvShowId: Int,
        sessionId: String,
        ratingValue: Number
    ): Flow<Resource<MessageResponse>>

    fun getAccountStatesForTvShow(
        tvShowId: Int,
        sessionId: String
    ): Flow<Resource<AccountStatesResponse>>
}