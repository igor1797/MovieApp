package igor.kuridza.dice.movieapp.utils.resource

interface ResourceHelper {
    fun topRatedString(): String

    fun popularString(): String

    fun upcomingString(): String

    fun onTheAirString(): String

    fun invalidPasswordStringId(): Int

    fun invalidUsernameStringId(): Int
}