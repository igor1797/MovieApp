package igor.kuridza.dice.movieapp.model.view_state

sealed class ViewState<out T>
data class Success<out T>(val data: T) : ViewState<T>()
data class Error(val message: String?) : ViewState<Nothing>()
object Loading : ViewState<Nothing>()

