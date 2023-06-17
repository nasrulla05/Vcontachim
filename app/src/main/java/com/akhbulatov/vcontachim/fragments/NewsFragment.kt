package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.adapters.NewsAdapter
import com.akhbulatov.vcontachim.databinding.FragmentNewsBinding
import com.akhbulatov.vcontachim.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class NewsFragment : Fragment(R.layout.fragment_news) {
    private var binding: FragmentNewsBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        val newsAdapter = NewsAdapter()
        binding!!.listNews.adapter = newsAdapter

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                view,
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }

        viewModel.loadNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}