package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.utils.ResourceHelper
import igor.kuridza.dice.movieapp.utils.ResourceHelperImpl
import igor.kuridza.dice.movieapp.utils.SettingManagerImpl
import igor.kuridza.dice.movieapp.utils.SettingsManager
import org.koin.dsl.module

val utilsModule = module {
    single<SettingsManager> {
        SettingManagerImpl()
    }

    single<ResourceHelper> {
        ResourceHelperImpl(get())
    }
}