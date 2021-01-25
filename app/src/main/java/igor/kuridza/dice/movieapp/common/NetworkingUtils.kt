package igor.kuridza.dice.movieapp.common

import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.resource.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> makeNetworkRequest(apiCall: suspend () -> Response<T>): Flow<Resource<T>> {
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
