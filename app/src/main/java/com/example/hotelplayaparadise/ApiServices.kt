package com.example.hotelplayaparadise


import retrofit2.Call
import retrofit2.http.GET

// Retrofit Service Interface for KPI Data
interface ApiService {

    @GET("topClientesPorIngreso")
    fun getClientData(): Call<List<ClienteIngreso>>

    @GET("PromedioPreciosPorHabitacion")
    fun getPrecioporHabitacion(): Call<List<HabitacionPrecio>>

    @GET("ingresosPorAño")
    fun getIngresosporanio(): Call<List<IngresoAnual>>

    @GET("temporadaConMasIngresos")
    fun gettemporadaingresos(): Call<List<PrecioTemporada>>

    @GET("reservaciones")
    fun getreservacionestabular(): Call<List<ClienteReserva>>
}
