package igor.kuridza.dice.movieapp.repositories.auth

import igor.kuridza.dice.movieapp.common.makeNetworkRequest
import igor.kuridza.dice.movieapp.model.auth.AuthRequest
import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.auth.RequestToken
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.networking.AuthenticationService
import kotlinx.coroutines.flow.Flow

class AuthenticationRepositoryImpl(
    private val authenticationService: AuthenticationService
) : AuthenticationRepository {

    override fun createRequestToken(): Flow<Resource<GetRequestToken>> =
        makeNetworkRequest { authenticationService.createRequestToken() }

    override fun login(
        username: String,
        password: String,
        requestToken: String
    ): Flow<Resource<GetRequestToken>> =
        makeNetworkRequest {
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
}