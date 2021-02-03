package igor.kuridza.dice.movieapp.model.auth

data class AuthRequest(
    val username: String,
    val password: String,
    val request_token: String
)
