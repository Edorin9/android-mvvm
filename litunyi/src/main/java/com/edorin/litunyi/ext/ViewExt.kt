package com.edorin.litunyi.ext

import android.os.SystemClock
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Set double tap listener
 * @param onDoubleTap callback when double tapped
 */
fun View.setOnDoubleTapListener(onDoubleTap: () -> Unit) {
    val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            onDoubleTap.invoke()
            return true
        }
    })
    setOnTouchListener { _, event -> return@setOnTouchListener gestureDetector.onTouchEvent(event) }
}

/**
 * Set safe click listener
 * @param onSafeClick callback when clicked
 */
fun View.setSafeOnClickListener(onSafeClick: (View?) -> Unit) {
    val safeClickListener = OnSafeClick(1000) { onSafeClick(it) }
    setOnClickListener(safeClickListener)
}

/**
 * set alpha touch feedback
 */
fun View.setAlphaTouchFeedback() {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.alpha = 0.5f
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                v.alpha = 1f
            }
        }
        false
    }
}

class OnSafeClick(private var defaultInterval: Int, private val onSafeClick: (View?) -> Unit) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(view: View?) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) return
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeClick(view)
    }
}

