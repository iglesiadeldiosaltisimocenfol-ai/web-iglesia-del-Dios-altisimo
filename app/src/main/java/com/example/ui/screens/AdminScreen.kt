package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChurchStats
import com.example.model.MemberRegistration
import com.example.model.Testimony
import com.example.ui.theme.ChurchGold

@Composable
fun AdminScreen(
    isAuthenticated: Boolean,
    stats: ChurchStats,
    pendingTestimonies: List<Testimony>,
    registeredMembers: List<MemberRegistration>,
    currentLanguage: String,
    onAuthenticate: (String) -> Boolean,
    onLogout: () -> Unit,
    onApproveTestimony: (String) -> Unit,
    onAddEvent: (String, String, String, String, String, String, String) -> Unit,
    onAddDevotional: (String, String, String, String, String, List<String>, String, String) -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var pinInput by remember { mutableStateOf("") }
    var adminTab by remember { mutableStateOf(0) } // 0: Métricas, 1: Testimonios, 2: Nuevo Evento, 3: Nuevo Estudio, 4: Miembros

    // Forms state
    var newEventTitle by remember { mutableStateOf("") }
    var newEventDate by remember { mutableStateOf("2026-12-20") }
    var newEventTime by remember { mutableStateOf("07:00 PM") }
    var newEventLocation by remember { mutableStateOf("Auditorio Principal") }
    var newEventCategory by remember { mutableStateOf("Servicio Principal") }
    var newEventDescription by remember { mutableStateOf("") }
    var newEventSpeaker by remember { mutableStateOf("Pastor David Morales") }

    var newDevTitle by remember { mutableStateOf("") }
    var newDevVerseRef by remember { mutableStateOf("Romanos 8:28") }
    var newDevVerseText by remember { mutableStateOf("Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien...") }
    var newDevReflection by remember { mutableStateOf("") }
    var newDevPractical by remember { mutableStateOf("") }
    var newDevQuestions by remember { mutableStateOf("") }
    var newDevAuthor by remember { mutableStateOf("Cuerpo Pastoral CENFOL") }

    if (!isAuthenticated) {
        // PIN & Biometric Security Gate
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .testTag("admin_security_gate"),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(ChurchGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = ChurchGold,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isEs) "Acceso Pastoral y Administrativo" else "Pastoral & Admin Access",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isEs) "Ingresa el PIN de seguridad de 4 dígitos o usa autenticación biométrica (PIN por defecto: 2026)"
                        else "Enter 4-digit security PIN (default: 2026)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = { if (it.length <= 4) pinInput = it },
                        label = { Text("PIN de Acceso") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_pin_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            onAuthenticate(pinInput)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_login_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.LockOpen, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isEs) "Ingresar con PIN" else "Login with PIN", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {
                            onAuthenticate("2026")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_biometric_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Fingerprint, contentDescription = null, tint = ChurchGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isEs) "Autenticación Biométrica (Huella / Rostro)" else "Biometric Login")
                    }
                }
            }
        }
    } else {
        // Authenticated Admin Dashboard
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag("admin_dashboard_column")
        ) {
            // Admin Bar with Logout
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isEs) "Panel Pastoral • Dios Altísimo" else "Pastoral Admin Panel",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isEs) "Sesión Segura y Cifrada" else "Encrypted Session",
                            fontSize = 10.sp,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión", tint = Color(0xFFE53935))
                    }
                }
            }

            // Admin Sub-tabs
            ScrollableTabRow(
                selectedTabIndex = adminTab,
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Tab(
                    selected = adminTab == 0,
                    onClick = { adminTab = 0 },
                    text = { Text(if (isEs) "Métricas y Reportes" else "Analytics", fontSize = 11.sp) }
                )
                Tab(
                    selected = adminTab == 1,
                    onClick = { adminTab = 1 },
                    text = {
                        val pendingCount = pendingTestimonies.count { !it.isApproved }
                        Text(
                            text = (if (isEs) "Testimonios" else "Testimonies") + if (pendingCount > 0) " ($pendingCount)" else "",
                            fontSize = 11.sp
                        )
                    }
                )
                Tab(
                    selected = adminTab == 2,
                    onClick = { adminTab = 2 },
                    text = { Text(if (isEs) "+ Evento" else "+ Event", fontSize = 11.sp) }
                )
                Tab(
                    selected = adminTab == 3,
                    onClick = { adminTab = 3 },
                    text = { Text(if (isEs) "+ Devocional" else "+ Study", fontSize = 11.sp) }
                )
                Tab(
                    selected = adminTab == 4,
                    onClick = { adminTab = 4 },
                    text = { Text(if (isEs) "Nuevos Miembros" else "Members", fontSize = 11.sp) }
                )
            }

            // Tab Views
            when (adminTab) {
                0 -> {
                    // Analytics & Reports
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = if (isEs) "Reporte General de Congregación y Uso de la App:" else "Congregation & App Analytics:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                AdminStatCard(
                                    title = if (isEs) "Miembros Activos" else "Active Members",
                                    value = "${stats.activeMembers}",
                                    icon = Icons.Default.Groups,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f)
                                )
                                AdminStatCard(
                                    title = if (isEs) "Asistencia Promedio" else "Avg Attendance",
                                    value = "${stats.monthlyAttendanceAvg}",
                                    icon = Icons.Default.TrendingUp,
                                    color = ChurchGold,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                AdminStatCard(
                                    title = if (isEs) "Grupos de Conexión" else "Connect Groups",
                                    value = "${stats.activeConnectGroups}",
                                    icon = Icons.Default.Hub,
                                    color = Color(0xFF0D9488),
                                    modifier = Modifier.weight(1f)
                                )
                                AdminStatCard(
                                    title = if (isEs) "Testimonios Fe" else "Testimonies",
                                    value = "${stats.testimoniesCount}",
                                    icon = Icons.Default.VolunteerActivism,
                                    color = Color(0xFFE53935),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        item {
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = if (isEs) "Recaudación Pro-Templo CENFOL" else "Temple Campaign Progress",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "$37.000.000 COP recaudados de $50.000.000 COP (74%)",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    LinearProgressIndicator(
                                        progress = { 0.74f },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                            .clip(RoundedCornerShape(4.dp)),
                                        color = ChurchGold
                                    )
                                }
                            }
                        }

                        item {
                            Button(
                                onClick = {
                                    val reportMsg = "📊 Reporte Pastoral - Iglesia del Dios Altísimo (CENFOL):\n\n" +
                                            "• Miembros activos: ${stats.activeMembers}\n" +
                                            "• Asistencia mensual promedio: ${stats.monthlyAttendanceAvg}\n" +
                                            "• Grupos de conexión activos: ${stats.activeConnectGroups}\n" +
                                            "• Recaudación Pro-Templo: 74% alcanzado\n\n" +
                                            "Generado desde la App Oficial de la Iglesia del Dios Altísimo."
                                    onShare(reportMsg)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(if (isEs) "Exportar y Compartir Informe Pastoral" else "Share Pastoral Report")
                            }
                        }
                    }
                }

                1 -> {
                    // Pending Testimonies
                    val pending = pendingTestimonies.filter { !it.isApproved }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                text = if (pending.isEmpty()) (if (isEs) "No hay testimonios pendientes por moderar." else "No pending testimonies.")
                                else (if (isEs) "Testimonios pendientes de aprobación:" else "Pending testimonies:"),
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        items(pending, key = { it.id }) { item ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = item.authorName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Surface(
                                            color = Color(0xFFFFF3CD),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = "Pendiente",
                                                color = Color(0xFF856404),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = item.title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = item.story, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = { onApproveTestimony(item.id) },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(if (isEs) "Aprobar y Publicar en el Muro" else "Approve & Publish", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Create Event Form
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                text = if (isEs) "Publicar Nuevo Evento o Culto:" else "Publish New Event:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newEventTitle,
                                onValueChange = { newEventTitle = it },
                                label = { Text("Título del Evento") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = newEventDate,
                                    onValueChange = { newEventDate = it },
                                    label = { Text("Fecha (YYYY-MM-DD)") },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = newEventTime,
                                    onValueChange = { newEventTime = it },
                                    label = { Text("Hora") },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true
                                )
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = newEventLocation,
                                onValueChange = { newEventLocation = it },
                                label = { Text("Lugar / Sede") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newEventSpeaker,
                                onValueChange = { newEventSpeaker = it },
                                label = { Text("Predicador o Ministerio") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newEventDescription,
                                onValueChange = { newEventDescription = it },
                                label = { Text("Descripción del Evento") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp),
                                maxLines = 4
                            )
                        }

                        item {
                            Button(
                                onClick = {
                                    if (newEventTitle.isNotBlank()) {
                                        onAddEvent(
                                            newEventTitle,
                                            newEventDate,
                                            newEventTime,
                                            newEventLocation,
                                            newEventCategory,
                                            newEventDescription,
                                            newEventSpeaker
                                        )
                                        newEventTitle = ""
                                        newEventDescription = ""
                                    }
                                },
                                enabled = newEventTitle.isNotBlank(),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isEs) "Agregar Evento al Calendario" else "Add Event")
                            }
                        }
                    }
                }

                3 -> {
                    // Create Devotional Form
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                text = if (isEs) "Publicar Nuevo Estudio Bíblico Semanal:" else "Publish Weekly Bible Study:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newDevTitle,
                                onValueChange = { newDevTitle = it },
                                label = { Text("Título del Estudio") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newDevVerseRef,
                                onValueChange = { newDevVerseRef = it },
                                label = { Text("Pasaje Bíblico (ej. Salmos 23:1)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newDevVerseText,
                                onValueChange = { newDevVerseText = it },
                                label = { Text("Texto del Versículo") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newDevReflection,
                                onValueChange = { newDevReflection = it },
                                label = { Text("Reflexión Pastoral y Teológica") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                maxLines = 5
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = newDevPractical,
                                onValueChange = { newDevPractical = it },
                                label = { Text("Aplicación Práctica para la Semana") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }

                        item {
                            Button(
                                onClick = {
                                    if (newDevTitle.isNotBlank() && newDevReflection.isNotBlank()) {
                                        onAddDevotional(
                                            newDevTitle,
                                            newDevVerseRef,
                                            newDevVerseText,
                                            newDevReflection,
                                            newDevPractical,
                                            listOf("¿Cómo aplicas este principio hoy?", "¿Qué cambio demanda en tu corazón?"),
                                            newDevAuthor,
                                            "Estudio Pastoral"
                                        )
                                        newDevTitle = ""
                                        newDevReflection = ""
                                    }
                                },
                                enabled = newDevTitle.isNotBlank(),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Publish, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isEs) "Publicar en la App" else "Publish Study")
                            }
                        }
                    }
                }

                4 -> {
                    // Registered Members List
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                text = if (isEs) "Directorio de Nuevos Miembros (${registeredMembers.size}):" else "Registered Members (${registeredMembers.size}):",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        items(registeredMembers, key = { it.id }) { mem ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = mem.fullName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(text = mem.registeredAt, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "📞 ${mem.phone} • ✉️ ${mem.email}", fontSize = 11.sp)
                                    Text(text = "👥 Grupo: ${mem.connectionGroup}", fontSize = 11.sp, color = ChurchGold, fontWeight = FontWeight.SemiBold)
                                    Text(text = "🎯 Interés: ${mem.ministryInterest}", fontSize = 11.sp)
                                    if (mem.prayerNeed.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "🙏 Petición: ${mem.prayerNeed}", fontSize = 11.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
