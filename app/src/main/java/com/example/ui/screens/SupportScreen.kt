package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ChurchGold

@Composable
fun SupportScreen(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    onShare: (String) -> Unit
) {
    val context = LocalContext.current
    val isEs = currentLanguage == "ES"

    var prayerRequesterName by remember { mutableStateOf("") }
    var prayerMessage by remember { mutableStateOf("") }
    var isUrgent by remember { mutableStateOf(false) }
    var prayerSubmitted by remember { mutableStateOf(false) }

    var expandedFaqIndex by remember { mutableStateOf<Int?>(null) }

    val faqs = listOf(
        Pair(
            if (isEs) "¿Cuáles son los horarios de los cultos presenciales y online?"
            else "What are the in-person and online service times?",
            if (isEs) "Los servicios dominicales son a las 8:00 AM y 10:30 AM (con transmisión en vivo en la App). Los viernes a las 7:00 PM tenemos Culto de Jóvenes y Noche de Oración y Milagros."
            else "Sunday services are at 8:00 AM and 10:30 AM (with Live streaming on the App). Fridays at 7:00 PM we host Youth Service & Miracle Prayer Night."
        ),
        Pair(
            if (isEs) "¿Cómo me integro a un Grupo de Conexión CENFOL?"
            else "How do I join a CENFOL Connection Group?",
            if (isEs) "Puedes registrarte en la sección 'Nuevo Miembro' de la app seleccionando tu sector más cercano (Norte, Sur, Cali o Virtual), y un líder de discipulado se comunicará contigo."
            else "Register in the 'New Member' screen in the app, and a leader will connect with you."
        ),
        Pair(
            if (isEs) "¿Cómo puedo solicitar bautismos en agua o presentación de niños?"
            else "How can I request baptism or baby dedication?",
            if (isEs) "Escribe directamente al WhatsApp pastoral desde el botón de abajo o marca la opción en tu registro de membresía. Realizamos bautismos cada dos meses en el río Pance."
            else "Contact pastoral WhatsApp below. We hold water baptisms every two months."
        ),
        Pair(
            if (isEs) "¿Es segura la donación y cómo obtengo mi certificado tributario?"
            else "Are donations secure and how do I get a tax receipt?",
            if (isEs) "Todas las ofrendas y diezmos cuentan con cifrado de grado bancario TLS 1.3. Al finalizar tu donación obtienes un recibo digital con código único que puedes descargar o enviar a tu correo."
            else "All transactions are secured with TLS 1.3 banking encryption. You receive an instant digital receipt."
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("support_screen_lazy_column"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Support Header & Language Toggle
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = ChurchGold,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = if (isEs) "SOPORTE Y CUIDADO PASTORAL" else "SUPPORT & PASTORAL CARE",
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        // Multilingual switcher
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FilterChip(
                                selected = currentLanguage == "ES",
                                onClick = { onLanguageChange("ES") },
                                label = { Text("ES", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ChurchGold,
                                    selectedLabelColor = Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            FilterChip(
                                selected = currentLanguage == "EN",
                                onClick = { onLanguageChange("EN") },
                                label = { Text("EN", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ChurchGold,
                                    selectedLabelColor = Color.Black
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isEs) "¿Cómo podemos servirte hoy?" else "How can we serve you today?",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isEs) "Nuestro equipo pastoral y técnico está disponible para atender tus inquietudes, orientarte y orar por tus necesidades."
                        else "Our pastoral and tech team is ready to assist and pray for you.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE2E8F0))
                    )
                }
            }
        }

        // Direct Contact Action Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // WhatsApp Button
                Button(
                    onClick = {
                        try {
                            val uri = Uri.parse("https://wa.me/573123456789?text=Hola,%20me%20comunico%20desde%20la%20App%20de%20la%20Iglesia%20del%20Dios%20Altisimo%20CENFOL")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            onShare("Hola pastoral CENFOL, me comunico desde la App de la Iglesia del Dios Altísimo")
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("WhatsApp", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                // Email Button
                Button(
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:iglesiadeldiosaltisimocenfol@gmail.com")
                                putExtra(Intent.EXTRA_SUBJECT, "Consulta desde App Iglesia del Dios Altísimo")
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            onShare("Para: iglesiadeldiosaltisimocenfol@gmail.com")
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isEs) "Correo" else "Email", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Confidential Prayer Request Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(ChurchGold.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = ChurchGold, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isEs) "Petición de Oración Confidencial" else "Confidential Prayer Request",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = if (isEs) "Leída únicamente por los pastores principales" else "Read only by senior pastors",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = prayerRequesterName,
                        onValueChange = { prayerRequesterName = it },
                        label = { Text(if (isEs) "Tu nombre completo" else "Your name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = prayerMessage,
                        onValueChange = { prayerMessage = it },
                        label = { Text(if (isEs) "Motivo de oración (Sanidad, Familia, Finanzas, Trabajo...)" else "Prayer concern...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isEs) "Marcar como caso urgente" else "Mark as urgent case",
                            fontSize = 12.sp
                        )
                        Switch(
                            checked = isUrgent,
                            onCheckedChange = { isUrgent = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            if (prayerMessage.isNotBlank()) {
                                prayerSubmitted = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = prayerMessage.isNotBlank(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isEs) "Enviar Petición a Intercesión" else "Send Prayer Request")
                    }

                    if (prayerSubmitted) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isEs) "✓ Tu petición ha sido recibida y el equipo de intercesión estará orando por ti."
                            else "✓ Request received. Intercession team will pray for you.",
                            color = Color(0xFF2E7D32),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // FAQs Section
        item {
            Text(
                text = if (isEs) "Preguntas Frecuentes (FAQ):" else "Frequently Asked Questions:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(faqs.size) { index ->
            val (question, answer) = faqs[index]
            val isExpanded = expandedFaqIndex == index

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedFaqIndex = if (isExpanded) null else index }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = question,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = answer,
                                style = MaterialTheme.typography.bodySmall,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
