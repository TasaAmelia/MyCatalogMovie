package com.s2.made.core.di

import androidx.room.Room
import com.s2.made.core.datasource.MovieCatalogRepository
import com.s2.made.core.datasource.local.LocalDataSource
import com.s2.made.core.datasource.local.room.MovieCatalogDatabase
import com.s2.made.core.datasource.remote.RemoteDataSource
import com.s2.made.core.datasource.remote.network.ApiServices
import com.s2.made.core.domain.repository.ICatalogRepository
import com.s2.made.core.utils.AppExecutors
import com.s2.made.core.utils.Const
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {
    val databaseModule = module {
        factory { get<MovieCatalogDatabase>().movieCatalogDao() }
        single {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("catalog".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                    androidContext(),
                    MovieCatalogDatabase::class.java, "Movie.db"
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    }

    val networkModule = module {
        single {
            val hostname = "movie-api.dev"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
                .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
                .add(hostname, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
                .build()
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .certificatePinner(certificatePinner)
                    .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                    .baseUrl("${Const.BASE_URL}/${Const.API_VER}/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(get())
                    .build()
            retrofit.create(ApiServices::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<ICatalogRepository> {
            MovieCatalogRepository(
                    get(),
                    get(),
                    get()
            )
        }
    }
}