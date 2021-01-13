package igor.kuridza.dice.movieapp.app

import android.app.Application
import igor.kuridza.dice.movieapp.di.networkingModule
import igor.kuridza.dice.movieapp.di.repositoryModule
import igor.kuridza.dice.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MovieApp)
            modules(listOf(
                networkingModule,
                viewModelModule,
                repositoryModule
            ))
        }
    }
}