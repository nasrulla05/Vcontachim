package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
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
                    if (commentsUi.usersLike == 0L) viewModel.likeComment(commentsUi)
                    else viewModel.likeDelete(commentsUi)
                }

//                override fun router(user: Root.User) {
//                    VcontachimApplication.router.navigateTo(Screens.infoProfile(user))
//                }
            } )

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


        val argItem = arguments?.getSerializable(ARGUMENTS_ITEM)
        val item: Item = argItem as Item

        viewModel.getComments(argItem.id)

        binding!!.leaveAComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Вызывается ДО изменения текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding!!.leaveAComment.text.isNotEmpty()) {
                    binding!!.submitComment.setImageResource(R.drawable.send_28_active)
                    binding!!.submitComment.setOnClickListener {
                        viewModel.submitComment(item, s.toString())
                        VcontachimApplication.keyboard.hideKeyBoard(view)
                        s!!.clear()

                        viewModel.leaveCommLiveData.observe(viewLifecycleOwner) {
                            val toast = Toast.makeText(
                                context,
                                it,
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    }
                } else {
                    binding!!.submitComment.setImageResource(R.drawable.send_28_not_active)
                }
            }
        })


    }

    companion object {
        private const val ARGUMENTS_ITEM = "ITEM"

        fun createFragment(item: Item): Fragment {
            val photoCommentsFragments = PhotoCommentsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_ITEM, item)
            photoCommentsFragments.arguments = bundle

            return photoCommentsFragments
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}