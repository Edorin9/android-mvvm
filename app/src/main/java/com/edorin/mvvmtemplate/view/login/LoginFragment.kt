package com.edorin.mvvmtemplate.view.login

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
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.sharedViewModel
import org.koin.androidx.viewmodel.ext.viewModel

class LoginFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: LoginViewModel by viewModel()

    /** Overrides **/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // event observer
        viewModel.event.observe(this, Observer { event ->
            when (event) {
                is LoginEvent.Pending -> onPending()
                is LoginEvent.LoginSuccess -> onLoginSuccess()
                is LoginEvent.LoginFailed -> onLoginFailed(event.error)
            }
        })

        // login click
        login_button_login.setOnClickListener {
            viewModel.login(
                email = login_field_email.text.toString(),
                password = login_field_password.text.toString()
            )
        }

        // nav to register click
        login_button_register.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.loginToRegister())
        }

    }

    /** OnEventTriggered Methods **/

    private fun onPending() {
        mainViewModel.startLoad()
    }

    private fun onLoginSuccess() {
        mainViewModel.endLoad()
        findNavController().navigate(LoginFragmentDirections.loginToColors())
    }

    private fun onLoginFailed(error: Throwable) {
        toast(error.localizedMessage)
        mainViewModel.endLoad()
    }

}
