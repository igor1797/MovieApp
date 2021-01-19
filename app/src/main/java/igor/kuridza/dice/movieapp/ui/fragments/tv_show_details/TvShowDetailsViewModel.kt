package igor.kuridza.dice.movieapp.ui.fragments.tv_show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.movieapp.model.tv_show.TvShowDetails
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TvShowDetailsViewModel(
    private val tvShowRepository: TvShowRepository
) : ViewModel() {

    private val _tvShowDetails = MutableLiveData<Resource<TvShowDetails>>()
    val tvShowDetails: LiveData<Resource<TvShowDetails>>
        get() = _tvShowDetails

    fun getPrimaryInformationAboutTvShow(tvShowId: Int, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowRepository.getPrimaryTvShowDetailsById(tvShowId, language).collect { data ->
                _tvShowDetails.postValue(data)
            }
        }
    }
}