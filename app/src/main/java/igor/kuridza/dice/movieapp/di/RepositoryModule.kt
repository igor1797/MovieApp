package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.repositories.movie.MovieRepository
import igor.kuridza.dice.movieapp.repositories.movie.MovieRepositoryImpl
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepository
import igor.kuridza.dice.movieapp.repositories.tv_show.TvShowRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    single<TvShowRepository> {
        TvShowRepositoryImpl(get())
    }
}