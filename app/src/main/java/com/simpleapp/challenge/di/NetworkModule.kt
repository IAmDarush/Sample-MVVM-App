package com.simpleapp.challenge.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simpleapp.challenge.BuildConfig
import com.simpleapp.challenge.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val BASE_URL: String = "https://root/"
  private const val HTTP_WRITE_TIMEOUT = 20
  private const val HTTP_READ_TIMEOUT = 20
  private const val HTTP_CONNECT_TIMEOUT = 20

  @Provides
  @Singleton
  fun provideCache(application: Application): Cache =
    Cache(application.cacheDir, 20L * 1024 * 1024) // 20 mb

  @Provides
  @Singleton
  fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level =
      if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
      else HttpLoggingInterceptor.Level.NONE
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    cache: Cache,
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient =
    OkHttpClient.Builder()
      .connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
      .writeTimeout(HTTP_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
      .readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
      .cache(cache)
      .addInterceptor(httpLoggingInterceptor)
      .build()

  @Provides
  @Singleton
  fun provideGsonBuilder(): Gson {
    return GsonBuilder()
      .create()
  }

  @Provides
  @Singleton
  fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
    GsonConverterFactory.create(gson)

  @Provides
  @Singleton
  fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
  ): Retrofit =
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(gsonConverterFactory)
      .client(okHttpClient)
      .build()

  @Provides
  @Singleton
  fun provideUserService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

}