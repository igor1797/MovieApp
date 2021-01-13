package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.ui.fragments.movies.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MoviesViewModel(get())
    }
}