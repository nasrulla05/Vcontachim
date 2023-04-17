package com.akhbulatov.vcontachim.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
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
import com.akhbulatov.vcontachim.viewModel.AuthViewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val authUrl =  "https://oauth.vk.com/authorize?" +
            "client_id=51611155&" +
            "redirect_uri=https://oauth.vk.com/blank.html&" +
            "scope=12&" +
            "display=mobile&" +
            "response_type=token"

    private var binding: FragmentAuthBinding? = null
    val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)

        binding!!.webView.loadUrl(authUrl)

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