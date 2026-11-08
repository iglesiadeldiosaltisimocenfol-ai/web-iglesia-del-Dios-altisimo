package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.ChurchGold

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    currentLanguage: String,
    onToggleTheme: () -> Unit,
    onLanguageChange: (String) -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"

    var notifLive by remember { mutableStateOf(true) }
    var notifDevotional by remember { mutableStateOf(true) }
    var notifEvents by remember { mutableStateOf(true) }
    var notifDailyVerse by remember { mutableStateOf(true) }
    var biometricSecurity by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("settings_screen_column"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Church Identity Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_church_logo_1794096727873),
                        contentDescription = "Emblema Dios Altísimo",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "Iglesia del Dios Altísimo",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "CENFOL Colombia • Sede Central",
                            fontSize = 12.sp,
                            color = ChurchGold,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Versión de la App 1.0.0 (Build 2026)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Section: Visual & Language Preferences
        item {
            Text(
                text = if (isEs) "Apariencia e Idioma" else "Appearance & Language",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Dark Mode Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isEs) "Modo Oscuro" else "Dark Theme",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = if (isDarkTheme) (if (isEs) "Activado (Ahorro de batería y descanso visual)" else "Enabled")
                                    else (if (isEs) "Desactivado (Tema claro diurno)" else "Disabled"),
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { onToggleTheme() },
                            modifier = Modifier.testTag("dark_mode_switch")
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    // Language Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = ChurchGold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (isEs) "Idioma de la Aplicación" else "Application Language",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            FilterChip(
                                selected = currentLanguage == "ES",
                                onClick = { onLanguageChange("ES") },
                                label = { Text("Español", fontSize = 11.sp) }
                            )
                            FilterChip(
                                selected = currentLanguage == "EN",
                                onClick = { onLanguageChange("EN") },
                                label = { Text("English", fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }
        }

        // Section: Push Notifications Settings
        item {
            Text(
                text = if (isEs) "Notificaciones Push Personalizadas" else "Personalized Push Notifications",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    NotificationSettingRow(
                        title = if (isEs) "Transmisiones en Vivo (Cultos)" else "Live Services Alerts",
                        checked = notifLive,
                        onCheckedChange = { notifLive = it }
                    )
                    HorizontalDivider()
                    NotificationSettingRow(
                        title = if (isEs) "Nuevo Devocional y Estudio Bíblico" else "Weekly Devotional Alerts",
                        checked = notifDevotional,
                        onCheckedChange = { notifDevotional = it }
                    )
                    HorizontalDivider()
                    NotificationSettingRow(
                        title = if (isEs) "Recordatorios de Eventos y Vigilias" else "Event & Prayer Night Reminders",
                        checked = notifEvents,
                        onCheckedChange = { notifEvents = it }
                    )
                    HorizontalDivider()
                    NotificationSettingRow(
                        title = if (isEs) "Versículo Bíblico Inspiracional del Día" else "Daily Inspirational Bible Verse",
                        checked = notifDailyVerse,
                        onCheckedChange = { notifDailyVerse = it }
                    )
                }
            }
        }

        // Section: Security & Biometrics
        item {
            Text(
                text = if (isEs) "Seguridad y Privacidad" else "Security & Privacy",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Fingerprint, contentDescription = null, tint = Color(0xFF2E7D32))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isEs) "Protección Biométrica / Huella" else "Biometric Security Protection",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = if (isEs) "Para confirmar ofrendas y acceder al panel" else "For giving and admin verification",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Switch(
                        checked = biometricSecurity,
                        onCheckedChange = { biometricSecurity = it }
                    )
                }
            }
        }

        // Section: Share App
        item {
            Button(
                onClick = {
                    val shareMsg = "✝️ Te invito a descargar la App de la Iglesia del Dios Altísimo (CENFOL):\n\n" +
                            "• Transmisiones de cultos en vivo todos los domingos\n" +
                            "• Estudios bíblicos y devocionales semanales\n" +
                            "• Calendario interactivo de eventos y vigilias\n" +
                            "• Muro de testimonios y peticiones de oración\n" +
                            "• Donaciones y ofrendas seguras\n\n" +
                            "¡Comparte esta bendición en tus grupos de WhatsApp y redes sociales!"
                    onShare(shareMsg)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isEs) "Compartir App en Grupos de WhatsApp" else "Share App with Friends")
            }
        }
    }
}

@Composable
fun NotificationSettingRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 12.sp, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
