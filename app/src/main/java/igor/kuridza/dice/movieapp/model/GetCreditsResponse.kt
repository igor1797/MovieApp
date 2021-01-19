package igor.kuridza.dice.movieapp.model

data class GetCreditsResponse(
    val id: Int,
    val cast: List<Person>
)
