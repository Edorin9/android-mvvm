package com.edorin.litunyi

import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import org.jetbrains.anko.bundleOf

/**
 * Do nothing. Just a clean version of {} or Unit()
 **/
fun doNothing() = Unit

/**
 * Empty string
 */
fun emptyString() = String()

/**
 * Set [views] visibility to [View.VISIBLE]
 * @param views views to set visibility
 */
fun showViews(vararg views: View) {
    views.forEach { view -> view.show() }
}

/**
 * Set [views] visibility to [View.GONE]
 * @param views views to set visibility
 */
fun detachViews(vararg views: View) {
    views.forEach { view -> view.hide(gone = true) }
}

/**
 * Set [views] visibility to [View.INVISIBLE]
 * @param views views to set visibility
 */
fun hideViews(vararg views: View) {
    views.forEach { view -> view.hide(gone = false) }
}

/**
 * Set [font] to views
 * @param font [Typeface] to set
 * @param views views to set [font] to
 */
fun setFontToViews(font: Typeface?, vararg views: TextView) {
    views.forEach { view -> view.typeface = font }
}

/**
 * Override fonts of all texts in a view
 * @param view view to set font on
 * @param withFont font to use
 */
fun overrideFonts(view: View, withFont: Typeface) {
    try {
        if (view is ViewGroup) {
            (0 until view.childCount)
                .map { view.getChildAt(it) }
                .forEach { overrideFonts(it, withFont) }
        } else if (view is TextView) {
            view.typeface = withFont
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Set alpha touch feedback to [views]
 * @param views views to set visual feedback to
 */
fun setAlphaTouchFeedbackToViews(vararg views: View) {
    views.forEach { view ->
        view.apply {
            isClickable = true
            isFocusable = true
            isFocusableInTouchMode = true
            setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val anim = AlphaAnimation(1f, 0.5f)
                        anim.fillAfter = true
                        anim.duration = 50
                        v.startAnimation(anim)
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        val anim = AlphaAnimation(0.5f, 1f)
                        anim.fillAfter = true
                        anim.duration = 200
                        v.startAnimation(anim)
                    }
                }
                false
            }
        }
    }
}

/**
 * Create bundle [arguments] from key-value pairs
 * @param arguments key-value [Pair]<[String], [Any]?> arguments
 */
fun args(vararg arguments: Pair<String, Any?>) = bundleOf(*arguments)