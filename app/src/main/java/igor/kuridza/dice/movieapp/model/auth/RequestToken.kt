package igor.kuridza.dice.movieapp.model.auth

import com.google.gson.annotations.SerializedName

data class RequestToken(
    @SerializedName("request_token")
    val token: String
)
