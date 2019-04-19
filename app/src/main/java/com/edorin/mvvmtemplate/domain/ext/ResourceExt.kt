package com.edorin.mvvmtemplate.domain.ext

import com.edorin.mvvmtemplate.domain.entity.Color
import com.edorin.reqres.model.Resource

/**
 * Map Resource into Color
 */
fun Resource.toColorEntity() = Color(
    id = id,
    name = name,
    hex = color,
    pantoneValue = pantoneValue
)
