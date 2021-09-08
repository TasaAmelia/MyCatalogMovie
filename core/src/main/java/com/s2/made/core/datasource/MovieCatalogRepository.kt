package com.s2.made.core.datasource

import com.s2.made.core.datasource.local.LocalDataSource
import com.s2.made.core.datasource.remote.network.ApiResponse
import com.s2.made.core.datasource.remote.RemoteDataSource
import com.s2.made.core.datasource.remote.response.MovieResponse
import com.s2.made.core.domain.model.Movie
import com.s2.made.core.domain.repository.ICatalogRepository
import com.s2.made.core.utils.AppExecutors
import com.s2.made.core.utils.DataMapper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieCatalogRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : ICatalogRepository {

    override fun getMoviesPopular(): Flowable<Resource<List<Movie>>> =
            object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(appExecutors) {
                override fun loadFromDB(): Flowable<List<Movie>> {
                    return localDataSource.getMovies().map { DataMapper.mapEntitiesToDomain(it) }
                }

                override fun shouldFetch(data: List<Movie>?): Boolean =
                        data == null || data.isEmpty()

                override fun createCall(): Flowable<ApiResponse<List<MovieResponse>>> =
                        remoteDataSource.getMoviesPopular()

                override fun saveCallResult(data: List<MovieResponse>) {
                    val movieList = DataMapper.mapResponsesToEntities(data)
                    localDataSource.insertMovies(movieList)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                }
            }.asFlowable()

    override fun getDetailMovie(
            movieId: String,
//            state: Boolean,
//            category: String
    ): Flowable<Resource<Movie>> =
            object : NetworkBoundResource<Movie, MovieResponse>(appExecutors) {
                override fun loadFromDB(): Flowable<Movie> {
                    return localDataSource.getMovieById(movieId).map { DataMapper.mapEntityToDomain(it) }
                }

                override fun shouldFetch(data: Movie?): Boolean =
                        data?.genre == ""

                override fun createCall(): Flowable<ApiResponse<MovieResponse>> =
                        remoteDataSource.getDetailMovie(movieId)

                override fun saveCallResult(data: MovieResponse) {
                    val movie = DataMapper.mapResponseToEntity(data)
                    localDataSource.updateMovie(movie)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                }
            }.asFlowable()

    override fun getFavoriteMovie(): Flowable<List<Movie>> =
            localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

}