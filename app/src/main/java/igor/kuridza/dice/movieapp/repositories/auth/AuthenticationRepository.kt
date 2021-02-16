package igor.kuridza.dice.movieapp.repositories.auth

import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.view_state.ViewState
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun createRequestToken(): Flow<ViewState<GetRequestToken>>

    fun login(
        username: String,
        password: String,
        requestToken: String
    ): Flow<ViewState<GetRequestToken>>

    fun createSessionId(requestToken: String): Flow<ViewState<GetSessionId>>

    suspend fun saveUserSessionId(sessionId: String)

    suspend fun userSkippedLogin(skippedLogin: Boolean)

    fun getSessionId(): String

    fun getUserInvalidUsernameStringId(): Int

    fun getInvalidPasswordStringId(): Int

    fun isUserSkippedLogin(): Boolean

    fun isUserLoggedIn(): Boolean

}