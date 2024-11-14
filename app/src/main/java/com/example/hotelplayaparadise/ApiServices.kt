package com.example.hotelplayaparadise


import retrofit2.Call
import retrofit2.http.GET

// Retrofit Service Interface for KPI Data
interface ApiService {

    @GET("topClientesPorIngreso")
    fun getClientData(): Call<List<ClienteIngreso>>
}
