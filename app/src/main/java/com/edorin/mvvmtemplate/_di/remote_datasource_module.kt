package com.edorin.mvvmtemplate._di

import com.edorin.reqres.ReqresDatasource
import com.edorin.litunyi.helpers.WebService
import org.koin.dsl.module

/**
 * Remote Web Service datasource
 */
val remoteDatasourceModule = module {
	// ReqresDatasource
	single {
		WebService.create<ReqresDatasource>(
			ReqresDatasource.baseUrl,
			Pair("Content-type", "application/json")
		)
	}
}
