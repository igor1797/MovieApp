package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.ui.fragments.images.ImageViewModel
import igor.kuridza.dice.movieapp.ui.fragments.movie_details.MovieDetailsViewModel
import igor.kuridza.dice.movieapp.ui.fragments.movies.MoviesViewModel
import igor.kuridza.dice.movieapp.ui.fragments.search.movies.SearchMoviesViewModel
import igor.kuridza.dice.movieapp.ui.fragments.search.tv_shows.SearchTvShowsViewModel
import igor.kuridza.dice.movieapp.ui.fragments.settings.SettingsViewModel
import igor.kuridza.dice.movieapp.ui.fragments.tv_show_details.TvShowDetailsViewModel
import igor.kuridza.dice.movieapp.ui.fragments.tv_shows.TvShowsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MoviesViewModel(get())
    }

    viewModel {
        MovieDetailsViewModel(get())
    }

    viewModel {
        TvShowsViewModel(get())
    }

    viewModel {
        TvShowDetailsViewModel(get())
    }

    viewModel {
        ImageViewModel(get(), get())
    }

    viewModel {
        SettingsViewModel()
    }

    viewModel {
        SearchMoviesViewModel(get())
    }

    viewModel {
        SearchTvShowsViewModel(get())
    }
}