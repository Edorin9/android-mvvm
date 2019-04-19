package com.edorin.mvvmtemplate

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.edorin.mvvmtemplate._di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // KotPref
        Kotpref.init(this)

        // Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(appModules)
        }

    }

}
