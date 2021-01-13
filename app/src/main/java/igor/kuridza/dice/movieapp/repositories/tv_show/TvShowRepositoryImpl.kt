package igor.kuridza.dice.movieapp.repositories.tv_show

import igor.kuridza.dice.movieapp.networking.TvShowApiService

class TvShowRepositoryImpl(
    private val tvShowApiService: TvShowApiService
): TvShowRepository {

    override suspend fun getTvShowsByType(tvShowType: String, language: String) =
            tvShowApiService.getTvShowsByType(tvShowType, language)

    override suspend fun removeRatingForTvShow(tvShowId: Int) =
            tvShowApiService.removeRatingForTvShow(tvShowId)

    override suspend fun rateTvShow(tvShowId: Int, ratingValue: Number) =
            tvShowApiService.rateTvShow(tvShowId, ratingValue)

    override suspend fun getImagesThatBelongToTvShow(tvShowId: Int, language: String) =
            tvShowApiService.getImagesThatBelongToTvShow(tvShowId, language)

    override suspend fun getTvShowListForTvShow(tvShowId: Int, tvShowListType: String, language: String) =
            tvShowApiService.getTvShowListForTvShow(tvShowId, tvShowListType, language)

    override suspend fun getUserReviewsForTvShow(tvShowId: Int, language: String) =
            tvShowApiService.getUserReviewsForTvShow(tvShowId, language)
}