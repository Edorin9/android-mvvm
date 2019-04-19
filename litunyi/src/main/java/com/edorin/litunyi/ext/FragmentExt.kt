package com.edorin.litunyi.ext

import androidx.fragment.app.Fragment

/**
 * Get argument from bundle
 * @param key key of argument
 */
fun <T : Any> Fragment.argument(key: String) =
	lazy { arguments?.get(key) as? T ?: error("Intent Argument $key is missing") }

/**
 * Get argument from bundle
 * @param key key of argument
 * @param defaultValue default value for argument
 */
fun <T : Any> Fragment.argument(key: String, defaultValue: T? = null) =
	lazy { arguments?.get(key) as? T ?: defaultValue }
