package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChurchEvent
import com.example.ui.theme.ChurchGold

@Composable
fun EventsScreen(
    events: List<ChurchEvent>,
    currentLanguage: String,
    onToggleRegistration: (String) -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var selectedCategory by remember { mutableStateOf("Todos") }
    var selectedDayIndex by remember { mutableStateOf(0) }
    var detailEvent by remember { mutableStateOf<ChurchEvent?>(null) }

    val categories = listOf("Todos", "Jóvenes", "Oración", "Familia", "Acción Social", "Servicio Principal")

    val calendarDays = listOf(
        Pair("HOY", "07 Nov"),
        Pair("DOM", "08 Nov"),
        Pair("VIE", "20 Nov"),
        Pair("VIE", "27 Nov"),
        Pair("SÁB", "05 Dic"),
        Pair("DOM", "06 Dic"),
        Pair("SÁB", "12 Dic")
    )

    val filteredEvents = remember(selectedCategory, events) {
        if (selectedCategory == "Todos") events
        else events.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("events_screen_column")
    ) {
        // Interactive Calendar Date Selector Bar
        Surface(
            tonalElevation = 3.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEs) "Calendario de Actividades 2026" else "2026 Activities Calendar",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Nov - Dic",
                        style = MaterialTheme.typography.labelSmall,
                        color = ChurchGold,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(calendarDays.size) { index ->
                        val (dayLabel, dateStr) = calendarDays[index]
                        val isSelected = selectedDayIndex == index

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                                .clickable { selectedDayIndex = index }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dayLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dateStr,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isSelected) ChurchGold else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Category Filter Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = { Text(category, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ChurchGold,
                        selectedLabelColor = Color.Black
                    )
                )
            }
        }

        // Events List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredEvents, key = { it.id }) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { detailEvent = event }
                        .testTag("event_card_${event.id}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = event.category,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.People,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${event.attendeesCount} " + (if (isEs) "asistentes" else "attending"),
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = event.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = ChurchGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${event.date} • ${event.time}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = event.location,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    val shareMsg = "📅 Invitación especial - Iglesia del Dios Altísimo:\n\n" +
                                            "Evento: ${event.title}\n" +
                                            "Fecha: ${event.date} a las ${event.time}\n" +
                                            "Lugar: ${event.location}\n" +
                                            "Descripción: ${event.description}\n\n" +
                                            "¡Te esperamos con toda tu familia! Más info en nuestra App oficial."
                                    onShare(shareMsg)
                                },
                                modifier = Modifier.testTag("share_event_${event.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir evento en WhatsApp",
                                    tint = Color(0xFF25D366)
                                )
                            }

                            Button(
                                onClick = { onToggleRegistration(event.id) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (event.isRegistered) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.testTag("register_event_button_${event.id}")
                            ) {
                                Icon(
                                    imageVector = if (event.isRegistered) Icons.Default.Check else Icons.Default.EventAvailable,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (event.isRegistered) {
                                        if (isEs) "¡Agendado!" else "Attending"
                                    } else {
                                        if (isEs) "Asistir / Agendar" else "Join Event"
                                    },
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Detail Dialog
    detailEvent?.let { event ->
        AlertDialog(
            onDismissRequest = { detailEvent = null },
            title = {
                Text(text = event.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            },
            text = {
                Column {
                    Text(text = "📅 Fecha: ${event.date} - ${event.time}", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "📍 Lugar: ${event.location}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "🎤 Expositor: ${event.speaker}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onToggleRegistration(event.id)
                        detailEvent = null
                    }
                ) {
                    Text(if (event.isRegistered) "Cancelar Asistencia" else "Confirmar Asistencia")
                }
            },
            dismissButton = {
                TextButton(onClick = { detailEvent = null }) {
                    Text("Cerrar")
                }
            }
        )
    }
}
