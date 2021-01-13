package igor.kuridza.dice.movieapp.model.image

data class GetImagesResponse(
    val id: Int,
    val backdrops: List<Image>,
    val posters: List<Image>
)
