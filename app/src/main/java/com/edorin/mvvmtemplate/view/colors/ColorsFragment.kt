package com.edorin.mvvmtemplate.view.colors

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.edorin.litunyi.ext.setup
import com.edorin.mvvmtemplate.R
import com.edorin.mvvmtemplate.domain.entity.Color
import com.edorin.mvvmtemplate.view.MainViewModel
import com.edorin.mvvmtemplate.view.colors.adapter.ColorsAdapter
import com.pawegio.kandroid.e
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_colors.*
import org.koin.androidx.viewmodel.ext.sharedViewModel
import org.koin.androidx.viewmodel.ext.viewModel

class ColorsFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: ColorsViewModel by viewModel()
    private val viewModelState: ColorsState? get() = viewModel.state.value

    private lateinit var adapter: ColorsAdapter
    private val colorsList = mutableListOf<Color>()

    /** Overrides **/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_colors, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // animate layout changes
        fragment_colors.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        // event observer
        viewModel.event.observe(this, Observer { event ->
            when (event) {
                is ColorsEvent.Pending -> onPending()
                is ColorsEvent.LoadColorsSuccess -> onLoadColorsSuccess()
                is ColorsEvent.LoadColorsFailed -> onLoadColorsFailed(event.error)
            }
        })

        // state observer
        viewModel.state.observe(this, Observer { state -> setState(state) })

        // setup colorsList list
        adapter = ColorsAdapter(colorsList) { clickedColorId ->
            this@ColorsFragment.e("clickedColorId => $clickedColorId")
            viewModel.setTitleLastClicked(clickedColorId ?: 0)
            findNavController().navigate(
                ColorsFragmentDirections.colorsToDetails(clickedColorId ?: 0)
            )
        }
        colors_list_colors.setup(LinearLayoutManager(context), adapter)
    }

    override fun onResume() {
        super.onResume()
        viewModelState?.colors?.isNotEmpty() ?: viewModel.loadColors()
    }

    /** OnEventTriggered members **/

    private fun setState(newState: ColorsState) {
        with(newState) {
            colors_text_title.text = title
            colorsList.apply { clear(); addAll(colors) }
            adapter.notifyDataSetChanged()
        }
    }

    private fun onPending() {
        mainViewModel.startLoad()
    }

    private fun onLoadColorsSuccess() {
        mainViewModel.endLoad()
    }

    private fun onLoadColorsFailed(error: Throwable) {
        toast(error.localizedMessage)
        mainViewModel.endLoad()
    }

}
