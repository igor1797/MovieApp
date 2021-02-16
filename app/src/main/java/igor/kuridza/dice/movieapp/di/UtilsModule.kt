package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.common.Connectivity
import igor.kuridza.dice.movieapp.common.utils.resource.ResourceHelper
import igor.kuridza.dice.movieapp.common.utils.resource.ResourceHelperImpl
import igor.kuridza.dice.movieapp.common.utils.settings.SettingsManagerImpl
import igor.kuridza.dice.movieapp.common.utils.settings.SettingsManager
import org.koin.dsl.module

val utilsModule = module {
    single<SettingsManager> {
        SettingsManagerImpl()
    }

    single<ResourceHelper> {
        ResourceHelperImpl(get())
    }

    single {
        Connectivity(get())
    }
}