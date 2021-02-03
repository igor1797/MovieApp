package igor.kuridza.dice.movieapp.networking

import igor.kuridza.dice.movieapp.model.auth.AuthRequest
import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.auth.RequestToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {

    @GET("authentication/token/new")
    suspend fun createRequestToken(): Response<GetRequestToken>

    @POST("authentication/token/validate_with_login")
    suspend fun validateRequestTokenWithUserNameAndPassword(
        @Body authRequest: AuthRequest
    ): Response<GetRequestToken>

    @POST("authentication/session/new")
    suspend fun createNewSessionId(
        @Body requestToken: RequestToken
    ): Response<GetSessionId>
}