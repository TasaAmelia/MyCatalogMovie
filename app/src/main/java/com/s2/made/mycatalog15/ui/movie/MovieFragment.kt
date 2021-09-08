package com.s2.made.mycatalog15.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.s2.made.core.datasource.Resource
import com.s2.made.core.domain.model.Movie
import com.s2.made.core.ui.MovieAdapter
import com.s2.made.mycatalog15.databinding.FragmentMovieBinding
import com.s2.made.mycatalog15.ui.detail.DetailMovieActivity
import com.s2.made.mycatalog15.utils.Util
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModel()

    private var _binding: FragmentMovieBinding? = null
    private lateinit var movieAdapter: MovieAdapter
    private val binding get() = _binding as FragmentMovieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMovies()
        showMoviePopular()
    }

    override fun onResume() {
        super.onResume()
        showMovies()
        showMoviePopular()
    }

    private fun showMoviePopular() {
        if (activity != null) {
            movieAdapter.onItemClick = { selectedData ->
                Intent(activity, DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_ID, selectedData.id)
                    startActivity(this)
                }
            }
            movieViewModel.moviePopular.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    when (data) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> {
                            showLoading(false)
                            showMovieItems(data.data!!)
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

    private fun showMovieItems(movie: List<Movie>) {
        movieAdapter.apply { setData(movie) }
    }

    private fun showMovies() {
        movieAdapter = MovieAdapter()
        with(binding.rvHomeMovie) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBarHomeMovie.visibility = View.VISIBLE
        else binding.progressBarHomeMovie.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}