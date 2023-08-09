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
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.adapters.HistoryAdapter
import com.akhbulatov.vcontachim.adapters.UserSearchAdapter
import com.akhbulatov.vcontachim.database.HistoryUser
import com.akhbulatov.vcontachim.databinding.FragmentUserSearchBinding
import com.akhbulatov.vcontachim.model.UserSearchUi
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
                    override fun clearUser(user: HistoryUser) {
                        viewModel.deleteElement(user)
                    }
                })

            search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH){
                        val text = search.text.toString()
                        val his = HistoryUser(text)
                        viewModel.addElement(his)

                        return true
                    }
                    return false
                }
            })

            listUsers.adapter = adapter
            history.adapter = adapterHistory

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
                    if (text.length > 3) viewModel.searchUser(text)

                    human.setText(R.string.global_search)
                    clearListButton.visibility = View.GONE
                }
            })

            clearListButton.setOnClickListener {
                viewModel.clearUsers()
//                viewModel.clearListHistory()
                history.visibility = View.GONE
                clearListButton.visibility = View.GONE
                human.setText(R.string.global_search)
                listUsers.visibility = View.VISIBLE
            }

            removeText.setOnClickListener {
                search.text.clear()
                history.visibility = View.VISIBLE
                listUsers.visibility = View.GONE
                human.setText(R.string.search_history)
                clearListButton.visibility = View.VISIBLE
            }

            viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
                if (it) progressBar.visibility = View.VISIBLE
                else progressBar.visibility = View.GONE
            }

            viewModel.usersLiveData.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            viewModel.historyLiveData.observe(viewLifecycleOwner) {list->
                adapterHistory.submitList(list)
                viewModel.maxLengthHistory()
            }

            viewModel.errorLiveData.observe(viewLifecycleOwner) {
                val snackbar = Snackbar.make(
                    requireView(),
                    it,
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }

            viewModel.loadHistory()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}