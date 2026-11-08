package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Devotional
import com.example.ui.theme.ChurchGold

@Composable
fun DevotionalsScreen(
    devotionals: List<Devotional>,
    currentLanguage: String,
    onToggleRead: (String) -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var searchQuery by remember { mutableStateOf("") }
    var expandedDevId by remember { mutableStateOf<String?>(devotionals.firstOrNull()?.id) }
    var isAudioPlaying by remember { mutableStateOf(false) }

    val filteredDevotionals = remember(searchQuery, devotionals) {
        if (searchQuery.isBlank()) devotionals
        else devotionals.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.verseRef.contains(searchQuery, ignoreCase = true) ||
            it.category.contains(searchQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("devotionals_screen_lazy_column"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Search and Header
        item {
            Column {
                Text(
                    text = if (isEs) "Estudios Bíblicos y Devocionales Semanales" else "Weekly Bible Studies & Devotionals",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = if (isEs) "Edificando tu vida y tu grupo de conexión CENFOL en la Palabra" else "Building your life and connection group on the Word",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(if (isEs) "Buscar por título, pasaje o tema..." else "Search by title, verse...", fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        // Devotionals List
        items(filteredDevotionals, key = { it.id }) { dev ->
            val isExpanded = expandedDevId == dev.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("devotional_card_${dev.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (dev.isRead) MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    else MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = ChurchGold.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = dev.week,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (dev.isRead) {
                                Surface(
                                    color = Color(0xFF2E7D32).copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "✓ " + (if (isEs) "Leído" else "Read"),
                                        color = Color(0xFF2E7D32),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                            }

                            IconButton(
                                onClick = {
                                    val shareMsg = "📖 Estudio Bíblico Semanal - Iglesia del Dios Altísimo:\n\n" +
                                            "Tema: ${dev.title}\n" +
                                            "Pasaje: ${dev.verseRef}\n\n" +
                                            "\"${dev.verseText}\"\n\n" +
                                            "Reflexión:\n${dev.reflection}\n\n" +
                                            "¡Comparte esta bendición con tu grupo de conexión!"
                                    onShare(shareMsg)
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir estudio",
                                    tint = Color(0xFF25D366),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = dev.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.clickable {
                            expandedDevId = if (isExpanded) null else dev.id
                        }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "📖 ${dev.verseRef} • Autor: ${dev.author}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Scripture Verse Card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "\"${dev.verseText}\"",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                lineHeight = 20.sp
                            ),
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Reflection
                    Text(
                        text = dev.reflection,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                        lineHeight = 22.sp
                    )

                    // Expanded Section: Practical Application & Small Group Questions
                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            // Audio Study Player Mockup
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = { isAudioPlaying = !isAudioPlaying },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(ChurchGold)
                                    ) {
                                        Icon(
                                            imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                            contentDescription = "Escuchar audio estudio",
                                            tint = Color.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = if (isEs) "Audio Devocional Pastoral" else "Pastoral Audio Study",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                        Text(
                                            text = if (isAudioPlaying) "Reproduciendo audio de reflexión (5:20 min)" else "Duración: 5:20 min • Toca para escuchar",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = if (isEs) "💡 Aplicación Práctica para la Semana:" else "💡 Practical Application:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = ChurchGold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = dev.practicalApplication,
                                style = MaterialTheme.typography.bodySmall,
                                lineHeight = 20.sp
                            )

                            if (dev.studyQuestions.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = if (isEs) "👥 Preguntas para el Grupo de Conexión CENFOL:" else "👥 Connection Group Questions:",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                dev.studyQuestions.forEachIndexed { i, q ->
                                    Text(
                                        text = "${i + 1}. $q",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { expandedDevId = if (isExpanded) null else dev.id }
                        ) {
                            Text(
                                text = if (isExpanded) (if (isEs) "Ver menos" else "Show less") else (if (isEs) "Leer estudio completo" else "Read full study"),
                                fontSize = 12.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { onToggleRead(dev.id) },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = if (dev.isRead) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (dev.isRead) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (dev.isRead) (if (isEs) "Leído" else "Read") else (if (isEs) "Marcar Leído" else "Mark Read"),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
