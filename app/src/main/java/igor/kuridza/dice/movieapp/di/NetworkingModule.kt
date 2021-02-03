package igor.kuridza.dice.movieapp.di

import igor.kuridza.dice.movieapp.networking.AuthenticationService
import igor.kuridza.dice.movieapp.networking.MovieApiService
import igor.kuridza.dice.movieapp.networking.TvShowApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val AUTHORIZATION_NAME = "Authorization"
private const val BEARER_NAME = "Bearer"
private const val BEARER_TOKEN_VALUE = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNWRiOGU1NmMzNmZkYWFiYzMyMzJjZTQxZmVkYTZiMyIsInN1YiI6IjVjYWM1MTM1OTI1MTQxMTkxOGI0NjUzZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PeJA2pjw-pnda2NffCNE3exzBsMZwCvvWs19Olocgz8"
private const val CONTENT_TYPE_NAME = "Content-Type"
private const val CONTENT_TYPE_VALUE = "application/json;charset=utf-8"

val networkingModule = module {

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single {
        Interceptor{ chain ->
            val request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION_NAME,"$BEARER_NAME $BEARER_TOKEN_VALUE")
                .addHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE)
                .build()
            chain.proceed(request)
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .baseUrl(BASE_URL)
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(MovieApiService::class.java)
    }

    single {
        get<Retrofit>().create(TvShowApiService::class.java)
    }

    single {
        get<Retrofit>().create(AuthenticationService::class.java)
    }
}