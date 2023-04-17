package com.akhbulatov.vcontachim.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.FragmentAuthBinding
import com.akhbulatov.vcontachim.viewmodel.AuthViewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var binding: FragmentAuthBinding? = null
    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)

        binding!!.webView.loadUrl(viewModel.authUrl)

        binding!!.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val url: Uri = request.url
                viewModel.getAccessToken(url)
                return super.shouldOverrideUrlLoading(view, request)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}