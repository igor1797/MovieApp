package igor.kuridza.dice.movieapp.ui.fragments.authentication

import android.content.Intent
import android.net.Uri
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.gone
import igor.kuridza.dice.movieapp.common.showToast
import igor.kuridza.dice.movieapp.common.visible
import igor.kuridza.dice.movieapp.databinding.AuthenticationFragmentBinding
import igor.kuridza.dice.movieapp.model.auth.GetRequestToken
import igor.kuridza.dice.movieapp.model.auth.GetSessionId
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel


class AuthenticationFragment : BaseFragment<AuthenticationFragmentBinding>() {

    private val authViewModel: AuthenticationViewModel by viewModel()

    override fun getLayoutResourceId(): Int = R.layout.authentication_fragment

    override fun setUpUi() {
        binding.viewModel = authViewModel
        subscribeToRequestTokenResponse()
        subscribeToLoginRequest()
        subscribeToSessionIdRequest()
        subscribeToLoginFormState()
        monitorsInputChanges()
        setLoginOnClickListener()
        setSkipLoginOnClickListener()
        setGoToRegisterOnClickListener()
    }

    private fun monitorsInputChanges() {
        binding.apply {
            passwordInput.addTextChangedListener {
                authViewModel.loginDataChanged(usernameInput.text.toString(), it.toString())
            }
            usernameInput.addTextChangedListener {
                authViewModel.loginDataChanged(it.toString(), passwordInput.text.toString())
            }
        }
    }

    private fun setGoToRegisterOnClickListener() {
        binding.goToRegister.setOnClickListener {
            openBrowserToRegister()
        }
    }

    private fun openBrowserToRegister() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/signup"))
        startActivity(browserIntent)
    }

    private fun setSkipLoginOnClickListener() {
        binding.skipLogin.setOnClickListener {
            goToHomeFragment()
            authViewModel.userSkippedLogin()
        }
    }

    private fun setLoginOnClickListener() {
        binding.login.setOnClickListener {
            binding.apply {
                usernameInput.clearFocus()
                passwordInput.clearFocus()
                loading.visible()
            }
            authViewModel.createRequestToken()
        }
    }

    private fun subscribeToLoginFormState() {
        authViewModel.loginFormState.observe(viewLifecycleOwner) { loginFormState ->
            binding.passwordLayout.error = loginFormState.passwordError?.let { getString(it) }
            binding.userNameTextLayout.error = loginFormState.usernameError?.let { getString(it) }
        }
    }

    private fun subscribeToRequestTokenResponse() {
        authViewModel.getRequestTokenResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success<GetRequestToken> -> handleSuccess(response.data)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun subscribeToLoginRequest() {
        authViewModel.loginRequest.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success<GetRequestToken> -> handleSuccessLogin(response.data)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun subscribeToSessionIdRequest() {
        authViewModel.sessionIdRequest.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success<GetSessionId> -> handleSuccessCreatedSessionId(response.data)
                is Error -> handleError(response.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun handleSuccessCreatedSessionId(getSessionId: GetSessionId) {
        if (getSessionId.success) {
            binding.loading.gone()
            authViewModel.saveValueInPrefs(getSessionId.session_id)
            goToHomeFragment()
        }
    }

    private fun goToHomeFragment() {
        findNavController().popBackStack()
    }

    private fun handleSuccessLogin(getRequestToken: GetRequestToken) {
        if (getRequestToken.success) {
            authViewModel.createSessionId(getRequestToken.request_token)
        }
    }

    private fun handleSuccess(getRequestToken: GetRequestToken) {
        val username = binding.usernameInput.text.toString()
        val password = binding.passwordInput.text.toString()
        authViewModel.login(username, password, getRequestToken.request_token)
    }

    private fun handleLoading() {
        binding.loading.visible()
    }

    private fun handleError(message: String?) {
        binding.loading.gone()
        showToast(getString(R.string.errorOccurredMessageText))
    }
}