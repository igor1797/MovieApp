package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.utils.resource.ResourceHelper
import igor.kuridza.dice.movieapp.utils.resource.ResourceHelperImpl
import igor.kuridza.dice.movieapp.utils.settings.SettingsManagerImpl
import igor.kuridza.dice.movieapp.utils.settings.SettingsManager
import org.koin.dsl.module

val utilsModule = module {
    single<SettingsManager> {
        SettingsManagerImpl()
    }

    single<ResourceHelper> {
        ResourceHelperImpl(get())
    }
}