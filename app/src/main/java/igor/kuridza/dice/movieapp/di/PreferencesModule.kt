package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.prefs.SettingsPrefs
import igor.kuridza.dice.movieapp.prefs.SettingsPrefsImpl
import org.koin.dsl.module

val preferencesModule = module {

    single<SettingsPrefs> {
        SettingsPrefsImpl(get())
    }
}