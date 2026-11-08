package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.R
import com.example.model.GalleryItem
import com.example.ui.theme.ChurchGold

@Composable
fun GalleryScreen(
    galleryItems: List<GalleryItem>,
    currentLanguage: String,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var selectedCategory by remember { mutableStateOf("Todos") }
    var selectedItem by remember { mutableStateOf<GalleryItem?>(null) }

    val categories = listOf("Todos", "Alabanza", "Jóvenes", "Niños", "Misiones", "Bautismos")

    val filteredItems = remember(selectedCategory, galleryItems) {
        if (selectedCategory == "Todos") galleryItems
        else galleryItems.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("gallery_screen_column")
    ) {
        // Header
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEs) "Galería de Actividades y Ministerios" else "Church Activities Gallery",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = if (isEs) "Momentos de fe, servicio y comunidad en la Iglesia del Dios Altísimo"
                else "Moments of faith, service and fellowship",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Category filter chips
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

        // Photo Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredItems, key = { it.id }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { selectedItem = item }
                        .testTag("gallery_card_${item.id}"),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Image
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.church_hero_banner_1794096739680)
                        )

                        // Bottom Gradient & Title
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color(0xB3000000), Color(0xE60D1E3A)),
                                        startY = 100f
                                    )
                                )
                        )

                        // Category Badge
                        Surface(
                            color = ChurchGold,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = item.category,
                                color = Color.Black,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        // Caption
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = item.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = item.date,
                                color = Color(0xFFCBD5E1),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // Full-Screen Image Dialog
    selectedItem?.let { item ->
        Dialog(onDismissRequest = { selectedItem = null }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.church_hero_banner_1794096739680)
                        )

                        IconButton(
                            onClick = { selectedItem = null },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .background(Color(0x88000000), RoundedCornerShape(20.dp))
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = item.category,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(text = item.date, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                val shareText = "📸 Fotografía de actividad - Iglesia del Dios Altísimo (CENFOL):\n\n" +
                                        "\"${item.title}\" - ${item.activityName}\n" +
                                        "${item.description}\n\n" +
                                        "¡Conoce más de nuestras actividades en la app oficial!"
                                onShare(shareText)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isEs) "Compartir en WhatsApp" else "Share on WhatsApp")
                        }
                    }
                }
            }
        }
    }
}
