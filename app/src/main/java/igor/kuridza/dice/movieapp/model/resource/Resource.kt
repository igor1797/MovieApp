package igor.kuridza.dice.movieapp.model.resource

sealed class Resource<out T>
data class Success<out T>(val data: T) : Resource<T>()
data class Error(val message: String?) : Resource<Nothing>()
object Loading : Resource<Nothing>()

