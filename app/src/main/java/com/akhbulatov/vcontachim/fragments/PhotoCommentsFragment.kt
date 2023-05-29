package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.adapters.PhotoCommentsAdapter
import com.akhbulatov.vcontachim.databinding.FragmentCommentsBinding
import com.akhbulatov.vcontachim.model.Item
import com.akhbulatov.vcontachim.model.PhotoCommentsUi
import com.akhbulatov.vcontachim.viewmodel.PhotoCommentsViewModel
import com.google.android.material.snackbar.Snackbar

class PhotoCommentsFragment : Fragment(R.layout.fragment_comments) {
    private var binding: FragmentCommentsBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[PhotoCommentsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommentsBinding.bind(view)

        binding!!.exit.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        val photoCommAdapter = PhotoCommentsAdapter(
            object : PhotoCommentsAdapter.OnClick {
                override fun likeComm(commentsUi: PhotoCommentsUi) {
                    viewModel.likeComment(commentsUi)
                }
            }
        )

        binding!!.commentList.adapter = photoCommAdapter

        viewModel.commentsLiveData.observe(viewLifecycleOwner) {
            photoCommAdapter.submitList(it)
            binding!!.exit.subtitle = it[0].count.toString()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }

        val arg = requireArguments().getLong(ARGUMENTS_PHOTO_ID)
        viewModel.getComments(arg)
    }

    companion object {
        private const val ARGUMENTS_PHOTO_ID = "ID"

        fun createFragment(item: Item): Fragment {
            val photoCommentsFragments = PhotoCommentsFragment()
            val bundle = Bundle()
            bundle.putLong(ARGUMENTS_PHOTO_ID, item.id)
            photoCommentsFragments.arguments = bundle

            return photoCommentsFragments
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}