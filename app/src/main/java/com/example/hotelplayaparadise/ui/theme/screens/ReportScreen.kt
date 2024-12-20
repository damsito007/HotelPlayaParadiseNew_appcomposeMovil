package com.example.hotelplayaparadise.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hotelplayaparadise.IngresoData
import com.example.hotelplayaparadise.NewReservationData
import com.example.hotelplayaparadise.PagoTotalPorMetodo
import com.example.hotelplayaparadise.R
import com.example.hotelplayaparadise.ReservacionFactura
import com.example.hotelplayaparadise.ReservacionTotal
import com.example.hotelplayaparadise.ReservationData
import com.example.hotelplayaparadise.ui.theme.theme.greenstrong
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Retrofit service interface to fetch reservation data from the API
interface ApiService {

    @GET("Reservacion/Todas")
    fun getTotalReservationsData(): Call<List<ReservacionTotal>>

    @GET("Reservacion/confirmadas")
    fun getReservationData(): Call<List<ReservationData>>

    @GET("Reservacion/agrupadas/estado")
    fun getNewReservationData(): Call<List<NewReservationData>>

    @GET("Reservacion/ingresos_por_paquete")
    fun getIngresoData(): Call<List<IngresoData>>

    @GET("Facturas")
    fun getfacturasall(): Call<List<ReservacionFactura>>

    @GET("Facturas/pagadas")
    fun getfacturasaPagadas(): Call<List<ReservacionFactura>>

    @GET("Facturas/monto_minimo/1500")
    fun getfacturasMontoMinimo(): Call<List<ReservacionFactura>>

    @GET("Facturas/total_pago_por_metodo")
    fun getfacturastotalmetodopago(): Call<List<PagoTotalPorMetodo>>
}

