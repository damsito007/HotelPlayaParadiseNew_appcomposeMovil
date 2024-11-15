package com.example.hotelplayaparadise

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hotelplayaparadise.ui.theme.screens.ApiService
import com.example.hotelplayaparadise.ui.theme.screens.FacturasTodasCard
import com.example.hotelplayaparadise.ui.theme.screens.IngresoCard
import com.example.hotelplayaparadise.ui.theme.screens.LoginScreen
import com.example.hotelplayaparadise.ui.theme.screens.NewReservationCard
import com.example.hotelplayaparadise.ui.theme.screens.PagoCard
import com.example.hotelplayaparadise.ui.theme.screens.ProfileScreen
import com.example.hotelplayaparadise.ui.theme.screens.ReportScreen
import com.example.hotelplayaparadise.ui.theme.screens.ReservationCard
import com.example.hotelplayaparadise.ui.theme.screens.ReservationCardall
import com.example.hotelplayaparadise.ui.theme.screens.RetrofitInstance
import com.example.hotelplayaparadise.ui.theme.screens.SplashScreen
import com.example.hotelplayaparadise.ui.theme.theme.HotelPlayaParadiseTheme
import com.example.hotelplayaparadise.ui.theme.theme.greenstrong
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.io.path.Path
import kotlin.io.path.moveTo
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HotelPlayaParadiseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(modifier = Modifier.padding(innerPadding) )
                }
            }
        }
    }
}

@Composable
fun MyApp( modifier: Modifier = Modifier) {
    //Host de Navegación
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        // Pantalla de cara (Splash Screen)
        composable("splash") { SplashScreen(navController) }
        // Pantalla de Login
        composable("login") { LoginScreen(navController) }
        // Pantalla Home con Menú Desplegable
        composable("home") { HomeScreen(navController) }
        // Pantalla de Configuración
        composable("settings") { SettingsScreen(navController) }
        // Pantalla de Perfil
        composable("profile") { ProfileScreen(navController) }
        // Pantalla de Informe de Satisfacción
        composable("report") { ReportScreen(navController) }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    HotelPlayaParadiseTheme {
        val navController = rememberNavController()
        // SplashScreen(navController)
        //LoginScreen(navController)
        HomeScreen(navController)
        // OpcionesMenuLateral(navController)
        //HomeContent()
        //ProfileScreen(navController)
        //ReportScreen(navController)

    }
}

