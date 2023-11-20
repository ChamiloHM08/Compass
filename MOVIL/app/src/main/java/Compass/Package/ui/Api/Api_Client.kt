package Compass.Package.ui.Api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api_Client {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val tmdbApiService: TmdbApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TmdbApiService::class.java)
    }
}