// Retrofit instance to interact with the API
object RetrofitInstance {
    private const val BASE_URL = "http://apimongopython.centralus.azurecontainer.io:5000/"
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

@Composable
fun ReportScreen(navController: NavHostController) {
    // Estados para manejar todas las respuestas de la API
    var reservationstotal by remember { mutableStateOf<List<ReservacionTotal>>(emptyList()) }
    var reservations by remember { mutableStateOf<List<ReservationData>>(emptyList()) }
    var newReservations by remember { mutableStateOf<List<NewReservationData>>(emptyList()) }
    var ingresos by remember { mutableStateOf<List<IngresoData>>(emptyList()) }
    var facturastotal by remember { mutableStateOf<List<ReservacionFactura>>(emptyList()) }
    var facturaspagadas by remember { mutableStateOf<List<ReservacionFactura>>(emptyList()) }
    var facturasmontominimo by remember { mutableStateOf<List<ReservacionFactura>>(emptyList()) }
    var facturasmetodopago by remember { mutableStateOf<List<PagoTotalPorMetodo>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Fetch reservation data from all three endpoints on Composable load
    LaunchedEffect(Unit) {

        // firts endpoint

        // Fetching data from the first endpoint
        RetrofitInstance.apiService.getTotalReservationsData().enqueue(object : Callback<List<ReservacionTotal>> {
            override fun onResponse(call: Call<List<ReservacionTotal>>, response: Response<List<ReservacionTotal>>) {
                if (response.isSuccessful) {
                    reservationstotal = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<ReservacionTotal>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
            }
        })
        // Fetching data from the first endpoint
        RetrofitInstance.apiService.getReservationData().enqueue(object : Callback<List<ReservationData>> {
            override fun onResponse(call: Call<List<ReservationData>>, response: Response<List<ReservationData>>) {
                if (response.isSuccessful) {
                    reservations = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<ReservationData>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
            }
        })

        // Fetching data from the second endpoint
        RetrofitInstance.apiService.getNewReservationData().enqueue(object : Callback<List<NewReservationData>> {
            override fun onResponse(call: Call<List<NewReservationData>>, response: Response<List<NewReservationData>>) {
                if (response.isSuccessful) {
                    newReservations = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<NewReservationData>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
            }
        })

        // Fetching data from the third endpoint
        RetrofitInstance.apiService.getIngresoData().enqueue(object : Callback<List<IngresoData>> {
            override fun onResponse(call: Call<List<IngresoData>>, response: Response<List<IngresoData>>) {
                if (response.isSuccessful) {
                    ingresos = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
                loading = false
            }

            override fun onFailure(call: Call<List<IngresoData>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
                loading = false
            }
        })

        RetrofitInstance.apiService.getfacturasall().enqueue(object : Callback<List<ReservacionFactura>> {
            override fun onResponse(call: Call<List<ReservacionFactura>>, response: Response<List<ReservacionFactura>>) {
                if (response.isSuccessful) {
                    facturastotal = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<ReservacionFactura>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
            }
        })

        RetrofitInstance.apiService.getfacturasaPagadas().enqueue(object : Callback<List<ReservacionFactura>> {
            override fun onResponse(call: Call<List<ReservacionFactura>>, response: Response<List<ReservacionFactura>>) {
                if (response.isSuccessful) {
                    facturaspagadas = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<ReservacionFactura>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
            }
        })

        RetrofitInstance.apiService.getfacturasMontoMinimo().enqueue(object : Callback<List<ReservacionFactura>> {
            override fun onResponse(call: Call<List<ReservacionFactura>>, response: Response<List<ReservacionFactura>>) {
                if (response.isSuccessful) {
                    facturasmontominimo = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<ReservacionFactura>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
            }
        })

        RetrofitInstance.apiService.getfacturastotalmetodopago().enqueue(object : Callback<List<PagoTotalPorMetodo>> {
            override fun onResponse(call: Call<List<PagoTotalPorMetodo>>, response: Response<List<PagoTotalPorMetodo>>) {
                if (response.isSuccessful) {
                    facturasmetodopago = response.body() ?: emptyList()
                } else {
                    error = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<PagoTotalPorMetodo>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Make space for the fixed button at the bottom
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Informe de MongoDB",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Display loading or error message
            if (loading) {
                // Barra de carga con color primario de MaterialTheme
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.primary // Usando color primario
                )
            } else if (error != null) {
                Text("Error: $error", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red))
            } else {

                // Content displaying all reservation types
                LazyColumn(modifier = Modifier.weight(1f)) {


                    // Título para las Reservaciones por estado
                    item {
                        Text(
                            text = "Reservaciones por estado:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }

                    // Displaying new reservations by estado
                    items(newReservations) { newReservation ->
                        NewReservationCard(newReservation)
                    }

                    // Título para los Ingresos por paquete
                    item {
                        Text(
                            text = "Ingresos por tipo de paquete:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }

                    // Displaying ingresos data
                    items(ingresos) { ingreso ->
                        IngresoCard(ingreso)
                    }

                    item {
                        Text(
                            text = "Total según método de pago:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }

                    items(facturasmetodopago) { pago ->
                        PagoCard(pago)
                    }

                    //a partir de aqui consultas mas comunes
                    item {
                        Text(
                            text = "Reservaciones Totales:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }

                    items(reservationstotal) { reservationall ->
                        ReservationCardall(reservationall)
                    }

                    // Título para las Reservaciones confirmadas
                    item {
                        Text(
                            text = "Reservaciones Confirmadas:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }
                    // Displaying normal reservations
                    items(reservations) { reservation ->
                        ReservationCard(reservation)
                    }


                    item {
                        Text(
                            text = "Informe de Facturas",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }
                    item {
                        Text(
                            text = "Facturas Totales:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }
                    items(facturastotal) { facturaall ->
                        FacturasTodasCard(facturaall)
                    }

                    item {
                        Text(
                            text = "Facturas Pagadas:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }

                    items(facturaspagadas) { facturaall ->
                        FacturasTodasCard(facturaall)
                    }

                    item {
                        Text(
                            text = "Facturas con monto minimo de $1500:",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center // Centrar el título
                        )
                    }

                    items(facturasmontominimo) { facturaall ->
                        FacturasTodasCard(facturaall)
                    }


                }

            }

        }
        // Button to go back to the home screen (Fixed at the bottom)
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = greenstrong)
        ) {
            Text("Volver al Menú", color = Color.White)
        }
    }
}

@Composable
fun ReservationCardall(reservationall: ReservacionTotal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // ID de Reserva
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_key_24), // Icono personalizado
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("ID de Reserva: ${reservationall.id}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Habitación ID
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_bedroom_parent_24), // Icono personalizado para habitación
                    contentDescription = null,
                    tint = Color(0xFF8E24AA),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Habitación ID: ${reservationall.habitacionId}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Estado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_real_estate_agent_24), // Icono de estado
                    contentDescription = null,
                    tint = Color(0xFF43A047),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Estado: ${reservationall.estadoReservacion}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Paquete
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_card_giftcard_24), // Icono para el paquete
                    contentDescription = null,
                    tint = Color(0xFFFFA000),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Paquete: ${reservationall.paquete.nombrePaquete}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Descripción
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_description_24), // Icono personalizado para descripción
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Descripción: ${reservationall.paquete.descripcion}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))


            // Costo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_attach_money_24), // Icono de costo
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Costo: \$${reservationall.paquete.costo}", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Días
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_wb_sunny_24), // Icono para duración de días
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Días: ${reservationall.paquete.tiempoDias} días", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Fecha de Llegada
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24), // Icono de fecha de llegada
                    contentDescription = null,
                    tint = Color(0xFF0288D1),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fecha de Llegada: ${reservationall.fechas.llegada}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Fecha de Salida
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_today_24), // Icono de fecha de salida
                    contentDescription = null,
                    tint = Color(0xFF0288D1),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fecha de Salida: ${reservationall.fechas.salida}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun ReservationCard(reservation: ReservationData) {
    // Reservation card displaying each reservation's details
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // ID de Reserva
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_key_24), // Icono para ID de Reserva
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("ID de Reserva: ${reservation.id}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Habitación ID
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_bedroom_parent_24), // Icono para la habitación
                    contentDescription = null,
                    tint = Color(0xFF8E24AA),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Habitación ID: ${reservation.habitacionId}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Estado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_real_estate_agent_24), // Icono para el estado
                    contentDescription = null,
                    tint = Color(0xFF43A047),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Estado: ${reservation.estadoReservacion}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Paquete
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_card_giftcard_24), // Icono para Paquete
                    contentDescription = null,
                    tint = Color(0xFFFFA000),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Paquete: ${reservation.paquete.nombrePaquete}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Descripción
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_description_24), // Icono para Descripción
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Descripción: ${reservation.paquete.descripcion}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Costo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_attach_money_24), // Icono para Costo
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Costo: \$${reservation.paquete.costo}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Días
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_wb_sunny_24), // Icono para duración de días
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Días: ${reservation.paquete.tiempoDias} días", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Fecha de Llegada
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24), // Icono para la fecha de llegada
                    contentDescription = null,
                    tint = Color(0xFF0288D1),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fecha de Llegada: ${reservation.fechas.llegada}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Fecha de Salida
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_today_24), // Icono para la fecha de salida
                    contentDescription = null,
                    tint = Color(0xFF0288D1),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fecha de Salida: ${reservation.fechas.salida}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Composable
fun NewReservationCard(reservation: NewReservationData) {
    // New reservation card displaying the new type of reservation data
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Estado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_real_estate_agent_24), // Icono para el estado
                    contentDescription = null,
                    tint = Color(0xFF43A047),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Estado: ${reservation.id}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Total Reservaciones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_assignment_24), // Icono para total de reservaciones
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Total Reservaciones: ${reservation.totalReservaciones}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Composable
fun IngresoCard(ingreso: IngresoData) {
    // Card displaying ingresos data
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Paquete
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_card_giftcard_24), // Icono para el paquete
                    contentDescription = null,
                    tint = Color(0xFFFFA000),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Paquete: ${ingreso.id}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Total Ingresos
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_attach_money_24), // Icono para los ingresos
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Total Ingresos: \$${ingreso.totalIngresos}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Composable
fun FacturasTodasCard(facturaall: ReservacionFactura) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Información básica de la reservación
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_key_24), // Icono para la ID de reserva
                    contentDescription = null,
                    tint = Color(0xFFFFA000),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("ID de Reserva: ${facturaall.id}", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24), // Icono para la fecha
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Fecha: ${facturaall.fecha}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_real_estate_agent_24), // Icono para el estado
                    contentDescription = null,
                    tint = Color(0xFF388E3C),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Estado: ${facturaall.estado}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24), // Icono para el ID del cliente
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cliente ID: ${facturaall.clientesId}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Información de tipo de transacción
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_payment_24), // Icono para el método de pago
                    contentDescription = null,
                    tint = Color(0xFF388E3C),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Método de Pago: ${facturaall.tipoTransaccion.metodoPago}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_credit_card_24), // Icono para la información de pago
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Información de Pago: ${facturaall.tipoTransaccion.informacionPago}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_monetization_on_24), // Icono para la moneda
                    contentDescription = null,
                    tint = Color(0xFF388E3C),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Moneda: ${facturaall.tipoTransaccion.moneda}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Detalle del monto
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_money_24), // Icono para el monto base
                    contentDescription = null,
                    tint = Color(0xFFFFA000),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Monto Base: \$${facturaall.detalleMonto.montoBase}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_balance_24), // Icono para los impuestos
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Impuestos: \$${facturaall.detalleMonto.impuestos}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_discount_24), // Icono para los descuentos
                    contentDescription = null,
                    tint = Color(0xFF388E3C),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Descuentos: -\$${facturaall.detalleMonto.descuentos}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_card_giftcard_24), // Icono para el costo de paquetes
                    contentDescription = null,
                    tint = Color(0xFFFFA000),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Costo de Paquetes: \$${facturaall.detalleMonto.costoPaquetes}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_balance_wallet_24), // Icono para el monto total
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Monto Total: \$${facturaall.detalleMonto.montoTotal}", style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Historial de pagos
            Text("Historial de Pagos:", style = MaterialTheme.typography.bodyLarge, color = Color(0xFF388E3C))
            facturaall.historialPagos.forEach { pago ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_list_alt_24), // Icono para el historial de pagos
                        contentDescription = null,
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Fecha: ${pago.fecha}", style = MaterialTheme.typography.bodySmall)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_attach_money_24), // Icono para el monto pagado
                        contentDescription = null,
                        tint = Color(0xFFFFA000),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Monto: \$${pago.monto}", style = MaterialTheme.typography.bodySmall)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_wallet_24), // Icono para el método de pago
                        contentDescription = null,
                        tint = Color(0xFF388E3C),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Método de Pago: ${pago.metodoPago}", style = MaterialTheme.typography.bodySmall)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_credit_card_24), // Icono para la información de pago
                        contentDescription = null,
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Información de Pago: ${pago.informacionPago}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun PagoCard(pago: PagoTotalPorMetodo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // ID de Pago
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_payment_24), // Icono para ID de Pago
                    contentDescription = null,
                    tint = Color(0xFF1976D2), // Azul
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "ID de Pago: ${pago.id}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray // Azul
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Total Pagos
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_account_balance_wallet_24), // Icono para Total Pagos
                    contentDescription = null,
                    tint = Color(0xFFFFA000), // Naranja
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Total Pagos: \$${pago.totalPagos}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black // Naranja
                )
            }
        }
    }
}

// Preview for the ReportScreen
@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    val navController = rememberNavController()
    ReportScreen(navController = navController)
}