@Composable
fun OpcionesMenuLateral(navController: NavHostController) {
    ModalDrawerSheet(
        // modifier = Modifier.fillMaxSize(),
        drawerContainerColor = greenstrong,
        content = {
            // Header del menú lateral con información del usuario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Foto de perfil o ícono de usuario
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.puesta_de_sol),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Hotel Playa Paradise", style = MaterialTheme.typography.titleLarge, color = Color.White)
            }

            Divider(color = Color.White)

            // Opciones del menú
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(16.dp))

                // Opción Configuración
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Configuración", tint = Color.White) }, // Ícono en negro
                    label = { Text("Configuración", color = Color.White) }, // Texto en negro
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent) // Fondo transparente
                )

                // Opción Perfil
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Perfil", tint = Color.White) }, // Ícono en negro
                    label = { Text("Perfil", color = Color.White) }, // Texto en negro
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent) // Fondo transparente
                )

                // Opción Informe de Satisfacción
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_data_saver_off_24), // Carga el ícono desde drawable
                            contentDescription = "Informe de MongoDB",
                            tint = Color.White // Cambia el color del ícono a blanco si es necesario
                        )
                    }, // Ícono en negro
                    label = { Text("Informe de MongoDB", color = Color.White) }, // Texto en negro
                    selected = false,
                    onClick = { navController.navigate("report") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent) // Fondo transparente
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Agregamos el componente para el menú lateral
            OpcionesMenuLateral(navController)
        },
        content = {
            Column {
                TopAppBar(
                    modifier = Modifier.height(60.dp), // Ajustamos la altura de la barra superior
                    title = {
                        Text(
                            "Hotel Playa Paradise",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 48.dp)
                                .padding(bottom = 5.dp )// Aseguramos que el texto no se desplace por el icono
                                .wrapContentSize(Alignment.Center) // Centramos el texto horizontalmente
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                            modifier = Modifier.padding(start = 8.dp).padding(bottom = 5.dp) // Espaciamos el ícono del borde
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu Icon")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = greenstrong, // Usamos el color greenstrong
                        titleContentColor = Color.White,    // Color del texto
                        navigationIconContentColor = Color.White // Color del ícono
                    )
                )

                    HomeContent()

            }
        }
    )
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeContent() {
    var clienteingre by remember { mutableStateOf<List<ClienteIngreso>>(emptyList()) }
    var habitacionPrecio by remember { mutableStateOf<List<HabitacionPrecio>>(emptyList()) }
    var ingresoanual by remember { mutableStateOf<List<IngresoAnual>>(emptyList()) }
    var temporadaprecio by remember { mutableStateOf<List<PrecioTemporada>>(emptyList()) }
    var reservacioncliente by remember { mutableStateOf<List<ClienteReserva>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {

        //Consumo de Clientes
        RetrofitClient.apiService.getClientData().enqueue(object : Callback<List<ClienteIngreso>> {
            override fun onResponse(call: Call<List<ClienteIngreso>>, response: Response<List<ClienteIngreso>>) {
                if (response.isSuccessful) {
                    clienteingre = response.body() ?: emptyList()
                    loading = false
                } else {
                    error = "Error: ${response.message()}"
                    loading = false
                }
            }

            override fun onFailure(call: Call<List<ClienteIngreso>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
                loading = false
            }
        })

      //Consumo de Habitaciones por precio
        RetrofitClient.apiService.getPrecioporHabitacion().enqueue(object : Callback<List<HabitacionPrecio>> {
            override fun onResponse(call: Call<List<HabitacionPrecio>>, response: Response<List<HabitacionPrecio>>) {
                if (response.isSuccessful) {
                    habitacionPrecio = response.body() ?: emptyList()
                    loading = false
                } else {
                    error = "Error: ${response.message()}"
                    loading = false
                }
            }

            override fun onFailure(call: Call<List<HabitacionPrecio>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
                loading = false
            }
        })

        //Consumo de Ingresos Anuales
        RetrofitClient.apiService.getIngresosporanio().enqueue(object : Callback<List<IngresoAnual>> {
            override fun onResponse(call: Call<List<IngresoAnual>>, response: Response<List<IngresoAnual>>) {
                if (response.isSuccessful) {
                    ingresoanual = response.body() ?: emptyList()
                    loading = false
                } else {
                    error = "Error: ${response.message()}"
                    loading = false
                }
            }

            override fun onFailure(call: Call<List<IngresoAnual>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
                loading = false
            }
        })

        //Consumo de Ingresos por temporada

        RetrofitClient.apiService.gettemporadaingresos().enqueue(object : Callback<List<PrecioTemporada>> {
            override fun onResponse(call: Call<List<PrecioTemporada>>, response: Response<List<PrecioTemporada>>) {
                if (response.isSuccessful) {
                    temporadaprecio = response.body() ?: emptyList()
                    loading = false
                } else {
                    error = "Error: ${response.message()}"
                    loading = false
                }
            }

            override fun onFailure(call: Call<List<PrecioTemporada>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
                loading = false
            }
        })

        //Consumo de Reservacion de clientes

        RetrofitClient.apiService.getreservacionestabular().enqueue(object : Callback<List<ClienteReserva>> {
            override fun onResponse(call: Call<List<ClienteReserva>>, response: Response<List<ClienteReserva>>) {
                if (response.isSuccessful) {
                    reservacioncliente = response.body() ?: emptyList()
                    loading = false
                } else {
                    error = "Error: ${response.message()}"
                    loading = false
                }
            }

            override fun onFailure(call: Call<List<ClienteReserva>>, t: Throwable) {
                error = "Failed to load data: ${t.message}"
                loading = false
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
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Informe del Modelo Tabular",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Estado de carga o error
            when {
                loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                error != null -> {
                    Text(
                        text = "Error: $error",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                }
                else -> {
                    // Listado de clientes
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Text(
                                text = "Clientes por Ingresos:",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        // Calcula el ingreso máximo para escalar las barras
                        val maxIngreso = clienteingre.maxOfOrNull { it.ingresoTotal } ?: 1.0

                        items(clienteingre) { cliente ->
                            SingleBarChart(cliente = cliente, maxIngreso = maxIngreso)
                        }

                        item {
                            Text(
                                text = "Promedio de precios por tipo de Habitación:",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        val maxPrecio = habitacionPrecio.maxOfOrNull { it.precioPromedioPorNoche } ?: 1.0

                            items(habitacionPrecio) { habitacion ->
                                SingleBarChartPrecio(habitacionPrecio = habitacion, maxPrecio = maxPrecio)
                            }

                        item {
                            Text(
                                text = "Ganancias Anuales:",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        item {
                            // Aquí pasamos la lista de ingresos anuales
                            IngresoAnualPieChart(ingresoanual = ingresoanual)
                        }

                        item {
                            Text(
                                text = "Ingresos Por Temporada:",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        item {
                            // Aquí pasamos la lista de ingresos anuales
                            RadarChart(temporadaprecio = temporadaprecio)
                        }

                        item {
                            Text(
                                text = "Reservaciones de los Clientes:",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        item {
                            // Llamamos al gráfico de dispersión pasando la lista `metodoReservacion`
                            ScatterPlotWithAxes(clienteReservacion = reservacioncliente)
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun SingleBarChart(cliente: ClienteIngreso, maxIngreso: Double) {
    val barWidth = 200.dp // Ancho de cada barra
    val barHeightRatio = cliente.ingresoTotal / maxIngreso

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nombre del cliente
        Text(
            text = cliente.nombreCliente,
            modifier = Modifier.width(80.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        // Barra de ingreso
        Canvas(modifier = Modifier.size(barWidth, 40.dp)) {
            val barWidthAdjusted = (barHeightRatio * size.width).toFloat()
            drawRoundRect(
                color = greenstrong,
                size = androidx.compose.ui.geometry.Size(width = barWidthAdjusted, height = size.height),
                cornerRadius = CornerRadius(8f)
            )
        }

        // Valor del ingreso al final de la barra
        Text(
            text = "$${cliente.ingresoTotal}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SingleBarChartPrecio(habitacionPrecio: HabitacionPrecio, maxPrecio: Double) {
    val barWidth = 120.dp // Barra más delgada (ajusta este valor si es necesario)
    val barHeightRatio = habitacionPrecio.precioPromedioPorNoche / maxPrecio

    // Definir colores pastel según el tipo de habitación
    val barColor = when (habitacionPrecio.tipoHabitacion) {
        "Suite" -> Color(0xFFFFC1C1)  // Rosa pastel
        "Doble" -> Color(0xFFBBDEFB)  // Azul pastel
        "Familiar" -> Color(0xFFF5D0A9)  // Durazno pastel
        "Individual" -> Color(0xFFB0E57C)  // verde pastel
        "Presidencial" -> Color(0xFFD1C4E9)  // Lila pastel
        else -> Color(0xFFB2DFDB)  // Verde azulado pastel para tipos desconocidos
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Espacio entre barras
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nombre del tipo de habitación
        Text(
            text = habitacionPrecio.tipoHabitacion,
            modifier = Modifier.width(120.dp), // Ancho fijo para el nombre del tipo de habitación
            style = MaterialTheme.typography.bodyLarge
        )

        // Barra de precio
        Canvas(modifier = Modifier.size(barWidth, 40.dp)) {
            val barWidthAdjusted = (barHeightRatio * size.width).toFloat()
            drawRoundRect(
                color = barColor,  // Color dinámico de la barra
                size = androidx.compose.ui.geometry.Size(width = barWidthAdjusted, height = size.height),
                cornerRadius = CornerRadius(8f) // Esquinas redondeadas
            )
        }

        // Valor del precio al final de la barra
        Text(
            text = "$${"%.2f".format(habitacionPrecio.precioPromedioPorNoche)}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.CenterVertically) // Alinea el precio en el centro verticalmente
                .padding(start = 4.dp) // Ajusta la posición horizontal del precio
        )
    }
}


//Chart para Ganancias Anuales

@Composable
fun IngresoAnualPieChart(ingresoanual: List<IngresoAnual>) {
    // Total de ingresos de todos los años para usarlo como referencia
    val totalIngresos = ingresoanual.filterNot { it.ingresosPorAnio.isNaN() }.sumOf { it.ingresosPorAnio }

    // Asegúrate de que el total no sea cero para evitar un error de división por cero
    if (totalIngresos > 0) {
        Canvas(modifier = Modifier.size(300.dp)) {
            var startAngle = 0f  // Aseguramos que startAngle es Float

            ingresoanual.forEach { ingreso ->
                // Filtramos los valores nulos o negativos si los hubiera
                if (ingreso.ingresosPorAnio > 0) {
                    val sweepAngle = ((ingreso.ingresosPorAnio.toFloat()) / totalIngresos.toFloat()) * 360f

                    // Dibuja el segmento del pastel
                    drawArc(
                        color = when (ingreso.anio) {
                            2020 -> Color(0xFFBBDEFB) // Light Gray (pastel)
                            2021 -> Color(0xFFB0E57C) // Light Green
                            2022 -> Color(0xFFF5A9B8) // Light Pink/Red
                            2023 -> Color(0xFFFFF7B2) // Light Yellow
                            2024 -> Color(0xFFB2EBF2) // Light Cyan
                            else -> Color.Gray
                        },
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        size = Size(size.width, size.height)
                    )

                    // Calcula la posición para colocar el texto al borde
                    val middleAngle = startAngle + sweepAngle / 2
                    val radius = size.width / 2

                    val textX = (size.width / 2) + (radius * Math.cos(Math.toRadians(middleAngle.toDouble()))).toFloat()
                    val textY = (size.height / 2) + (radius * Math.sin(Math.toRadians(middleAngle.toDouble()))).toFloat()

                    // Dibuja el texto (Año y Ganancia) en la posición calculada
                    drawContext.canvas.nativeCanvas.apply {
                        val text = "${ingreso.anio}: $${"%.2f".format(ingreso.ingresosPorAnio)}"
                        drawText(
                            text,
                            textX,
                            textY,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                        )
                    }

                    // Actualiza el ángulo de inicio para el siguiente segmento
                    startAngle += sweepAngle  // Ahora ambos son Float
                }
            }
        }
    } else {
        // Si no hay ingresos, muestra un mensaje alternativo
        Text(text = "No hay datos disponibles", color = Color.Red)
    }
}

@Composable
fun RadarChart(temporadaprecio: List<PrecioTemporada>) {
    // Filtrar valores nulos y obtener el valor máximo de precio para normalizar
    val filteredData = temporadaprecio.filter { it.precioNoche > 0 }
    val maxPrecio = filteredData.maxOfOrNull { it.precioNoche } ?: 1.0
    val numberOfAxes = filteredData.size  // Número de valores (ejes)

    // Dibuja el Canvas
    Canvas(modifier = Modifier.size(300.dp)) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        val angleStep = 360f / numberOfAxes

        // Dibujar los niveles concéntricos de fondo
        for (i in 1..5) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.2f * i),
                radius = radius * i / 5,
                center = center,
                style = Stroke(width = 2f)
            )
        }

        // Dibujar los ejes y etiquetas de cada `PrecioTemporada`
        filteredData.forEachIndexed { index, data ->
            val angle = Math.toRadians((angleStep * index).toDouble()).toFloat()
            val lineEnd = Offset(
                x = center.x + radius * cos(angle),
                y = center.y + radius * sin(angle)
            )

            // Eje de cada categoría
            drawLine(
                color = Color.Gray,
                start = center,
                end = lineEnd,
                strokeWidth = 2f
            )

            // Etiquetas de temporada, periodo y precio por noche
            drawContext.canvas.nativeCanvas.apply {
                val label = "${data.temporada} (${data.periodo}) - $${"%.2f".format(data.precioNoche)}"
                drawText(
                    label,
                    lineEnd.x,
                    lineEnd.y,
                    android.graphics.Paint().apply {
                        textSize = 23f
                        textAlign = android.graphics.Paint.Align.CENTER
                        color = android.graphics.Color.BLACK
                    }
                )
            }
        }

        // Dibujar el área de los datos
        val path = androidx.compose.ui.graphics.Path()
        filteredData.forEachIndexed { index, data ->
            val normalizedValue = (data.precioNoche / maxPrecio).toFloat()
            val angle = Math.toRadians((angleStep * index).toDouble()).toFloat()
            val point = Offset(
                x = center.x + radius * normalizedValue * cos(angle),
                y = center.y + radius * normalizedValue * sin(angle)
            )
            if (index == 0) path.moveTo(point.x, point.y) else path.lineTo(point.x, point.y)
        }
        path.close()  // Cerrar el área

        drawPath(
            path = path,
            color = Color.Cyan.copy(alpha = 0.4f),
            style = Fill
        )
    }
}

//Chart para reservaciones
@Composable
fun ScatterPlotWithAxes(clienteReservacion: List<ClienteReserva>) {
    val maxX = clienteReservacion.size.toFloat()  // Para normalizar el eje X
    val maxY = clienteReservacion.maxOfOrNull { it.precioPaquete }?.toFloat() ?: 1f // Eje Y basado en el precio más alto

    Canvas(modifier = Modifier.fillMaxWidth().height(400.dp)) {
        val padding = 16f
        val axisPadding = 40f // Para que los ejes no se dibujen en el borde

        // Dibujar los ejes X e Y
        val axisColor = Color.Black
        drawLine(
            color = axisColor,
            start = Offset(axisPadding, size.height - padding),
            end = Offset(size.width - padding, size.height - padding),
            strokeWidth = 2f
        ) // Eje X

        drawLine(
            color = axisColor,
            start = Offset(axisPadding, size.height - padding),
            end = Offset(axisPadding, padding),
            strokeWidth = 2f
        ) // Eje Y

        // Dibujar las etiquetas en el eje X (Meses o índice)
        clienteReservacion.forEachIndexed { index, cliente ->
            val xPos = axisPadding + (index / maxX) * (size.width - 2 * padding)
            val yPos = size.height - padding - (cliente.precioPaquete / maxY) * (size.height - 2 * padding)

            // Dibuja cada punto del gráfico de dispersión
            drawCircle(
                color = Color.Blue, // Color del punto
                radius = 6f,
                center = Offset(xPos, yPos)
            )
        }

        // Etiquetas del eje X (Meses o índices de datos)
        clienteReservacion.forEachIndexed { index, cliente ->
            val xPos = axisPadding + (index / maxX) * (size.width - 2 * padding)
            drawContext.canvas.nativeCanvas.drawText(
                "${index + 1}",  // Usamos el índice + 1 como etiqueta para X
                xPos,
                size.height - padding + 20f, // Espacio debajo del eje X
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 12.sp.toPx()
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Etiquetas del eje Y (Precios)
        val stepY = maxY / 5f // Dividir el eje Y en 5 pasos
        for (i in 0..5) {
            val yPosition = size.height - padding - (i * stepY * (size.height - 2 * padding) / maxY)

            // Ajustamos la posición de las etiquetas del eje Y un poco más abajo
            val adjustedYPosition = yPosition + 20f // Desplazamos las etiquetas hacia abajo

            drawContext.canvas.nativeCanvas.drawText(
                "${(i * stepY).toInt()}",
                axisPadding - 20f,
                adjustedYPosition,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 12.sp.toPx()
                }
            )
        }
    }
}

@Composable
fun SettingsScreen(navController: NavHostController) {
    // Pantalla de Configuración
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Configuración", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("home") }) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOpcionesMenuLateral() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Open) // Forzar que esté abierto

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            OpcionesMenuLateral(navController = navController)
        },
        content = {
            // Contenido principal (puede estar vacío para la previsualización del Drawer)
        }
    )
}
