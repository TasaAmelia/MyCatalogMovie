package com.s2.made.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.s2.made.core.ui.MovieAdapter
import com.s2.made.favorite.FavoriteModule.favoriteModule
import com.s2.made.favorite.databinding.FragmentFavoriteBinding
import com.s2.made.mycatalog15.ui.detail.DetailMovieActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var movieAdapter: MovieAdapter
    private val binding get() = _binding as FragmentFavoriteBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.catalog_favorite)
        showMovieFavorites()
    }

    private fun showMovieFavorites() {
        movieAdapter = MovieAdapter()
        if (activity != null) {
            movieAdapter.onItemClick = { selectedData ->
                Intent(activity, DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_ID, selectedData.id)
                    startActivity(this)
                }
            }

            favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner) { data ->
                movieAdapter.setData(data)
                binding.viewEmpty.root.visibility =
                        if (data.isNotEmpty()) View.GONE else View.VISIBLE
            }

            with(binding.rvFavoriteMovie) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }

        }

    }

}