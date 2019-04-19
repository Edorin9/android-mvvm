package com.edorin.reqres.model.getcolors

import com.edorin.reqres.model.Resource
import com.google.gson.annotations.SerializedName
import java.util.Collections.emptyList

data class GetResourcesResponse(
	val page: Int?,
	@SerializedName("per_page") val perPage: Int?,
	val total: Int?,
	@SerializedName("total_pages") val totalPages: Int?,
	val data: List<Resource> = emptyList())

