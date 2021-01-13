package igor.kuridza.dice.movieapp.model.tv_show

import com.google.gson.annotations.SerializedName

data class GetTvShowsResponse(
        val page: Int,
        @SerializedName("results")
        val tvShowList: List<TvShow>,
        @SerializedName("total_results")
        val totalResults: Int,
        @SerializedName("total_pages")
        val totalPages: Int
)
