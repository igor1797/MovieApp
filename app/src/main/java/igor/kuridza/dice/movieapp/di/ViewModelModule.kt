package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.ui.fragments.authentication.AuthenticationViewModel
import igor.kuridza.dice.movieapp.ui.fragments.home.HomeViewModel
import igor.kuridza.dice.movieapp.ui.fragments.images.ImageViewModel
import igor.kuridza.dice.movieapp.ui.fragments.movie_details.MovieDetailsViewModel
import igor.kuridza.dice.movieapp.ui.fragments.movies.MoviesViewModel
import igor.kuridza.dice.movieapp.ui.fragments.rating.RateViewModel
import igor.kuridza.dice.movieapp.ui.fragments.search.movies.SearchMoviesViewModel
import igor.kuridza.dice.movieapp.ui.fragments.search.tv_shows.SearchTvShowsViewModel
import igor.kuridza.dice.movieapp.ui.fragments.tv_show_details.TvShowDetailsViewModel
import igor.kuridza.dice.movieapp.ui.fragments.tv_shows.TvShowsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MoviesViewModel(get(), get(), get())
    }

    viewModel {
        MovieDetailsViewModel(get(), get())
    }

    viewModel {
        TvShowsViewModel(get(), get())
    }

    viewModel {
        TvShowDetailsViewModel(get(), get())
    }

    viewModel {
        ImageViewModel(get(), get())
    }

    viewModel {
        SearchMoviesViewModel(get())
    }

    viewModel {
        SearchTvShowsViewModel(get())
    }

    viewModel {
        AuthenticationViewModel(get(), get(), get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        RateViewModel(get(), get(), get())
    }
}