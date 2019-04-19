package com.edorin.mvvmtemplate.domain.entity

import android.graphics.Color
import java.io.Serializable

data class Color(
	val id: Int?,
	val name: String?,
	val hex: String?,
	val pantoneValue: String?
) : Serializable {

	val value: Int
		get() = Color.parseColor(hex)

}