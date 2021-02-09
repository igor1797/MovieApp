package igor.kuridza.dice.movieapp.repositories.auth

import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun createRequestToken(): Flow<Resource<GetRequestToken>>

    fun login(
        username: String,
        password: String,
        requestToken: String
    ): Flow<Resource<GetRequestToken>>

    fun createSessionId(requestToken: String): Flow<Resource<GetSessionId>>

    suspend fun saveUserSessionId(sessionId: String)

    suspend fun userSkippedLogin(skippedLogin: Boolean)

    fun getSessionId(): String

    fun getUserInvalidUsernameStringId(): Int

    fun getInvalidPasswordStringId(): Int

    fun isUserSkippedLogin(): Boolean

    fun isUserLoggedIn(): Boolean

}