package com.example.fitbittest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fitbittest.R
import com.example.fitbittest.databinding.FragmentUserDataBinding
import com.example.fitbittest.dataclasses.AuthInfo
import com.example.fitbittest.dataclasses.EcgData
import com.example.fitbittest.dataclasses.TokenData
import com.example.fitbittest.interfaces.RestApi
import com.example.fitbittest.objects.Database
import com.example.fitbittest.objects.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataFragment : Fragment() {

    private lateinit var binding : FragmentUserDataBinding
    private val client_id = Database.client_id
    private val client_secret = Database.client_secret
    private val redirectUrl = Database.redirectUrl
    private val authUrl = Database.authUrl
    private val tokenUrl = Database.tokenUrl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_data, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authInfo = AuthInfo(client_id, "authorization_code", redirectUrl, Database.authCode)
        getToken(authInfo)


    }

    private fun getToken(authInfo: AuthInfo) {
        var accessToken : String = ""
        var refreshToken : String = ""
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getTokenInfo(authInfo.clientId, authInfo.grant_type, authInfo.redirect_uri, authInfo.code).enqueue(
            object : Callback<TokenData> {
                override fun onFailure(call: Call<TokenData>, t: Throwable) {
                    Log.d("Service", t.message+"")
                }
                override fun onResponse( call: Call<TokenData>, response: Response<TokenData>) {
                    val tokenData = response.body()
                    Log.d("Access Response Code","".plus(response.raw().code))
                    accessToken = tokenData!!.access_token
                    refreshToken = tokenData!!.refresh_token
                    Log.d("Access Token", accessToken)
                    Database.accessToken = accessToken
                    Database.refreshToken = refreshToken
                    getEcgInfo(accessToken)
                }
            }
        )
    }

    fun getEcgInfo(accessToken : String) {
        val headerMap = mutableMapOf<String, String>()
        headerMap["authorization"] = "Bearer $accessToken"

        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.getEcgData(headerMap).enqueue(
            object : Callback<EcgData> {
                override fun onFailure(call: Call<EcgData>, t: Throwable) {
                    Log.d("Service", t.message+"")
                }
                override fun onResponse( call: Call<EcgData>, response: Response<EcgData>) {
                    val ecgData = response.body()
                    Log.d("ECG Response Code","".plus(response.raw().code))
                    if(response.raw().code == 200 && ecgData!=null){
                        val ecgReadings = ecgData.ecgReadings
                        if(ecgReadings.isNotEmpty()){
                            for(i in 0..ecgReadings.size){

                            }
                        }
                        binding.accTokenTvUdf.text = ecgData.toString()
                    }
                    else {
                        Log.d("ECG Response", response.raw().message)
                    }
                }
            }
        )
    }

}