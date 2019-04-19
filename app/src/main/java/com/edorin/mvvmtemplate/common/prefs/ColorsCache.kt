package com.edorin.mvvmtemplate.common.prefs

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref
import com.edorin.mvvmtemplate.domain.entity.Color

/**
 * App cache
 */
object ColorsCache : KotprefModel() {
    var token by stringPref()
    var colors by gsonPref(mutableListOf<Color>())
}