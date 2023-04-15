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
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {
    val authURL =
        "https://oauth.vk.com/authorize?" +
                "client_id=51611155&" +
                "redirect_uri=https://oauth.vk.com/blank.html&" +
                "scope=12&" +
                "display=mobile&" +
                "response_type=token"
    var binding: FragmentAuthBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)

        binding!!.webView.loadUrl(authURL)
        binding!!.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val url: Uri = request.url
                val urlString = url.toString()
                val urlDecoder = Uri.decode(urlString)
                val finalUrlDecoded = Uri.decode(urlDecoder)
                if (finalUrlDecoded.contains("access_token", ignoreCase = false)) {
                    val afterAccessToken = finalUrlDecoded.substringAfter("access_token=")
                    val beforeAccessToken = afterAccessToken.substringBefore("&")

                    val sharedPreferences: SharedPreferences =
                        requireContext().getSharedPreferences(
                            "vcontachim_preferences",
                            Context.MODE_PRIVATE
                        )
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("access_token", beforeAccessToken)
                    editor.apply()
                }

                return super.shouldOverrideUrlLoading(view, request)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}