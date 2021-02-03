package igor.kuridza.dice.movieapp.model.auth

data class GetRequestToken(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)
