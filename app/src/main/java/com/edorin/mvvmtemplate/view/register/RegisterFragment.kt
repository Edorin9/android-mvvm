package com.edorin.mvvmtemplate.view.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.edorin.mvvmtemplate.R
import com.edorin.mvvmtemplate.view.MainViewModel
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.sharedViewModel
import org.koin.androidx.viewmodel.ext.viewModel

class RegisterFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: RegisterViewModel by viewModel()

    /** Overrides **/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // event observer
        viewModel.event.observe(this, Observer { event ->
            when (event) {
                is RegisterEvent.Pending -> onPending()
                is RegisterEvent.RegisterSuccess -> onRegisterSuccess()
                is RegisterEvent.RegisterFailed -> onRegisterFailed(event.error)
            }
        })

        // back nav click
        register_button_back.setOnClickListener { findNavController().popBackStack() }

        // register click
        register_button_register.setOnClickListener {
            viewModel.register(
                email = register_field_email.text.toString(),
                password = register_field_password.text.toString()
            )
        }
    }

    /** OnEventTriggered Methods **/

    private fun onPending() {
        mainViewModel.startLoad()
    }

    private fun onRegisterSuccess() {
        mainViewModel.endLoad()
        findNavController().popBackStack()
    }

    private fun onRegisterFailed(error: Throwable) {
        toast(error.localizedMessage)
        mainViewModel.endLoad()
    }

}
