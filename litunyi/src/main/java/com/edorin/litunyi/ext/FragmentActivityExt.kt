package com.edorin.litunyi.ext

import androidx.fragment.app.FragmentActivity

/**
 * Get argument from intent bundle
 * @param key key of argument
 */
fun <T : Any> FragmentActivity.argument(key: String) =
    lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }

/**
 * Get argument from intent bundle
 * @param key key of argument
 * @param defaultValue default value for argument
 */
fun <T : Any> FragmentActivity.argument(key: String, defaultValue: T? = null) =
    lazy { intent.extras[key] as? T ?: defaultValue }
