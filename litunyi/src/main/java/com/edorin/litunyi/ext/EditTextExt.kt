package com.edorin.litunyi.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Check if blank
 */
fun EditText.isBlank(): Boolean = text.toString().trim() == ""

/**
 * Add text change listener
 * @param onBeforeTextChanged callback on beforeTextChanged
 * @param onTextChanged callback on onTextChanged
 * @param onAfterTextChanged callback on afterTextChanged
 */
fun EditText.onTextChangeListener(
    onBeforeTextChanged: ((String) -> Unit)? = null,
    onTextChanged: ((String) -> Unit)? = null,
    onAfterTextChanged: ((String) -> Unit)? = null) {

	addTextChangedListener(object : TextWatcher {
		override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
			onBeforeTextChanged?.invoke(p0.toString())
		}
		override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
			onTextChanged?.invoke(p0.toString())
		}
		override fun afterTextChanged(editable: Editable?) {
			onAfterTextChanged?.invoke(editable.toString())
		}
	})
}
