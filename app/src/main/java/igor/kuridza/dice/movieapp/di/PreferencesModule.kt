package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.prefs.settings.SettingsPrefs
import igor.kuridza.dice.movieapp.prefs.settings.SettingsPrefsImpl
import igor.kuridza.dice.movieapp.prefs.user.UserPrefs
import igor.kuridza.dice.movieapp.prefs.user.UserPrefsImpl
import org.koin.dsl.module

val preferencesModule = module {

    single<SettingsPrefs> {
        SettingsPrefsImpl(get())
    }

    single<UserPrefs> {
        UserPrefsImpl(get())
    }
}