package igor.kuridza.dice.movieapp.common

import igor.kuridza.dice.movieapp.model.view_state.Error
import igor.kuridza.dice.movieapp.model.view_state.Loading
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import igor.kuridza.dice.movieapp.model.view_state.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> makeNetworkRequest(apiCall: suspend () -> Response<T>): Flow<ViewState<T>> {
    return flow {
        emit(Loading)
        try {
            val response: Response<T> = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Success(it))
                }
            } else
                emit(Error(response.message()))
        } catch (e: Throwable) {
            emit(Error(e.message))
        }
    }
}
