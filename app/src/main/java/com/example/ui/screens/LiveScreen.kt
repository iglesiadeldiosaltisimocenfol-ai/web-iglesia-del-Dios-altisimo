package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ChatMessage
import com.example.model.LiveService
import com.example.ui.theme.ChurchGold
import kotlinx.coroutines.launch

@Composable
fun LiveScreen(
    liveService: LiveService,
    chatMessages: List<ChatMessage>,
    currentLanguage: String,
    onSendMessage: (String, String, Boolean) -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var isPlaying by remember { mutableStateOf(true) }
    var isMuted by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) } // 0: Chat en vivo, 1: Notas y Pasajes, 2: Servicios Anteriores
    var messageInput by remember { mutableStateOf("") }
    var userNameInput by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val chatListState = rememberLazyListState()

    // Auto-scroll chat to latest
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            chatListState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("live_screen_column")
    ) {
        // Video Player Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.church_hero_banner_1794096739680),
                contentDescription = "Transmisión en vivo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Dim overlay when paused
            if (!isPlaying) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x99000000)),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { isPlaying = true },
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(ChurchGold)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Reanudar transmisión",
                            tint = Color.Black,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }

            // Top Status Overlay (Red LIVE dot + Viewers count)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xE6E53935))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isEs) "EN VIVO" else "LIVE",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x88000000))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${liveService.viewerCount}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Bottom Controls Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC000000))
                        )
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { isPlaying = !isPlaying },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = { isMuted = !isMuted },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = "Silenciar",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "1080p HD",
                        color = ChurchGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0x44FFFFFF))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                IconButton(
                    onClick = {
                        val shareText = "🔴 ¡Conéctate ahora a la transmisión en vivo de la Iglesia del Dios Altísimo!\n\n" +
                                "Culto: ${liveService.title}\n" +
                                "Predica: ${liveService.preacher}\n" +
                                "Pasaje: ${liveService.biblicalText}\n\n" +
                                "¡Dios tiene una palabra poderosa para tu vida hoy! Conéctate en la app."
                        onShare(shareText)
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir transmisión",
                        tint = Color.White
                    )
                }
            }
        }

        // Live Title & Pastor Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = liveService.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "🎤 ${liveService.preacher} • ${liveService.scheduledTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = liveService.biblicalText,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                )
            }
        }

        // Tabs (Chat / Notas / Anteriores)
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(if (isEs) "Chat en Vivo" else "Live Chat", fontSize = 12.sp) },
                icon = { Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(if (isEs) "Notas Bíblicas" else "Sermon Notes", fontSize = 12.sp) },
                icon = { Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text(if (isEs) "Grabaciones" else "Archive", fontSize = 12.sp) },
                icon = { Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp)) }
            )
        }

        // Tab Content
        when (selectedTab) {
            0 -> {
                // Interactive Live Chat
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    // Chat Messages Stream
                    LazyColumn(
                        state = chatListState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(chatMessages, key = { it.id }) { msg ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (msg.isAmen) ChurchGold.copy(alpha = 0.12f)
                                        else MaterialTheme.colorScheme.surface
                                    )
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(if (msg.isAmen) ChurchGold else MaterialTheme.colorScheme.primary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = msg.userName.take(1).uppercase(),
                                        color = if (msg.isAmen) Color.Black else Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = msg.userName,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (msg.isAmen) ChurchGold else MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = msg.timestamp,
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        text = msg.message,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    // Quick Reactions Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AssistChip(
                            onClick = {
                                onSendMessage(
                                    userNameInput.ifBlank { "Hermano(a)" },
                                    "¡Amén y Gloria a Dios! 🙌🙏",
                                    true
                                )
                            },
                            label = { Text("¡Amén! 🙌", fontSize = 11.sp) },
                            modifier = Modifier.testTag("reaction_amen_chip")
                        )
                        AssistChip(
                            onClick = {
                                onSendMessage(
                                    userNameInput.ifBlank { "Hermano(a)" },
                                    "¡Dios es Bueno y Fiel! ✨",
                                    true
                                )
                            },
                            label = { Text("¡Dios es Fiel! ✨", fontSize = 11.sp) }
                        )
                        AssistChip(
                            onClick = {
                                onSendMessage(
                                    userNameInput.ifBlank { "Hermano(a)" },
                                    "Pido oración por mi familia ❤️",
                                    false
                                )
                            },
                            label = { Text("Oración ❤️", fontSize = 11.sp) }
                        )
                    }

                    // Message Input Row
                    Surface(
                        tonalElevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            if (userNameInput.isEmpty()) {
                                OutlinedTextField(
                                    value = userNameInput,
                                    onValueChange = { userNameInput = it },
                                    placeholder = { Text(if (isEs) "Tu nombre (opcional)" else "Your name (optional)", fontSize = 12.sp) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(46.dp),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = messageInput,
                                    onValueChange = { messageInput = it },
                                    placeholder = { Text(if (isEs) "Escribe tu saludo o amén..." else "Type a blessing...", fontSize = 13.sp) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("live_chat_input"),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                IconButton(
                                    onClick = {
                                        if (messageInput.isNotBlank()) {
                                            onSendMessage(
                                                userNameInput.ifBlank { "Hermano(a)" },
                                                messageInput,
                                                false
                                            )
                                            messageInput = ""
                                        }
                                    },
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                        .testTag("send_chat_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Enviar mensaje",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                // Sermon Notes Tab
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (isEs) "Puntos Clave del Mensaje" else "Sermon Key Points",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "1. El Abrigo de Dios no es un refugio temporal, es una habitación permanente.\n\n" +
                                            "2. Bajo la sombra del Omnipotente encontramos descanso en medio de la tormenta.\n\n" +
                                            "3. Con nuestras palabras declaramos esperanza y desatamos el favor de Dios sobre nuestras familias.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                val text = "📝 Notas del Culto - Iglesia del Dios Altísimo:\n\n" +
                                        "\"${liveService.title}\"\nPredicador: ${liveService.preacher}\nPasaje: ${liveService.biblicalText}\n\n" +
                                        "Puntos clave:\n1. El Abrigo de Dios es habitación permanente.\n2. Descanso bajo la sombra del Omnipotente.\n3. Confesión de fe sobre nuestra familia."
                                onShare(text)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isEs) "Compartir Notas por WhatsApp" else "Share Notes on WhatsApp")
                        }
                    }
                }
            }
            2 -> {
                // Archive of past recordings
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val pastServices = listOf(
                        Triple("Culto de Sanidad y Libertad", "01 Nov 2026 • Pastor David Morales", "Isaías 53:4-5"),
                        Triple("Vigilia de Intercesión Nacional", "25 Oct 2026 • Pastora Martha Morales", "2 Crónicas 7:14"),
                        Triple("Domingo de Pacto y Restauración", "18 Oct 2026 • Pastor Invitado Andrés", "Jeremías 29:11"),
                        Triple("Congreso de Alabanza 'Adoradores en Espíritu'", "11 Oct 2026 • Ministerio Levitas", "Juan 4:23-24")
                    )

                    items(pastServices) { (title, subtitle, passage) ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = "📖 $passage", fontSize = 11.sp, color = ChurchGold, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
