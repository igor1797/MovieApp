package igor.kuridza.dice.movieapp.ui.fragments.images

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.TV_SHOW
import igor.kuridza.dice.movieapp.databinding.ImageFragmentBinding
import igor.kuridza.dice.movieapp.model.image.Image
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import igor.kuridza.dice.movieapp.ui.adapters.ImageViewPagerAdapter
import igor.kuridza.dice.movieapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ImageFragment : BaseFragment<ImageFragmentBinding>() {

    private val viewModel: ImageViewModel by viewModel()
    private val imageViewPagerAdapter by lazy { ImageViewPagerAdapter() }
    private val args: ImageFragmentArgs by navArgs()
    private lateinit var imagePageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun getLayoutResourceId(): Int = R.layout.image_fragment

    override fun setUpUi() {
        setUpViewPager()
        getImages(args.id, args.type)
        observeImages()
        setBackIconOnClickListener()
    }

    private fun getImages(movieId: Int, type: String) {
        viewModel.getImages(movieId, type)
    }

    private fun setBackIconOnClickListener() {
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeImages() {
        viewModel.images.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Error -> handleError(response.message)
                is Success -> handleSuccess(response.data.posters)
                Loading -> handleLoading()
            }
        }
    }

    private fun setUpViewPager() {
        initPageChangeCallback()
        binding.imageViewPager.apply {
            adapter = imageViewPagerAdapter
            registerOnPageChangeCallback(imagePageChangeCallback)
        }

    }

    private fun initPageChangeCallback() {
        imagePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.imageNumber.text = "${position + 1}/${imageViewPagerAdapter.itemCount}"
            }
        }
    }


    private fun handleError(message: String?) {
        //To do
    }

    private fun handleLoading() {
        //To do
    }

    private fun handleSuccess(images: List<Image>) {
        imageViewPagerAdapter.setImages(images)
        binding.imageNumber.text = "1/${imageViewPagerAdapter.itemCount}"
    }

    override fun onDestroyView() {
        binding.imageViewPager.unregisterOnPageChangeCallback(imagePageChangeCallback)
        super.onDestroyView()
    }
}