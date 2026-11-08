package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.*
import com.example.ui.Screen
import com.example.ui.theme.ChurchGold
import com.example.ui.theme.ChurchNavyPrimary
import com.example.ui.theme.ChurchNavySecondary

@Composable
fun ChurchWebPortalScreen(
    liveService: LiveService,
    events: List<ChurchEvent>,
    devotionals: List<Devotional>,
    testimonies: List<Testimony>,
    gallery: List<GalleryItem>,
    stats: ChurchStats,
    currentLanguage: String,
    onNavigateTo: (Screen) -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var activeWebTab by remember { mutableStateOf("INICIO") }

    val navMenuItems = listOf(
        Pair("INICIO", if (isEs) "Inicio" else "Home"),
        Pair("EN_VIVO", if (isEs) "En Vivo" else "Live"),
        Pair("EVENTOS", if (isEs) "Eventos" else "Events"),
        Pair("ESTUDIOS", if (isEs) "Devocionales" else "Studies"),
        Pair("DONACIONES", if (isEs) "Donaciones" else "Giving"),
        Pair("TESTIMONIOS", if (isEs) "Testimonios" else "Testimonies"),
        Pair("GALERIA", if (isEs) "Galería" else "Gallery"),
        Pair("REGISTRO", if (isEs) "Membresía" else "Join Us"),
        Pair("CONTACTO", if (isEs) "Contacto" else "Contact")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("church_web_portal_column")
    ) {
        // --- 1. WEB TOP HEADER (Barra de Navegación del Sitio Web) ---
        item {
            Surface(
                color = ChurchNavyPrimary,
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    // Top Announcement Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF0A1830))
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isEs) "📍 Calle 14 #85-30, Cali • 📞 +57 312 345 6789" else "📍 Cali, Colombia • 📞 +57 312 345 6789",
                            fontSize = 11.sp,
                            color = Color(0xFFCBD5E1)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "iglesiadeldiosaltisimocenfol@gmail.com",
                                fontSize = 11.sp,
                                color = ChurchGold
                            )
                        }
                    }

                    // Main Web Brand Bar & Menu
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_church_logo_1794096727873),
                                contentDescription = "Logo Iglesia del Dios Altísimo",
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Iglesia del Dios Altísimo",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "CENFOL Comunidad Cristiana Oficial",
                                    color = ChurchGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // WhatsApp Action Button
                        FilledTonalButton(
                            onClick = {
                                val msg = "¡Hola! Te invito a visitar la web oficial de la Iglesia del Dios Altísimo (CENFOL): cultos en vivo, devocionales y eventos."
                                onShare(msg)
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color(0xFF25D366),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Compartir Web", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Horizontal Web Navigation Menu Bar
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF142747)),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(navMenuItems) { (key, label) ->
                            val isSelected = activeWebTab == key
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) ChurchGold else Color.Transparent,
                                modifier = Modifier.clickable {
                                    activeWebTab = key
                                    when (key) {
                                        "EN_VIVO" -> onNavigateTo(Screen.LIVE)
                                        "EVENTOS" -> onNavigateTo(Screen.EVENTS)
                                        "ESTUDIOS" -> onNavigateTo(Screen.DEVOTIONALS)
                                        "DONACIONES" -> onNavigateTo(Screen.GIVING)
                                        "TESTIMONIOS" -> onNavigateTo(Screen.TESTIMONIES)
                                        "GALERIA" -> onNavigateTo(Screen.GALLERY)
                                        "REGISTRO" -> onNavigateTo(Screen.REGISTER)
                                        "CONTACTO" -> onNavigateTo(Screen.SUPPORT)
                                    }
                                }
                            ) {
                                Text(
                                    text = label,
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- 2. WEB HERO BANNER ---
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.church_hero_banner_1794096739680),
                    contentDescription = "Santuario Iglesia del Dios Altísimo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x66000000),
                                    Color(0x99000000),
                                    Color(0xF209172E)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = ChurchGold,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "PORTAL WEB OFICIAL CENFOL",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Bienvenido a la Iglesia\ndel Dios Altísimo",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            lineHeight = 32.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "\"El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente\" — Salmos 91:1",
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { onNavigateTo(Screen.LIVE) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Culto En Vivo", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onNavigateTo(Screen.GIVING) },
                            colors = ButtonDefaults.buttonColors(containerColor = ChurchGold),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Donar Online", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // --- 3. WEB SECTION: HORARIOS DE SERVICIOS Y UBICACIÓN ---
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Horarios de Servicios y Reuniones",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Te esperamos de manera presencial en nuestro templo o conéctate vía streaming",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    WebScheduleCard(
                        day = "DOMINGOS",
                        time = "08:00 AM & 10:30 AM",
                        name = "Culto Dominical de Adoración y Palabra",
                        badge = "Transmisión Online",
                        modifier = Modifier.weight(1f)
                    )
                    WebScheduleCard(
                        day = "VIERNES",
                        time = "07:00 PM",
                        name = "Noche de Jóvenes, Oración y Milagros",
                        badge = "Presencial",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // --- 4. WEB SECTION: TRANSMISIÓN EN VIVO PREVIEW ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = Color(0xFFE53935),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "🔴 EN VIVO AHORA",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${liveService.viewerCount} conectados",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        TextButton(onClick = { onNavigateTo(Screen.LIVE) }) {
                            Text("Abrir reproductor y chat →", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = liveService.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Predicador: ${liveService.preacher} • ${liveService.biblicalText}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = liveService.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // --- 5. WEB SECTION: CALENDARIO DE EVENTOS ---
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Próximos Eventos y Actividades",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Agéndate para participar con tu familia",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    TextButton(onClick = { onNavigateTo(Screen.EVENTS) }) {
                        Text("Ver Calendario →", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                events.take(2).forEach { ev ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onNavigateTo(Screen.EVENTS) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("NOV", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Text(ev.date.takeLast(2), fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(ev.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("⏰ ${ev.time} • 📍 ${ev.location}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = ChurchGold)
                        }
                    }
                }
            }
        }

        // --- 6. WEB SECTION: DEVOCIONALES Y ESTUDIOS BÍBLICOS ---
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Blog de Devocionales Semanales",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Estudios bíblicos para alimentar tu espíritu",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    TextButton(onClick = { onNavigateTo(Screen.DEVOTIONALS) }) {
                        Text("Ver Todos →", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                devotionals.firstOrNull()?.let { dev ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateTo(Screen.DEVOTIONALS) }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(dev.week, fontSize = 11.sp, color = ChurchGold, fontWeight = FontWeight.Bold)
                            Text(dev.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("📖 ${dev.verseRef}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = dev.reflection,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // --- 7. WEB SECTION: NUEVO MIEMBRO & REGISTRO ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ChurchNavySecondary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "¿Eres nuevo en la Iglesia del Dios Altísimo?",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Queremos conocerte y acompañarte. Regístrate para integrarte a un grupo de conexión CENFOL, recibir consejería y crecer espiritualmente.",
                        fontSize = 12.sp,
                        color = Color(0xFFE2E8F0)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onNavigateTo(Screen.REGISTER) },
                        colors = ButtonDefaults.buttonColors(containerColor = ChurchGold),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Completar Registro de Miembro Online", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // --- 8. WEB FOOTER INSTITUCIONAL ---
        item {
            Surface(
                color = Color(0xFF071224),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_church_logo_1794096727873),
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Iglesia del Dios Altísimo",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Comunidad Cristiana CENFOL Colombia. Proclamando el Evangelio de Jesucristo con poder, amor y transformación.",
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(14.dp))

                    Text("📍 Dirección: Calle 14 #85-30, Cali, Colombia", color = Color(0xFFCBD5E1), fontSize = 11.sp)
                    Text("✉️ Email: iglesiadeldiosaltisimocenfol@gmail.com", color = Color(0xFFCBD5E1), fontSize = 11.sp)
                    Text("📞 Teléfono: +57 312 345 6789", color = Color(0xFFCBD5E1), fontSize = 11.sp)

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "© 2026 Iglesia del Dios Altísimo. Todos los derechos reservados.",
                            color = Color(0xFF64748B),
                            fontSize = 10.sp
                        )

                        TextButton(onClick = { onNavigateTo(Screen.ADMIN) }) {
                            Text("Acceso Pastoral", color = ChurchGold, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WebScheduleCard(
    day: String,
    time: String,
    name: String,
    badge: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Surface(
                color = ChurchGold.copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = day,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = ChurchGold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(time, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(name, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = badge,
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
    }
}
