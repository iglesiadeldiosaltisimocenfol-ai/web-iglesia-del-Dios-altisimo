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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Testimony
import com.example.ui.theme.ChurchGold

@Composable
fun TestimoniesScreen(
    testimonies: List<Testimony>,
    currentLanguage: String,
    onToggleLike: (String) -> Unit,
    onSubmitTestimony: (String, String, String, String) -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var selectedCategory by remember { mutableStateOf("Todos") }
    var showSubmitDialog by remember { mutableStateOf(false) }

    // Dialog state
    var authorInput by remember { mutableStateOf("") }
    var titleInput by remember { mutableStateOf("") }
    var categoryInput by remember { mutableStateOf("Sanidad Divina") }
    var storyInput by remember { mutableStateOf("") }

    val categories = listOf("Todos", "Sanidad Divina", "Restauración Familiar", "Transformación", "Provisión")

    val approvedTestimonies = remember(selectedCategory, testimonies) {
        val base = testimonies.filter { it.isApproved }
        if (selectedCategory == "Todos") base
        else base.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showSubmitDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.testTag("submit_testimony_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isEs) "Compartir Mi Testimonio" else "Share Testimony", fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("testimonies_screen_lazy_column"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            item {
                Column {
                    Text(
                        text = if (isEs) "Muro de Testimonios y Milagros" else "Testimonies & Miracles",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (isEs) "\"Y ellos le han vencido por medio de la sangre del Cordero y de la palabra del testimonio de ellos...\" — Apocalipsis 12:11"
                        else "\"They triumphed over him by the blood of the Lamb and by the word of their testimony...\"",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(categories) { cat ->
                            val isSelected = selectedCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ChurchGold,
                                    selectedLabelColor = Color.Black
                                )
                            )
                        }
                    }
                }
            }

            // Testimonies List
            items(approvedTestimonies, key = { it.id }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("testimony_card_${item.id}"),
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(ChurchGold.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.authorName.take(1).uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = ChurchGold
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = item.authorName,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = item.date,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = item.category,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = item.story,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { onToggleLike(item.id) },
                                modifier = Modifier.testTag("like_testimony_${item.id}")
                            ) {
                                Icon(
                                    imageVector = if (item.hasLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Alabar a Dios por este milagro",
                                    tint = if (item.hasLiked) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${item.likes} " + (if (isEs) "¡Gloria a Dios!" else "Praise God"),
                                    fontSize = 12.sp,
                                    fontWeight = if (item.hasLiked) FontWeight.Bold else FontWeight.Normal,
                                    color = if (item.hasLiked) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurface
                                )
                            }

                            IconButton(
                                onClick = {
                                    val shareText = "🙌 Testimonio de Fe - Iglesia del Dios Altísimo (CENFOL):\n\n" +
                                            "\"${item.title}\"\nPor: ${item.authorName}\nCategoría: ${item.category}\n\n" +
                                            "${item.story}\n\n" +
                                            "¡Dios sigue haciendo milagros hoy! Conoce más en nuestra App."
                                    onShare(shareText)
                                },
                                modifier = Modifier.testTag("share_testimony_${item.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir testimonio en WhatsApp",
                                    tint = Color(0xFF25D366)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }

    // Modal to Submit Testimony
    if (showSubmitDialog) {
        AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title = {
                Text(
                    text = if (isEs) "Cuéntanos lo que Dios hizo en tu vida" else "Share what God has done",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = authorInput,
                        onValueChange = { authorInput = it },
                        label = { Text(if (isEs) "Tu nombre completo" else "Your full name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { titleInput = it },
                        label = { Text(if (isEs) "Título del testimonio" else "Testimony title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = storyInput,
                        onValueChange = { storyInput = it },
                        label = { Text(if (isEs) "Escribe tu historia detallada..." else "Describe your story...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 6
                    )

                    Text(
                        text = if (isEs) "* Tu testimonio será revisado por el equipo pastoral antes de publicarse en el muro general."
                        else "* Your testimony will be reviewed by pastors before publishing.",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (titleInput.isNotBlank() && storyInput.isNotBlank()) {
                            onSubmitTestimony(
                                authorInput.ifBlank { "Miembro de la Congregación" },
                                titleInput,
                                categoryInput,
                                storyInput
                            )
                            authorInput = ""
                            titleInput = ""
                            storyInput = ""
                            showSubmitDialog = false
                        }
                    }
                ) {
                    Text(if (isEs) "Enviar Testimonio" else "Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitDialog = false }) {
                    Text(if (isEs) "Cancelar" else "Cancel")
                }
            }
        )
    }
}
