package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.ChurchViewModel
import com.example.ui.Screen
import com.example.ui.components.ChurchBottomNavBar
import com.example.ui.components.ChurchTopBar
import com.example.ui.screens.*
import com.example.ui.theme.ChurchAppTheme
import com.example.ui.theme.ChurchGold

class MainActivity : ComponentActivity() {
    private val viewModel: ChurchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
            val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
            val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
            val bannerMessage by viewModel.bannerMessage.collectAsStateWithLifecycle()

            val liveService by viewModel.liveService.collectAsStateWithLifecycle()
            val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
            val events by viewModel.events.collectAsStateWithLifecycle()
            val devotionals by viewModel.devotionals.collectAsStateWithLifecycle()
            val testimonies by viewModel.testimonies.collectAsStateWithLifecycle()
            val gallery by viewModel.gallery.collectAsStateWithLifecycle()
            val members by viewModel.members.collectAsStateWithLifecycle()
            val stats by viewModel.stats.collectAsStateWithLifecycle()
            val notifications by viewModel.notifications.collectAsStateWithLifecycle()
            val isAdminAuthenticated by viewModel.isAdminAuthenticated.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val snackbarHostState = remember { SnackbarHostState() }
            var showNotificationsDialog by remember { mutableStateOf(false) }

            // Handle snackbar messages
            LaunchedEffect(bannerMessage) {
                bannerMessage?.let { msg ->
                    snackbarHostState.showSnackbar(msg)
                    viewModel.dismissBanner()
                }
            }

            // Android Hardware / Gesture Back button support
            BackHandler(enabled = currentScreen != Screen.HOME) {
                if (!viewModel.navigateBack()) {
                    finish()
                }
            }

            ChurchAppTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        ChurchTopBar(
                            currentScreen = currentScreen,
                            isDarkTheme = isDarkTheme,
                            currentLanguage = currentLanguage,
                            onNavigateBack = { viewModel.navigateBack() },
                            onNavigateTo = { viewModel.navigateTo(it) },
                            onToggleTheme = { viewModel.toggleTheme() },
                            onOpenNotifications = { showNotificationsDialog = true }
                        )
                    },
                    bottomBar = {
                        ChurchBottomNavBar(
                            currentScreen = currentScreen,
                            currentLanguage = currentLanguage,
                            onNavigateTo = { viewModel.navigateTo(it) }
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.testTag("app_snackbar_host")
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "screen_transition"
                        ) { screen ->
                            when (screen) {
                                Screen.HOME -> HomeScreen(
                                    liveService = liveService,
                                    upcomingEvents = events,
                                    latestDevotional = devotionals.firstOrNull(),
                                    currentLanguage = currentLanguage,
                                    onNavigateTo = { viewModel.navigateTo(it) },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.WEB_PORTAL -> ChurchWebPortalScreen(
                                    liveService = liveService,
                                    events = events,
                                    devotionals = devotionals,
                                    testimonies = testimonies,
                                    gallery = gallery,
                                    stats = stats,
                                    currentLanguage = currentLanguage,
                                    onNavigateTo = { viewModel.navigateTo(it) },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.LIVE -> LiveScreen(
                                    liveService = liveService,
                                    chatMessages = chatMessages,
                                    currentLanguage = currentLanguage,
                                    onSendMessage = { name, msg, isAmen ->
                                        viewModel.sendLiveChat(name, msg, isAmen)
                                    },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.EVENTS -> EventsScreen(
                                    events = events,
                                    currentLanguage = currentLanguage,
                                    onToggleRegistration = { viewModel.toggleEventRegistration(it) },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.DEVOTIONALS -> DevotionalsScreen(
                                    devotionals = devotionals,
                                    currentLanguage = currentLanguage,
                                    onToggleRead = { viewModel.toggleDevotionalRead(it) },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.GIVING -> GivingScreen(
                                    currentLanguage = currentLanguage,
                                    onSubmitDonation = { name, cat, amount, isRec, freq, method ->
                                        viewModel.submitDonation(name, cat, amount, isRec, freq, method)
                                    },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.GALLERY -> GalleryScreen(
                                    galleryItems = gallery,
                                    currentLanguage = currentLanguage,
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.TESTIMONIES -> TestimoniesScreen(
                                    testimonies = testimonies,
                                    currentLanguage = currentLanguage,
                                    onToggleLike = { viewModel.toggleLikeTestimony(it) },
                                    onSubmitTestimony = { author, title, cat, story ->
                                        viewModel.submitTestimony(author, title, cat, story)
                                    },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.REGISTER -> RegisterMemberScreen(
                                    currentLanguage = currentLanguage,
                                    onRegister = { name, email, phone, age, addr, min, grp, bap, pray ->
                                        viewModel.registerMember(name, email, phone, age, addr, min, grp, bap, pray)
                                    },
                                    onNavigateBack = { viewModel.navigateBack() },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.SUPPORT -> SupportScreen(
                                    currentLanguage = currentLanguage,
                                    onLanguageChange = { viewModel.setLanguage(it) },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.ADMIN -> AdminScreen(
                                    isAuthenticated = isAdminAuthenticated,
                                    stats = stats,
                                    pendingTestimonies = testimonies,
                                    registeredMembers = members,
                                    currentLanguage = currentLanguage,
                                    onAuthenticate = { viewModel.authenticateAdmin(it) },
                                    onLogout = { viewModel.logoutAdmin() },
                                    onApproveTestimony = { viewModel.approveTestimony(it) },
                                    onAddEvent = { t, d, time, loc, cat, desc, spk ->
                                        viewModel.addEvent(t, d, time, loc, cat, desc, spk)
                                    },
                                    onAddDevotional = { t, vr, vt, ref, pr, q, aut, cat ->
                                        viewModel.addDevotional(t, vr, vt, ref, pr, q, aut, cat)
                                    },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                                Screen.SETTINGS -> SettingsScreen(
                                    isDarkTheme = isDarkTheme,
                                    currentLanguage = currentLanguage,
                                    onToggleTheme = { viewModel.toggleTheme() },
                                    onLanguageChange = { viewModel.setLanguage(it) },
                                    onShare = { text -> viewModel.shareContent(context, text) }
                                )
                            }
                        }
                    }

                    // Notifications Dialog
                    if (showNotificationsDialog) {
                        AlertDialog(
                            onDismissRequest = { showNotificationsDialog = false },
                            icon = {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = ChurchGold)
                            },
                            title = {
                                Text(
                                    text = if (currentLanguage == "ES") "Notificaciones Comunitarias" else "Community Notifications",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            text = {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    items(notifications) { notif ->
                                        Card(
                                            shape = RoundedCornerShape(8.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                            )
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(text = notif.title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                                    Text(text = notif.timestamp, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                                }
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(text = notif.body, fontSize = 11.sp)
                                            }
                                        }
                                    }
                                }
                            },
                            confirmButton = {
                                Button(onClick = { showNotificationsDialog = false }) {
                                    Text(if (currentLanguage == "ES") "Entendido" else "Got it")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
