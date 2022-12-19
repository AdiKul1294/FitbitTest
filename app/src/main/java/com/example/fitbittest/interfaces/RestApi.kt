package com.example.fitbittest.interfaces

import com.example.fitbittest.dataclasses.AuthInfo
import com.example.fitbittest.dataclasses.EcgData
import com.example.fitbittest.dataclasses.EcgReading
import com.example.fitbittest.dataclasses.TokenData
import retrofit2.Call
import retrofit2.http.*

interface RestApi {

    @Headers("Authorization: Basic MjM4VkNQOjRmYzM3NjVjMzhjNDdlYjg5NmUzYWU2ZjE4MjRmODZl")
    @POST("oauth2/token")
    @FormUrlEncoded
    fun getTokenInfo(
        @Field("clientId") clientId: String,
        @Field("grant_type") grant_type: String,
        @Field("redirect_uri") redirect_uri: String,
        @Field("code") code: String
    ): Call<TokenData>

    @Headers("accept: application/json")
    @GET("1/user/-/ecg/list.json?afterDate=2022-09-28&sort=asc&limit=1&offset=0")
    fun getEcgData(@HeaderMap headers: Map<String, String>) : Call<EcgData>
}