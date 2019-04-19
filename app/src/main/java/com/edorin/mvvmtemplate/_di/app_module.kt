package com.edorin.mvvmtemplate._di

import com.edorin.litunyi.coroutines.AppSchedulerProvider
import com.edorin.mvvmtemplate.domain.ColorsRepository
import com.edorin.mvvmtemplate.domain.ColorsRepositoryImpl
import com.edorin.litunyi.coroutines.SchedulerProvider
import com.edorin.mvvmtemplate.view.MainViewModel
import com.edorin.mvvmtemplate.view.colors.ColorsViewModel
import com.edorin.mvvmtemplate.view.details.DetailsViewModel
import com.edorin.mvvmtemplate.view.login.LoginViewModel
import com.edorin.mvvmtemplate.view.register.RegisterViewModel
import com.edorin.mvvmtemplate.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** App Components for Dependency Injection **/

val mainModule = module {
    viewModel { MainViewModel() }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ColorsViewModel(get(), get()) }
    viewModel { (colorId: Int) ->
        DetailsViewModel(colorId, get(), get())
    }

    // ColorsRepository
    single<ColorsRepository>(createdAtStart = true) { ColorsRepositoryImpl(get()) }

    // SchedulerProvider
    single<SchedulerProvider>(createdAtStart = true) { AppSchedulerProvider() }
}

val appModules = listOf(mainModule, remoteDatasourceModule)