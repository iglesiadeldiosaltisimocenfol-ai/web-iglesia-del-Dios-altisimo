package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.Screen
import com.example.ui.theme.ChurchGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChurchTopBar(
    currentScreen: Screen,
    isDarkTheme: Boolean,
    currentLanguage: String,
    onNavigateBack: () -> Unit,
    onNavigateTo: (Screen) -> Unit,
    onToggleTheme: () -> Unit,
    onOpenNotifications: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                if (currentScreen == Screen.HOME) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_church_logo_1794096727873),
                        contentDescription = "Logo Iglesia del Dios Altísimo",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Dios Altísimo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "CENFOL Comunidad Cristiana",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ChurchGold,
                                fontWeight = FontWeight.SemiBold
                            ),
                            maxLines = 1
                        )
                    }
                } else {
                    val titleText = when (currentScreen) {
                        Screen.WEB_PORTAL -> if (currentLanguage == "ES") "Página Web Oficial" else "Official Website"
                        Screen.LIVE -> if (currentLanguage == "ES") "Transmisión En Vivo" else "Live Broadcast"
                        Screen.EVENTS -> if (currentLanguage == "ES") "Eventos y Calendario" else "Events & Calendar"
                        Screen.DEVOTIONALS -> if (currentLanguage == "ES") "Estudios Bíblicos" else "Bible Studies"
                        Screen.GIVING -> if (currentLanguage == "ES") "Diezmos y Ofrendas" else "Giving & Tithes"
                        Screen.GALLERY -> if (currentLanguage == "ES") "Galería de Actividades" else "Photo Gallery"
                        Screen.TESTIMONIES -> if (currentLanguage == "ES") "Testimonios de Fe" else "Testimonies"
                        Screen.REGISTER -> if (currentLanguage == "ES") "Registro de Miembros" else "Member Registration"
                        Screen.SUPPORT -> if (currentLanguage == "ES") "Atención y Oración" else "Care & Support"
                        Screen.ADMIN -> if (currentLanguage == "ES") "Panel Pastoral" else "Admin Panel"
                        Screen.SETTINGS -> if (currentLanguage == "ES") "Configuración" else "Settings"
                        else -> "Dios Altísimo"
                    }
                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            if (currentScreen != Screen.HOME) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }
            }
        },
        actions = {
            // Theme toggle
            IconButton(
                onClick = onToggleTheme,
                modifier = Modifier.testTag("theme_toggle_button")
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Cambiar tema oscuro/claro"
                )
            }

            // Notifications
            IconButton(
                onClick = onOpenNotifications,
                modifier = Modifier.testTag("notifications_button")
            ) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = ChurchGold) {
                            Text("3", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificaciones"
                    )
                }
            }

            // Web Portal View Toggle
            IconButton(
                onClick = {
                    if (currentScreen == Screen.WEB_PORTAL) onNavigateTo(Screen.HOME)
                    else onNavigateTo(Screen.WEB_PORTAL)
                },
                modifier = Modifier.testTag("toggle_web_portal_button")
            ) {
                Icon(
                    imageVector = if (currentScreen == Screen.WEB_PORTAL) Icons.Default.PhoneAndroid else Icons.Default.Language,
                    contentDescription = if (currentScreen == Screen.WEB_PORTAL) "Ver Modo Móvil" else "Ver Página Web Oficial",
                    tint = if (currentScreen == Screen.WEB_PORTAL) ChurchGold else MaterialTheme.colorScheme.onSurface
                )
            }

            // More options dropdown
            Box {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.testTag("more_menu_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones"
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(if (currentLanguage == "ES") "Galería de Fotos" else "Photo Gallery") },
                        leadingIcon = { Icon(Icons.Outlined.PhotoLibrary, contentDescription = null) },
                        onClick = {
                            showMenu = false
                            onNavigateTo(Screen.GALLERY)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(if (currentLanguage == "ES") "Testimonios de Fe" else "Testimonies") },
                        leadingIcon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null) },
                        onClick = {
                            showMenu = false
                            onNavigateTo(Screen.TESTIMONIES)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(if (currentLanguage == "ES") "Nuevo Miembro" else "New Member") },
                        leadingIcon = { Icon(Icons.Outlined.PersonAdd, contentDescription = null) },
                        onClick = {
                            showMenu = false
                            onNavigateTo(Screen.REGISTER)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(if (currentLanguage == "ES") "Soporte y Oración" else "Support & Prayer") },
                        leadingIcon = { Icon(Icons.Outlined.SupportAgent, contentDescription = null) },
                        onClick = {
                            showMenu = false
                            onNavigateTo(Screen.SUPPORT)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(if (currentLanguage == "ES") "Panel Administrador" else "Admin Panel") },
                        leadingIcon = { Icon(Icons.Outlined.AdminPanelSettings, contentDescription = null) },
                        onClick = {
                            showMenu = false
                            onNavigateTo(Screen.ADMIN)
                        }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text(if (currentLanguage == "ES") "Configuración" else "Settings") },
                        leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        onClick = {
                            showMenu = false
                            onNavigateTo(Screen.SETTINGS)
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun ChurchBottomNavBar(
    currentScreen: Screen,
    currentLanguage: String,
    onNavigateTo: (Screen) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        val isEs = currentLanguage == "ES"

        NavigationBarItem(
            selected = currentScreen == Screen.HOME,
            onClick = { onNavigateTo(Screen.HOME) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == Screen.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Inicio"
                )
            },
            label = { Text(if (isEs) "Inicio" else "Home", fontSize = 11.sp) },
            modifier = Modifier.testTag("nav_item_home")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.LIVE,
            onClick = { onNavigateTo(Screen.LIVE) },
            icon = {
                BadgedBox(
                    badge = {
                        Badge(containerColor = Color(0xFFE53935)) {
                            Text("LIVE", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (currentScreen == Screen.LIVE) Icons.Filled.LiveTv else Icons.Outlined.LiveTv,
                        contentDescription = "En Vivo"
                    )
                }
            },
            label = { Text(if (isEs) "En Vivo" else "Live", fontSize = 11.sp) },
            modifier = Modifier.testTag("nav_item_live")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.EVENTS,
            onClick = { onNavigateTo(Screen.EVENTS) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == Screen.EVENTS) Icons.Filled.Event else Icons.Outlined.Event,
                    contentDescription = "Eventos"
                )
            },
            label = { Text(if (isEs) "Eventos" else "Events", fontSize = 11.sp) },
            modifier = Modifier.testTag("nav_item_events")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.DEVOTIONALS,
            onClick = { onNavigateTo(Screen.DEVOTIONALS) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == Screen.DEVOTIONALS) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                    contentDescription = "Estudios"
                )
            },
            label = { Text(if (isEs) "Estudios" else "Studies", fontSize = 11.sp) },
            modifier = Modifier.testTag("nav_item_devotionals")
        )

        NavigationBarItem(
            selected = currentScreen == Screen.GIVING,
            onClick = { onNavigateTo(Screen.GIVING) },
            icon = {
                Icon(
                    imageVector = if (currentScreen == Screen.GIVING) Icons.Filled.VolunteerActivism else Icons.Outlined.VolunteerActivism,
                    contentDescription = "Ofrendar"
                )
            },
            label = { Text(if (isEs) "Ofrendar" else "Giving", fontSize = 11.sp) },
            modifier = Modifier.testTag("nav_item_giving")
        )
    }
}
