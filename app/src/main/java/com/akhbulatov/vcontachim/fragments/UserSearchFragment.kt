package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.adapters.UserSearchAdapter
import com.akhbulatov.vcontachim.databinding.FragmentUserSearchBinding
import com.akhbulatov.vcontachim.model.UserSearchUi
import com.akhbulatov.vcontachim.utility.Keyboard
import com.akhbulatov.vcontachim.viewmodel.UserSearchViewModel
import com.google.android.material.snackbar.Snackbar

class UserSearchFragment : Fragment(R.layout.fragment_user_search) {
    private var binding: FragmentUserSearchBinding? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[UserSearchViewModel::class.java]
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserSearchBinding.bind(view)

        binding!!.exit.setOnClickListener {
            Keyboard.hideKeyBoard(view)
        }

        val adapter = UserSearchAdapter(
            object : UserSearchAdapter.SearchFriendListener {
                override fun searchFriendClick(item: UserSearchUi) {
                    if (item.isFriend != 1) viewModel.addFriend(item)
                    else viewModel.deleteFriend(item)
                }
            }
        )
        binding!!.listUsers.adapter = adapter

        binding!!.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Вызывается ДО изменения текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Вызывается ВО время изменения текста
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.toString().length > 2)
                viewModel.searchUser(s.toString())
            }
        })

        binding!!.removeText.setOnClickListener {
            viewModel.clearList()
            binding!!.search.text.clear()
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) binding!!.progressBar.visibility = View.VISIBLE
            else binding!!.progressBar.visibility = View.GONE
        }

        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                requireView(),
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}