package Compass.Package.ui.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String = "es"
    ): Call<MovieResponse>
    @GET("movie/{movie_id}/watch/providers")
    fun getWatchProviders(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<WatchProvidersResponse>

    data class MovieResponse(
        val results: List<Movies>
    )

    data class Movies(
        val id: Int,
        val title: String,
        val overview: String,
        val release_date: String,
        val popularity: Double,
        val vote_average: Double,
        val vote_count: Int,
        val poster_path: String,
        val backdrop_path: String,
        val genre_ids: List<Int>,
        val original_language: String,
        val video: Boolean,
        val adult: Boolean,
        var streamingPlatforms: String? = null // Nueva propiedad para almacenar la información de las plataformas de streaming
        // Otros campos que desees obtener
    )

    data class WatchProvidersResponse(
        val results: Map<String, WatchProviderRegion>
    )

    data class WatchProviderRegion(
        val link: String?,
        val buy: List<WatchProvider>?,
        val rent: List<WatchProvider>?,
        val flatrate: List<WatchProvider>?
    )

    data class WatchProvider(
        val provider_id: Int,
        val provider_name: String,
        val logo_path: String?
    )
}