package com.s2.made.mycatalog15

import android.app.Application
import com.s2.made.core.di.CoreModule.databaseModule
import com.s2.made.core.di.CoreModule.networkModule
import com.s2.made.core.di.CoreModule.repositoryModule
import com.s2.made.mycatalog15.di.AppModule.useCaseModule
import com.s2.made.mycatalog15.di.AppModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}