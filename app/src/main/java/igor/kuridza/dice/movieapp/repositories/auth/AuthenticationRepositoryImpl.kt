package igor.kuridza.dice.movieapp.repositories.auth

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.auth.AuthRequest
import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.auth.RequestToken
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.networking.AuthenticationService
import igor.kuridza.dice.movieapp.prefs.user.UserPrefs
import igor.kuridza.dice.movieapp.utils.resource.ResourceHelper
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val authenticationService: AuthenticationService,
    private val userPrefs: UserPrefs,
    private val resourceHelper: ResourceHelper
) : AuthenticationRepository {

    override fun createRequestToken(): Flow<Resource<GetRequestToken>> =
        makeNetworkRequest { authenticationService.createRequestToken() }

    override fun login(
        username: String,
        password: String,
        requestToken: String
    ): Flow<Resource<GetRequestToken>> = makeNetworkRequest {
        authenticationService.validateRequestTokenWithUserNameAndPassword(
            AuthRequest(
                username,
                password,
                requestToken
            )
        )
    }

    override fun createSessionId(requestToken: String): Flow<Resource<GetSessionId>> =
        makeNetworkRequest { authenticationService.createNewSessionId(RequestToken(requestToken)) }

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
}