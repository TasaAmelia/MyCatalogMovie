package com.s2.made.mycatalog15.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.s2.made.core.datasource.Resource
import com.s2.made.core.domain.model.Movie
import com.s2.made.mycatalog15.R
import com.s2.made.mycatalog15.databinding.ActivityDetailMovieBinding
import com.s2.made.mycatalog15.databinding.ContentDetailMovieBinding
import com.s2.made.mycatalog15.utils.Util
import org.koin.android.viewmodel.ext.android.viewModel


class DetailMovieActivity : AppCompatActivity() {
    private val detailMovieViewModel: DetailMovieViewModel by viewModel()
    private lateinit var binding: ContentDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        binding = activityDetailMovieBinding.detailMovie

        setContentView(activityDetailMovieBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.detail)

        getDetailMovieViewModelLive()
    }

    private fun getDetailMovieViewModelLive() {
        val id = intent.extras?.getInt(EXTRA_ID)
        val favorite = intent.extras?.getBoolean(EXTRA_FAVORITE)
        val category = intent.extras?.getString(EXTRA_CATEGORY)
        if (id != null) {
            if (favorite != null) {
                if (category != null) {
                    detailMovieViewModel.getDetailMovie(id.toString(), favorite, category).observe(this) { detail ->
                        if (detail != null) {
                            when (detail) {
                                is Resource.Loading -> showLoading(true)
                                is Resource.Success -> {
                                    showLoading(false)
                                    populateDetailMovie(detail.data)
                                }
                                is Resource.Error -> {
                                    showLoading(false)
                                    Util.showMessage(binding.root, "Failed show data")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun populateDetailMovie(detail: Movie?) {
        binding.apply {
            this.titleDetailMovie.text = detail?.title
            Glide.with(this@DetailMovieActivity)
                    .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2${detail?.poster_path}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)).override(50, 50)
                    .error(R.drawable.ic_error)
                    .into(imageDetailMovie)
            this.dateDetailMovie.text = detail?.release_date
            this.genreDetailMovie.text = detail?.genre
            this.userScoreDetailMovie.text = detail?.vote_average.toString()
            this.popularityDetailMovie.text = detail?.popularity.toString()
            this.overviewDetailMovie.text = detail?.overview
        }
        var statusFavorite = detail!!.favorite
        setFavoriteState(statusFavorite)
        binding.fab.setOnClickListener {
            statusFavorite = !statusFavorite
            setFavoriteState(statusFavorite)
            detailMovieViewModel.setFavoriteMovie(detail, statusFavorite)
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBarDetail.visibility = View.VISIBLE
        else binding.progressBarDetail.visibility = View.GONE
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_white_24))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_white_24))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_FAVORITE = "extra_favorite"
    }
}