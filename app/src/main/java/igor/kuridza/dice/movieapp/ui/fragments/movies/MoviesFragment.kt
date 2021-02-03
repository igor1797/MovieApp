package igor.kuridza.dice.movieapp.ui.fragments.movies

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.MoviesFragmentBinding
import igor.kuridza.dice.movieapp.model.DetailsItemType
import igor.kuridza.dice.movieapp.model.movie.Movie
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.activities.MainActivity
import igor.kuridza.dice.movieapp.ui.adapters.movie.MovieAdapter
import igor.kuridza.dice.movieapp.ui.adapters.movie.MovieClickListener
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.movieapp.ui.fragments.home.HomeFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<MoviesFragmentBinding>(), MovieClickListener {

    private val viewModel: MoviesViewModel by viewModel()
    private val movieAdapter = MovieAdapter(this, DetailsItemType)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback

    override fun getLayoutResourceId(): Int = R.layout.movies_fragment

    override fun setUpUi() {
        setUpRecycler()
        observeMovies()
        initBehavior()
        initBottomSheetCallback()
        initListeners()
        addBottomSheetCallback()
        observeSelectedCategory()
    }

    private fun initListeners() {
        setApplyButtonOnClickListener()
        setBottomSheetItemsOnClickListener()
        setBackgroundBehindBottomSheetOnClickListener()
        setToolbarIconsOnClickListener()
    }

    private fun observeSelectedCategory() {
        viewModel.category.observe(viewLifecycleOwner) { category ->
            val language = viewModel.getLanguage()
            getMoviesByType(category, language)
            binding.toolbar.title = viewModel.getCategoryNameByKey(category)
            setCategoryChecked(category)
        }
    }

    private fun setCategoryChecked(category: String) {
        when (category) {
            TOP_RATED -> binding.topRated.isSelected = true
            POPULAR -> binding.popular.isSelected = true
            UPCOMING -> binding.upcoming.isSelected = true
        }
    }

    private fun setUpRecycler() {
        binding.moviesRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

    private fun setToolbarIconsOnClickListener() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter -> showMovieCategoryPicker()
                R.id.search -> showSearchMoviesFragment()
                else -> false
            }
        }
    }

    private fun showMovieCategoryPicker(): Boolean {
        expandBottomSheet()
        return true
    }

    private fun showSearchMoviesFragment(): Boolean {
        val controller =
            Navigation.findNavController(activity as MainActivity, R.id.mainNavHostFragment)
        controller.navigate(R.id.goToSearchMoviesFragment)
        return true
    }

    private fun getMoviesByType(movieType: String, language: String) {
        viewModel.getMoviesByType(movieType, language)
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Loading -> handleLoading()
                is Error -> handleError(response.message)
                is Success -> handleSuccess(response.data.movies)
            }
        }
    }

    private fun handleError(message: String?){
        //To do
    }

    private fun handleLoading() {
        binding.apply {
            loading.visible()
            loadingBackground.visible()
        }
    }

    private fun handleSuccess(movies: List<Movie>) {
        movieAdapter.submitList(movies)
        binding.apply {
            loading.gone()
            loadingBackground.gone()
        }
    }

    private fun setApplyButtonOnClickListener() {
        binding.apply.setOnClickListener {
            applyChanges()
            hideBottomSheet()
        }
    }

    private fun applyChanges() {
        val checkedId = binding.categoryGroup.checkedRadioButtonId
        when (checkedId) {
            R.id.popular -> changeCategory(POPULAR)
            R.id.topRated -> changeCategory(TOP_RATED)
            R.id.upcoming -> changeCategory(UPCOMING)
        }
    }

    private fun changeCategory(movieCategory: String) {
        viewModel.changeCategory(movieCategory)
    }

    private fun initBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.chooseMovieCategoryBottomSheet)
    }

    private fun initBottomSheetCallback() {
        bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                setBackgroundBehindBottomSheetByNewBottomSheetState(newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                changeAlphaOfBackgroundBehindBottomSheet(slideOffset)
            }
        }
    }

    private fun setBackgroundBehindBottomSheetOnClickListener() {
        binding.backgroundBehindBottomSheet.setOnClickListener {
            hideBottomSheet()
        }
    }

    private fun setBottomSheetItemsOnClickListener() {
        binding.bottomSheetHeader.setOnClickListener {
            hideBottomSheet()
        }
    }

    private fun addBottomSheetCallback() {
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    private fun removeBottomSheetCallback() {
        bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallback)
    }

    private fun setBackgroundBehindBottomSheetByNewBottomSheetState(newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_HIDDEN -> hideBackgroundBehindBottomSheet()
            BottomSheetBehavior.STATE_EXPANDED -> showBackgroundBehindBottomSheet()
            else -> showBackgroundBehindBottomSheet()
        }
    }

    private fun showBackgroundBehindBottomSheet() {
        binding.backgroundBehindBottomSheet.visible()
    }

    private fun hideBackgroundBehindBottomSheet() {
        binding.backgroundBehindBottomSheet.gone()
    }

    private fun changeAlphaOfBackgroundBehindBottomSheet(slideOffset: Float) {
        binding.backgroundBehindBottomSheet.alpha = slideOffset - 0.2F
    }

    private fun expandBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onMovieClickListener(movie: Movie, moviePosterImage: ImageView) {
        val posterPath: String? = movie.posterPath
        val extras =
            if (posterPath.isNullOrEmpty()) {
                FragmentNavigatorExtras(moviePosterImage to "EmptyOrNullImagePath")
            } else {
                FragmentNavigatorExtras(moviePosterImage to posterPath)
            }
        val direction =
            HomeFragmentDirections.goToMovieDetailsFragment(movie.id, movie.posterPath ?: "")
        val controller =
            Navigation.findNavController(activity as MainActivity, R.id.mainNavHostFragment)
        controller.navigate(direction, extras)
    }

    override fun onDestroyView() {
        removeBottomSheetCallback()
        super.onDestroyView()
    }
}