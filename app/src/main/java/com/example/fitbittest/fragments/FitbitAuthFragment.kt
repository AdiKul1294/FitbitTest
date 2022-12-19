package com.example.fitbittest.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.fitbittest.R
import com.example.fitbittest.databinding.FragmentFitbitAuthBinding
import com.example.fitbittest.objects.Database
import java.util.*

class FitbitAuthFragment : Fragment() {

    private lateinit var binding : FragmentFitbitAuthBinding
    private val client_id = Database.client_id
    private val client_secret = Database.client_secret
    private val redirectUrl = Database.redirectUrl
    private val authUrl = Database.authUrl
    private val tokenUrl = Database.tokenUrl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fitbit_auth, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = binding.authWvFaf

        binding.authBtnCvFaf.setOnClickListener {
            var authCode : String = ""
            /*val unique_state = UUID.randomUUID().toString()
            val codeChallenge = Base64.getEncoder().encodeToString(unique_state.toByteArray())*/
            val uri = Uri.parse(authUrl)
                .buildUpon()
              /*  .appendQueryParameter("response_type", "code")
                .appendQueryParameter("client_id", client_id)
                .appendQueryParameter("scope", "activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight%20oxygen_saturation%20respiratory_rate%20temperature")
                .appendQueryParameter("expires_in", "604800")*/
                .build()

            binding.authBtnCvFaf.visibility = View.INVISIBLE
            webView.visibility = View.VISIBLE

            webView.settings.javaScriptEnabled = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.settings.setSupportMultipleWindows(true)

            webView.webChromeClient = WebChromeClient()
           webView.loadUrl(uri.toString())

                webView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        request?.let {
                            // Check if this url is our OAuth redirectUrl, otherwise ignore it
                            if (request.url.toString().startsWith(redirectUrl)) {
                                webView.visibility = View.GONE
                                Log.d("Redirect", request.url.toString())
                                request.url.getQueryParameter("code")?.let { code ->
                                    // Got it!
                                    authCode = code
                                    Database.authCode = code
                                    Log.d("OAuth", "Authorization code : $code")
                                    findNavController().navigate(R.id.action_fitbitAuthFragment_to_userDataFragment)
                                } ?: run {
                                    // User cancelled the login flow
                                    Log.d("OAuth", "Authorization code not received")
                                }
                            }
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }


        }
    }
}