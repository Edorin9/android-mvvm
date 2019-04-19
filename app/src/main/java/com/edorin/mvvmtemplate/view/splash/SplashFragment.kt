package com.edorin.mvvmtemplate.view.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.edorin.mvvmtemplate.R
import com.pawegio.kandroid.runDelayed
import org.koin.androidx.viewmodel.ext.viewModel

class SplashFragment : Fragment() {

	private val viewModel: SplashViewModel by viewModel()

	/** Overrides **/

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_splash, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// event observer
		viewModel.event.observe(this, Observer { state ->
			when (state) {
				is SplashEvent.Authenticated -> onAuthenticated()
				is SplashEvent.Unauthenticated -> onUnauthenticated()
			}
		})
	}

	override fun onResume() {
		super.onResume()
		runDelayed(900) { viewModel.mockLoad() }
	}

	/** OnEventTriggered Methods **/

	private fun onAuthenticated() {
		findNavController().navigate(SplashFragmentDirections.splashToColors())
	}

	private fun onUnauthenticated() {
		findNavController().navigate(SplashFragmentDirections.splashToLogin())
	}

}
