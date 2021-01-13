package igor.kuridza.dice.movieapp.common

import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.model.resource.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> makeNetworkRequest(type: String, language: String, apiCall: suspend (type: String, language: String) -> Response<T>): Flow<Resource<T>>{
    return flow {
        emit(Loading)
        try{
            val response: Response<T> = apiCall.invoke(type, language)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(Success(it))
                }
            }else
                emit(Error(response.message()))
        }catch (e: Throwable){
            emit(Error(e.message))
        }
    }
}

fun <T> makeNetworkRequest(id: Int, language: String, apiCall: suspend (id: Int, language: String) -> Response<T>): Flow<Resource<T>>{
    return flow {
        emit(Loading)
        try{
            val response: Response<T> = apiCall.invoke(id, language)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(Success(it))
                }
            }else
                emit(Error(response.message()))
        }catch (e: Throwable){
            emit(Error(e.message))
        }
    }
}

fun <T> makeNetworkRequest(value: Number, type: String, language: String, apiCall: suspend (value: Number, type: String, language: String) -> Response<T>): Flow<Resource<T>>{
    return flow {
        emit(Loading)
        try{
            val response: Response<T> = apiCall.invoke(value, type, language)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(Success(it))
                }
            }else
                emit(Error(response.message()))
        }catch (e: Throwable){
            emit(Error(e.message))
        }
    }
}