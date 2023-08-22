package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.adapters.HistoryAdapter
import com.akhbulatov.vcontachim.adapters.UserSearchAdapter
import com.akhbulatov.vcontachim.database.SearchHistoryModel
import com.akhbulatov.vcontachim.databinding.FragmentUserSearchBinding
import com.akhbulatov.vcontachim.model.UserSearchUi
import com.akhbulatov.vcontachim.utility.showToast
import com.akhbulatov.vcontachim.viewmodel.UserSearchViewModel
import java.util.*

class UserSearchFragment : Fragment(R.layout.fragment_user_search) {
    private var binding: FragmentUserSearchBinding? = null
    private val viewModel by viewModels<UserSearchViewModel>()

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserSearchBinding.bind(view)
        binding!!.apply {

            val adapter = UserSearchAdapter(
                object : UserSearchAdapter.SearchFriendListener {
                    override fun searchFriendClick(item: UserSearchUi) {
                        if (item.isFriend != 1) viewModel.addFriend(item)
                        else viewModel.deleteFriend(item)
                    }
                }
            )

            val adapterHistory = HistoryAdapter(
                object : HistoryAdapter.ClearListener {
                    override fun clearUser(historyItem: SearchHistoryModel) {
                        viewModel.deleteHistoryItem(historyItem)
                    }
                })

            search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val text = search.text.toString()
                        val his = SearchHistoryModel(text)
                        if (search.text.isNotBlank()) viewModel.addHistoryItem(his)

                        return true
                    }
                    return false
                }
            })

            listUsers.adapter = adapter
            historyList.adapter = adapterHistory

            search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Вызывается ДО изменения текста
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Вызывается ВО время изменения текста
                }

                override fun afterTextChanged(s: Editable?) {
                    val text = s!!.toString()
                    if (text.length > 1) viewModel.searchUser(text)

                    if (search.text.isBlank()) {
                        searchHistoryTextView.setText(R.string.search_history)
                        clearListButton.visibility = View.VISIBLE
                        historyList.visibility = View.VISIBLE
                        listUsers.visibility = View.GONE
                    } else {
                        searchHistoryTextView.setText(R.string.global_search)
                        clearListButton.visibility = View.GONE
                        historyList.visibility = View.GONE
                        listUsers.visibility = View.VISIBLE
                    }
                }
            })

            removeText.setOnClickListener {
                viewModel.clearUsersClick()
                search.text.clear()
            }

            clearListButton.setOnClickListener {
                viewModel.clearListHistoryClick()
            }

            viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
                if (it) progressBar.visibility = View.VISIBLE
                else progressBar.visibility = View.GONE
            }

            viewModel.usersLiveData.observe(viewLifecycleOwner) { list ->
                listUsers.visibility = View.VISIBLE
                adapter.submitList(list)
            }

            viewModel.historyLiveData.observe(viewLifecycleOwner) { list ->
                adapterHistory.submitList(list.reversed())
            }

            viewModel.errorLiveData.observe(viewLifecycleOwner) {
                showToast(it)
            }

            viewModel.loadHistory()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}