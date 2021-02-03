package igor.kuridza.dice.movieapp.ui.fragments.movies

import androidx.lifecycle.*
import igor.kuridza.dice.movieapp.common.POPULAR
import igor.kuridza.dice.movieapp.common.TOP_RATED
import igor.kuridza.dice.movieapp.common.UPCOMING
import igor.kuridza.dice.movieapp.model.movie.GetMoviesResponse
import igor.kuridza.dice.movieapp.model.resource.Resource
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import igor.kuridza.dice.movieapp.prefs.settings.SettingsPrefs
import igor.kuridza.dice.movieapp.utils.resource.ResourceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val settingsPrefs: SettingsPrefs,
    private val resourceHelper: ResourceHelper
): ViewModel() {

    private val categories = mapOf(
        TOP_RATED to resourceHelper.topRatedString(),
        POPULAR to resourceHelper.popularString(),
        UPCOMING to resourceHelper.upcomingString()
    )

    fun getCategoryNameByKey(category: String): String {
        return categories[category].toString()
    }

    private val _category = MutableLiveData(TOP_RATED)
    val category: LiveData<String>
        get() = _category

    private val mMovies = MutableLiveData<Resource<GetMoviesResponse>>()
    val movies: LiveData<Resource<GetMoviesResponse>>
        get() = mMovies

    fun getMoviesByType(type: String, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMoviesByType(type, language).collect { data ->
                mMovies.postValue(data)
            }
        }
    }

    fun changeCategory(movieCategory: String) {
        _category.value = movieCategory
    }

    fun getLanguage(): String {
        return settingsPrefs.getLanguage()
    }
}