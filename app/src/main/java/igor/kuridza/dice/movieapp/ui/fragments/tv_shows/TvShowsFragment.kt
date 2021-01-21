package igor.kuridza.dice.movieapp.ui.fragments.tv_shows

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.*
import igor.kuridza.dice.movieapp.databinding.TvShowsFragmentBinding
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.model.tv_show.TvShow
import igor.kuridza.dice.movieapp.ui.activities.MainActivity
import igor.kuridza.dice.movieapp.ui.adapters.TvShowAdapter
import igor.kuridza.dice.movieapp.ui.adapters.TvShowClickListener
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.movieapp.ui.fragments.home.HomeFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowsFragment : BaseFragment<TvShowsFragmentBinding>(), TvShowClickListener {

    private val viewModel: TvShowsViewModel by viewModel()
    private val tvShowAdapter = TvShowAdapter(this)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback

    override fun getLayoutResourceId(): Int = R.layout.tv_shows_fragment

    override fun setUpUi() {
        setUpRecycler()
        observeTvShows()

        initBehavior()
        initBottomSheetCallback()
        setBackgroundBehindBottomSheetOnClickListener()
        setBottomSheetItemsOnClickListener()
        addBottomSheetCallback()
        setApplyButtonOnClickListener()
        observeSelectedCategory()
        setFilterIconOnClickListener()
    }

    private fun observeSelectedCategory() {
        viewModel.category.observe(viewLifecycleOwner) { category ->
            getTvShowsByType(category, DEFAULT_LANGUAGE)
            binding.toolbar.title = viewModel.getCategoryNameByKey(category)
            setCategoryChecked(category)
        }
    }

    private fun setCategoryChecked(category: String) {
        when (category) {
            TOP_RATED -> binding.topRated.isSelected = true
            POPULAR -> binding.popular.isSelected = true
            UPCOMING -> binding.onTheAir.isSelected = true
        }
    }

    private fun setUpRecycler() {
        binding.tvShowsRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = tvShowAdapter
        }
    }

    private fun setFilterIconOnClickListener() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter -> showMovieCategoryPicker()
                else -> true
            }
        }
    }

    private fun showMovieCategoryPicker(): Boolean {
        expandBottomSheet()
        return true
    }

    private fun getTvShowsByType(tvShowsType: String, language: String) {
        viewModel.getTvShowsByType(tvShowsType, language)
    }

    private fun observeTvShows() {
        viewModel.tvShows.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Loading -> handleLoading()
                is Error -> handleError(response.message)
                is Success -> handleSuccess(response.data.tvShowList)
            }
        }
    }

    private fun handleError(message: String?) {
        //To do
    }

    private fun handleLoading() {
        binding.apply {
            loading.visible()
            loadingBackground.visible()
        }
    }

    private fun handleSuccess(movies: List<TvShow>) {
        tvShowAdapter.submitList(movies)
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
            R.id.onTheAir -> changeCategory(ON_THE_AIR)
        }
    }

    private fun changeCategory(movieCategory: String) {
        viewModel.changeCategory(movieCategory)
    }

    private fun initBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.chooseTvShowCategoryBottomSheet)
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

    override fun onTvShowClickListener(tvShow: TvShow, tvShowPosterImage: ImageView) {
        val extras = FragmentNavigatorExtras(
            tvShowPosterImage to tvShow.posterPath!!
        )

        val direction =
            HomeFragmentDirections.goToTvShowDetailsFragment(tvShow.id, tvShow.posterPath)
        val controller =
            Navigation.findNavController(activity as MainActivity, R.id.mainNavHostFragment)
        controller.navigate(direction, extras)
    }

    override fun onDestroyView() {
        removeBottomSheetCallback()
        super.onDestroyView()
    }
}