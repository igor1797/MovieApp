package igor.kuridza.dice.movieapp.model.review

import com.google.gson.annotations.SerializedName

data class GetReviewsResponse(
    val id: Int,
    val page: Int,
    @SerializedName("results")
    val reviews: List<Review>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
