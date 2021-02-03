package igor.kuridza.dice.movieapp.ui.fragments.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import igor.kuridza.dice.movieapp.R
import igor.kuridza.dice.movieapp.common.MOVIE_TYPE
import igor.kuridza.dice.movieapp.common.gone
import igor.kuridza.dice.movieapp.common.showToast
import igor.kuridza.dice.movieapp.common.visible
import igor.kuridza.dice.movieapp.databinding.RateDialogFragmentBinding
import igor.kuridza.dice.movieapp.model.message.MessageResponse
import igor.kuridza.dice.movieapp.model.resource.Error
import igor.kuridza.dice.movieapp.model.resource.Loading
import igor.kuridza.dice.movieapp.model.resource.Success
import org.koin.android.viewmodel.ext.android.viewModel

class RateDialogFragment : DialogFragment() {

    private val viewModel: RateViewModel by viewModel()
    private lateinit var binding: RateDialogFragmentBinding
    private val args: RateDialogFragmentArgs by navArgs()
    private var rateListener: RateListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.rate_dialog_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCancelBtnOnClickListener()
        setRateStarOnClickListener()
        setRatingStarsOnRatingChangeListener()
        observeRateMessageResponse()
    }

    private fun setCancelBtnOnClickListener() {
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setRateStarOnClickListener() {
        binding.rate.setOnClickListener {
            val rating = (binding.ratingStars.rating) * 2
            viewModel.apply {
                changeRating(rating)
                if (args.type == MOVIE_TYPE)
                    rateMovie(args.id, rating as Number)
                else
                    rateTvShow(args.id, rating as Number)
            }
        }
    }

    private fun setRatingStarsOnRatingChangeListener() {
        binding.ratingStars.setOnRatingBarChangeListener { _, rating, _ ->
            setRatingText(rating)
        }
    }

    private fun setRatingText(rating: Float) {
        binding.rating.text = getString(R.string.ratingText, (rating * 2).toInt(), 10)
    }

    private fun observeRateMessageResponse() {
        viewModel.rateMessageResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Success<MessageResponse> -> handleSuccess(it.data)
                is Error -> handleError(it.message)
                Loading -> handleLoading()
            }
        }
    }

    private fun handleSuccess(response: MessageResponse) {
        if (response.status_code == 1)
            showToast(getString(R.string.successMessageText))
        else if (response.status_code == 12)
            showToast(getString(R.string.successfullyUpdatedRatingMessageText))
        binding.loading.gone()
        rateListener?.onRatingChange(viewModel.getRating())
        findNavController().popBackStack()
    }

    private fun handleError(message: String?) {}

    private fun handleLoading() {
        binding.loading.visible()
    }
}