package igor.kuridza.dice.movieapp.repositories.auth

import igor.kuridza.dice.movieapp.common.Connectivity
import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.auth.AuthRequest
import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.auth.RequestToken
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.networking.AuthenticationService
import igor.kuridza.dice.movieapp.prefs.user.UserPrefs
import igor.kuridza.dice.movieapp.utils.resource.ResourceHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthenticationRepositoryImpl(
    private val authenticationService: AuthenticationService,
    private val userPrefs: UserPrefs,
    private val resourceHelper: ResourceHelper,
    private val connectivity: Connectivity
) : AuthenticationRepository {

    override fun createRequestToken(): Flow<Resource<GetRequestToken>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { authenticationService.createRequestToken() }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun login(
        username: String,
        password: String,
        requestToken: String
    ): Flow<Resource<GetRequestToken>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest {
                authenticationService.validateRequestTokenWithUserNameAndPassword(
                    AuthRequest(username, password, requestToken)
                )
            }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override fun createSessionId(requestToken: String): Flow<Resource<GetSessionId>> {
        return if (connectivity.hasNetworkAccess())
            makeNetworkRequest { authenticationService.createNewSessionId(RequestToken(requestToken)) }
        else
            flow { emit(Error(NO_INTERNET_CONNECTION_MESSAGE)) }
    }

    override suspend fun saveUserSessionId(sessionId: String) {
        userPrefs.put(sessionId)
    }

    override suspend fun userSkippedLogin(skippedLogin: Boolean) {
        userPrefs.userSkippedLogin(true)
    }

    override fun getInvalidPasswordStringId(): Int = resourceHelper.invalidPasswordStringId()

    override fun getUserInvalidUsernameStringId(): Int = resourceHelper.invalidUsernameStringId()

    override fun isUserLoggedIn(): Boolean = userPrefs.get().isNotEmpty()

    override fun isUserSkippedLogin(): Boolean = userPrefs.isUserSkippedLogin()

    override fun getSessionId(): String = userPrefs.get()

    companion object {
        private const val NO_INTERNET_CONNECTION_MESSAGE = "No internet connection"
    }
}