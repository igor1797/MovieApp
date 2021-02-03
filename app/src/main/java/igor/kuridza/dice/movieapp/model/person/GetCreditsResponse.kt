package igor.kuridza.dice.movieapp.model.person

data class GetCreditsResponse(
    val id: Int,
    val cast: List<Person>
)
