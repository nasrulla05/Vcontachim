package com.akhbulatov.vcontachim.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.FragmentInfoProfileBinding
import com.akhbulatov.vcontachim.model.Root

class InfoProfileFragment:Fragment(R.layout.fragment_info_profile) {
    private var binding:FragmentInfoProfileBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoProfileBinding.bind(view)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object{
        fun createFragment(user: Root.User):Fragment{
            val fr = InfoProfileFragment()
            val bundle = Bundle()

            fr.arguments = bundle
            return fr
        }
    }
}