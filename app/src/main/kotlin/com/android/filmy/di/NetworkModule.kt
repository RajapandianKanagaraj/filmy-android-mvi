package com.android.filmy.di

import com.android.filmy.BuildConfig
import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.MediaContent
import com.android.filmy.model.response.Person
import com.android.filmy.model.response.Provider
import com.android.filmy.network.MovieApi
import com.android.filmy.network.PeopleApi
import com.android.filmy.network.ProvidersApi
import com.android.filmy.network.TvApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.Polymorphic
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val url = original.url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()
            val req = original.newBuilder().url(url).build()
            chain.proceed(req)
        }
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(FeedItemResponse::class.java, "media_type")
                    .withSubtype(MediaContent::class.java, "movie")
                    .withSubtype(MediaContent::class.java, "tv")
                    .withSubtype(Person::class.java, "person")
                    .withSubtype(Provider::class.java, "provider")
            )
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun providesTvShowsApi(retrofit: Retrofit): TvApi = retrofit.create(TvApi::class.java)

    @Provides
    @Singleton
    fun providesPeopleApi(retrofit: Retrofit): PeopleApi = retrofit.create(PeopleApi::class.java)

    @Provides
    @Singleton
    fun providesProvidersApi(retrofit: Retrofit): ProvidersApi = retrofit.create(ProvidersApi::class.java)
}