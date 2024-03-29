package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.adapters.NewsAdapter
import com.akhbulatov.vcontachim.databinding.FragmentNewsBinding
import com.akhbulatov.vcontachim.model.NewsUi
import com.akhbulatov.vcontachim.model.TypeNews
import com.akhbulatov.vcontachim.utility.showSnackbar
import com.akhbulatov.vcontachim.viewmodel.NewsViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {
    private var binding: FragmentNewsBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        val newsAdapter = NewsAdapter(
            object : NewsAdapter.LikeDeletePostListener {
                override fun addDeleteLikePostClick(news:NewsUi) {
                    if (news.userLikes == 0) viewModel.addLike(news)
                    else viewModel.deleteLike(news)
                }
            }
        )
        binding!!.listNews.adapter = newsAdapter

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) binding!!.progressBar.visibility = View.VISIBLE
            else binding!!.progressBar.visibility = View.GONE
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showSnackbar(it)
        }

        val type = arguments?.getSerializable(ARGUMENTS_TYPE) as TypeNews
        viewModel.loadNews(type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ARGUMENTS_TYPE = "arg"

        fun createFragment(type: TypeNews): Fragment {
            val fr = NewsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_TYPE, type)

            fr.arguments = bundle
            return fr
        }
    }
}