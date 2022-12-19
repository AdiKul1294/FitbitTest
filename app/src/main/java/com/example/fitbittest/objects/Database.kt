package com.example.fitbittest.objects

object Database {
    val client_id = "238VCP"
    val client_secret = "4fc3765c38c47eb896e3ae6f1824f86e"
    val redirectUrl = "https://example.com"
    val authUrl = "https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=238VCP&scope=activity+cardio_fitness+electrocardiogram+heartrate+location+nutrition+oxygen_saturation+profile+respiratory_rate+settings+sleep+social+temperature+weight"
    val tokenUrl = "https://api.fitbit.com/oauth2/token"
    var authCode : String = ""
    var accessToken : String = ""
    var refreshToken : String = ""
}