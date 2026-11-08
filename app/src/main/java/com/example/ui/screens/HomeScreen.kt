package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ChurchEvent
import com.example.model.Devotional
import com.example.model.LiveService
import com.example.ui.Screen
import com.example.ui.theme.ChurchGold
import com.example.ui.theme.ChurchNavyPrimary

@Composable
fun HomeScreen(
    liveService: LiveService,
    upcomingEvents: List<ChurchEvent>,
    latestDevotional: Devotional?,
    currentLanguage: String,
    onNavigateTo: (Screen) -> Unit,
    onShare: (String) -> Unit
) {
    val context = LocalContext.current
    val isEs = currentLanguage == "ES"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_lazy_column"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Sanctuary Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.church_hero_banner_1794096739680),
                    contentDescription = "Santuario de Adoración Iglesia del Dios Altísimo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x88000000),
                                    Color(0xE60D1E3A)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Surface(
                        color = ChurchGold,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (isEs) "CENFOL FAMILIA DE FE" else "CENFOL COMMUNITY",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isEs) "Iglesia del Dios Altísimo" else "Most High God Church",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (isEs) "Un lugar para adorar, restaurar tu familia y crecer juntos" else "A home to worship, restore families and grow together",
                        color = Color(0xFFE2E8F0),
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Live Service Quick Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable { onNavigateTo(Screen.LIVE) }
                    .testTag("home_live_banner_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Ver Transmisión",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🔴 " + (if (isEs) "TRANSMISIÓN EN VIVO" else "LIVE BROADCAST"),
                                color = Color(0xFFE53935),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${liveService.viewerCount} " + (if (isEs) "conectados" else "watching"),
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = liveService.title,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = liveService.preacher,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Ir a En Vivo",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Web Portal Switch Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable { onNavigateTo(Screen.WEB_PORTAL) }
                    .testTag("home_web_portal_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(ChurchGold.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Página Web",
                            tint = ChurchGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEs) "🌐 Portal Web Oficial de la Iglesia" else "🌐 Official Church Web Portal",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isEs) "Toca para ver el diseño completo en formato página web" else "Tap to view in full website format",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    FilledTonalButton(
                        onClick = { onNavigateTo(Screen.WEB_PORTAL) },
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(if (isEs) "Ver Web" else "View Web", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Daily Biblical Verse Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("daily_verse_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoStories,
                                contentDescription = null,
                                tint = ChurchGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEs) "VERSÍCULO DEL DÍA" else "VERSE OF THE DAY",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ChurchGold
                                )
                            )
                        }

                        IconButton(
                            onClick = {
                                val textToShare = "📖 Versículo del día - Iglesia del Dios Altísimo:\n\n" +
                                        "\"El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente. Diré yo a Jehová: Esperanza mía, y castillo mío; Mi Dios, en quien confiaré.\"\n— Salmos 91:1-2 (RVR1960)\n\n" +
                                        "¡Únete a nuestra comunidad en la app de la Iglesia del Dios Altísimo!"
                                onShare(textToShare)
                            },
                            modifier = Modifier.size(32.dp).testTag("share_daily_verse_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir versículo",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "\"El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente. Diré yo a Jehová: Esperanza mía, y castillo mío; Mi Dios, en quien confiaré.\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Salmos 91:1-2 (RVR1960)",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }

        // Quick Action Shortcuts Grid
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = if (isEs) "Accesos Rápidos" else "Quick Actions",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionButton(
                        title = if (isEs) "Ofrendar" else "Give",
                        subtitle = if (isEs) "Diezmos y ofrendas" else "Tithes & offerings",
                        icon = Icons.Default.VolunteerActivism,
                        color = ChurchGold,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.GIVING) }
                    )
                    QuickActionButton(
                        title = if (isEs) "Nuevo Miembro" else "Register",
                        subtitle = if (isEs) "Únete a la familia" else "Join connection group",
                        icon = Icons.Default.PersonAdd,
                        color = Color(0xFF0D9488),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.REGISTER) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionButton(
                        title = if (isEs) "Testimonios" else "Testimonies",
                        subtitle = if (isEs) "Historias de fe" else "Stories of faith",
                        icon = Icons.Default.Favorite,
                        color = Color(0xFFE53935),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.TESTIMONIES) }
                    )
                    QuickActionButton(
                        title = if (isEs) "Galería" else "Gallery",
                        subtitle = if (isEs) "Fotos de actividades" else "Church photos",
                        icon = Icons.Default.PhotoLibrary,
                        color = Color(0xFF3F51B5),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.GALLERY) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionButton(
                        title = if (isEs) "Petición Oración" else "Prayer Request",
                        subtitle = if (isEs) "Atención pastoral" else "Pastoral care",
                        icon = Icons.Default.ContactSupport,
                        color = Color(0xFF8E24AA),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTo(Screen.SUPPORT) }
                    )
                    QuickActionButton(
                        title = if (isEs) "WhatsApp" else "WhatsApp",
                        subtitle = if (isEs) "Compartir comunidad" else "Share with groups",
                        icon = Icons.Default.Share,
                        color = Color(0xFF25D366),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val msg = "¡Hola! Te invito a conocer la Iglesia del Dios Altísimo (CENFOL). Conéctate a los cultos en vivo, estudios bíblicos y actividades descargando nuestra App oficial: Dios Altísimo."
                            onShare(msg)
                        }
                    )
                }
            }
        }

        // Upcoming Event Highlight
        item {
            val nextEvent = upcomingEvents.firstOrNull()
            if (nextEvent != null) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isEs) "Próximo Evento" else "Upcoming Event",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        TextButton(onClick = { onNavigateTo(Screen.EVENTS) }) {
                            Text(if (isEs) "Ver Todos" else "View All", fontSize = 12.sp)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateTo(Screen.EVENTS) },
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
                                        text = nextEvent.category,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                                Text(
                                    text = "📅 ${nextEvent.date} • ${nextEvent.time}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = nextEvent.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = nextEvent.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📍 ${nextEvent.location}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                FilledTonalButton(
                                    onClick = { onNavigateTo(Screen.EVENTS) },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(if (isEs) "Detalles" else "Details", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Latest Bible Study / Devotional
        if (latestDevotional != null) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isEs) "Estudio Bíblico de la Semana" else "Weekly Bible Study",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        TextButton(onClick = { onNavigateTo(Screen.DEVOTIONALS) }) {
                            Text(if (isEs) "Ver devocionales" else "View studies", fontSize = 12.sp)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateTo(Screen.DEVOTIONALS) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = latestDevotional.week,
                                style = MaterialTheme.typography.labelSmall,
                                color = ChurchGold,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = latestDevotional.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "📖 ${latestDevotional.verseRef}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = latestDevotional.reflection,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
