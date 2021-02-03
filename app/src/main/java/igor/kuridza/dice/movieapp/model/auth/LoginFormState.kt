package igor.kuridza.dice.movieapp.model.auth

data class LoginFormState(
    var usernameError: Int? = null,
    var passwordError: Int? = null,
    var isDataValid: Boolean = false
)
