package com.edorin.mvvmtemplate.view.details

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.edorin.mvvmtemplate.R
import com.edorin.mvvmtemplate.view.MainViewModel
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.sharedViewModel
import org.koin.androidx.viewmodel.ext.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

	private val args: DetailsFragmentArgs by navArgs()

	private val mainViewModel: MainViewModel by sharedViewModel()
	private val viewModel: DetailsViewModel by viewModel { parametersOf(args.id) }
	private val viewModelState: DetailsState? get() = viewModel.state.value

	/** Overrides **/

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_details, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// animate layout changes
		fragment_details.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

		// state observer
		viewModel.state.observe(this, Observer { state -> setState(state) })

		// event observer
		viewModel.event.observe(this, Observer { event ->
			when (event) {
				is DetailsEvent.Pending -> onPending()
				is DetailsEvent.LoadDetailsSuccess -> onLoadDetailsSuccess()
				is DetailsEvent.LoadDetailsFailed -> onLoadDetailsFailed(event.error)
			}
		})
	}

	override fun onResume() {
		super.onResume()
		viewModelState?.color ?: viewModel.loadColor()
	}

    /** OnEventTriggered Methods **/

	private fun setState(newState: DetailsState) {
		with(newState.color) {
			fragment_details.setBackgroundColor(value)
			details_text_color_name.text = name
			details_text_hex.text = hex
			details_text_pantone.text = pantoneValue
		}
	}

	private fun onPending() {
		mainViewModel.startLoad()
	}

	private fun onLoadDetailsSuccess() {
		mainViewModel.endLoad()
	}

	private fun onLoadDetailsFailed(error: Throwable) {
		toast(error.localizedMessage)
		mainViewModel.endLoad()
	}

}
