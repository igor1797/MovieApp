package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.ui.authentication.presentation.AuthenticationViewModel
import igor.kuridza.dice.movieapp.ui.home.fragment.presentation.HomeViewModel
import igor.kuridza.dice.movieapp.ui.poster_images.presentation.ImageViewModel
import igor.kuridza.dice.movieapp.ui.movie_details.presentation.MovieDetailsViewModel
import igor.kuridza.dice.movieapp.ui.movies.list.presentation.MoviesViewModel
import igor.kuridza.dice.movieapp.ui.rating.presentation.RateViewModel
import igor.kuridza.dice.movieapp.ui.movies.search.presentation.SearchMoviesViewModel
import igor.kuridza.dice.movieapp.ui.tv_shows.search.presentation.SearchTvShowsViewModel
import igor.kuridza.dice.movieapp.ui.tv_show_details.presentation.TvShowDetailsViewModel
import igor.kuridza.dice.movieapp.ui.tv_shows.list.presentation.TvShowsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MoviesViewModel(get())
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
        AuthenticationViewModel(get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        RateViewModel(get(), get(), get())
    }
}