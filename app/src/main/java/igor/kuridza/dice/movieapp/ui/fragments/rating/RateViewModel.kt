package igor.kuridza.dice.movieapp.ui.fragments.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.repositories.auth.AuthenticationRepository
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RateViewModel(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private var rating = 0F

    private val _rateMessageResponse = MutableLiveData<Resource<MessageResponse>>()
    val rateMessageResponse: LiveData<Resource<MessageResponse>>
        get() = _rateMessageResponse

    fun rateMovie(movieId: Int, ratingValue: Number) {
        viewModelScope.launch(Dispatchers.IO) {
            val sessionId = authenticationRepository.getSessionId()
            movieRepository.rateMovie(movieId, sessionId, ratingValue).collect { data ->
                _rateMessageResponse.postValue(data)
            }
        }
    }

    fun rateTvShow(tvShowId: Int, ratingValue: Number) {
        viewModelScope.launch(Dispatchers.IO) {
            val sessionId = authenticationRepository.getSessionId()
            tvShowRepository.rateTvShow(tvShowId, sessionId, ratingValue).collect { data ->
                _rateMessageResponse.postValue(data)
            }
        }
    }

    fun changeRating(ratingValue: Float) {
        rating = ratingValue
    }

    fun getRating(): Float {
        return rating
    }
}