package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.FragmentComments2Binding

class CommentsFragment : Fragment(R.layout.fragment_comments2) {
    private var binding:FragmentComments2Binding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentComments2Binding.bind(view)





    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}