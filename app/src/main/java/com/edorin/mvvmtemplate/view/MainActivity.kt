package com.edorin.mvvmtemplate.view

import android.animation.LayoutTransition
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.edorin.litunyi.ext.showIndefiniteSnackbar
import com.edorin.mvvmtemplate.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val loader by lazy {
        AlertDialog.Builder(this).create().apply {
            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setView(layoutInflater.inflate(R.layout.dialog_loader, activity_main))
        }
    }

    /** Overrides **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // animate layout changes
        activity_main.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        // state observer
        viewModel.state.observe(this, Observer { state -> setState(state) })
    }

    override fun onPause() {
        super.onPause()
        loader.dismiss()
    }

    /** OnEventTriggered Methods **/

    private fun setState(newState: MainState) {
        with(newState) { loader.apply { if (isLoading) show() else dismiss() } }
    }

}
