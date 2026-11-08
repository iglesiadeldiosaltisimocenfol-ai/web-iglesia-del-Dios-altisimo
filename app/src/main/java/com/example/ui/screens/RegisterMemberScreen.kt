package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ChurchGold

@Composable
fun RegisterMemberScreen(
    currentLanguage: String,
    onRegister: (String, String, String, String, String, String, String, Boolean, String) -> Unit,
    onNavigateBack: () -> Unit,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var ageGroup by remember { mutableStateOf("Jóvenes (18-28)") }
    var address by remember { mutableStateOf("") }
    var ministryInterest by remember { mutableStateOf("Ministerio de Alabanza") }
    var connectionGroup by remember { mutableStateOf("CENFOL Grupo Norte") }
    var isBaptized by remember { mutableStateOf(false) }
    var prayerNeed by remember { mutableStateOf("") }

    var isSuccessDialogShown by remember { mutableStateOf(false) }

    val ageGroups = listOf("Jóvenes (15-28)", "Adultos (29-50)", "Adultos Mayores (50+)")
    val ministries = listOf(
        "Ministerio de Alabanza",
        "Medios y Transmisión",
        "Escuela Bíblica Infantil",
        "Ujieres y Bienvenida",
        "Misiones y Acción Social",
        "Oración e Intercesión"
    )
    val connectGroups = listOf(
        "CENFOL Grupo Norte",
        "CENFOL Grupo Sur",
        "CENFOL Valle del Lili",
        "CENFOL Grupo Jóvenes",
        "Grupo Virtual / Online"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("register_member_screen_column"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        color = ChurchGold,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (isEs) "MEMBRESÍA & DISCIPULADO" else "MEMBERSHIP & DISCIPLESHIP",
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isEs) "¡Bienvenido a la Familia del Dios Altísimo!" else "Welcome to Most High God Family!",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isEs) "Nos alegra tenerte en casa. Completa tus datos para integrarte a un grupo de conexión CENFOL y ser pastoreado."
                        else "Fill out your details to join a connection group and receive pastoral care.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE2E8F0))
                    )
                }
            }
        }

        // Section 1: Personal Info
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isEs) "1. Información Personal y de Contacto:" else "1. Personal Contact Info:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text(if (isEs) "Nombre y Apellidos completos *" else "Full Name *") },
                    modifier = Modifier.fillMaxWidth().testTag("member_name_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth().testTag("member_email_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(if (isEs) "Teléfono / WhatsApp *" else "Phone / WhatsApp *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth().testTag("member_phone_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(if (isEs) "Dirección y Barrio" else "Address / City") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        // Section 2: Grupo de Edad
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isEs) "2. Rango de Edad:" else "2. Age Range:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ageGroups.forEach { group ->
                        val isSelected = ageGroup == group
                        OutlinedButton(
                            onClick = { ageGroup = group },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                            )
                        ) {
                            Text(
                                text = group,
                                fontSize = 11.sp,
                                maxLines = 1,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Section 3: Ministerio e Intereses
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isEs) "3. ¿En qué ministerio te gustaría servir?" else "3. Which ministry would you like to serve in?",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                var expandedMinistry by remember { mutableStateOf(false) }

                Box {
                    OutlinedButton(
                        onClick = { expandedMinistry = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(ministryInterest, modifier = Modifier.weight(1f))
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = expandedMinistry,
                        onDismissRequest = { expandedMinistry = false }
                    ) {
                        ministries.forEach { m ->
                            DropdownMenuItem(
                                text = { Text(m) },
                                onClick = {
                                    ministryInterest = m
                                    expandedMinistry = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Section 4: Grupo de Conexión CENFOL
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isEs) "4. Grupo de Conexión CENFOL más cercano:" else "4. Connection Group:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                var expandedGroup by remember { mutableStateOf(false) }

                Box {
                    OutlinedButton(
                        onClick = { expandedGroup = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(connectionGroup, modifier = Modifier.weight(1f))
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = expandedGroup,
                        onDismissRequest = { expandedGroup = false }
                    ) {
                        connectGroups.forEach { g ->
                            DropdownMenuItem(
                                text = { Text(g) },
                                onClick = {
                                    connectionGroup = g
                                    expandedGroup = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Section 5: Bautismo en agua
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEs) "¿Has sido bautizado en agua en el nombre del Padre, Hijo y Espíritu Santo?"
                            else "Have you been water baptized?",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isBaptized) (if (isEs) "Sí, ya he sido bautizado(a)" else "Yes, already baptized")
                            else (if (isEs) "No / Deseo dar el paso de bautismo" else "No / Want to be baptized"),
                            fontSize = 11.sp,
                            color = ChurchGold,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Switch(
                        checked = isBaptized,
                        onCheckedChange = { isBaptized = it },
                        modifier = Modifier.testTag("baptized_switch")
                    )
                }
            }
        }

        // Section 6: Prayer Request
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isEs) "5. ¿Tienes alguna necesidad especial de oración?" else "5. Prayer Request:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = prayerNeed,
                    onValueChange = { prayerNeed = it },
                    placeholder = { Text(if (isEs) "Escribe tu petición para que el cuerpo pastoral ore por ti..." else "Share your prayer request...", fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 4
                )
            }
        }

        // Action Button: Submit Registration
        item {
            Button(
                onClick = {
                    if (fullName.isNotBlank() && phone.isNotBlank()) {
                        onRegister(
                            fullName,
                            email,
                            phone,
                            ageGroup,
                            address,
                            ministryInterest,
                            connectionGroup,
                            isBaptized,
                            prayerNeed
                        )
                        isSuccessDialogShown = true
                    }
                },
                enabled = fullName.isNotBlank() && phone.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_registration_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEs) "Completar Registro de Membresía" else "Complete Registration",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }

    // Success Dialog
    if (isSuccessDialogShown) {
        AlertDialog(
            onDismissRequest = {
                isSuccessDialogShown = false
                onNavigateBack()
            },
            icon = {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(48.dp))
            },
            title = {
                Text(
                    text = if (isEs) "¡Registro Exitoso!" else "Registration Successful!",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (isEs) "Bienvenido(a) $fullName a la Iglesia del Dios Altísimo (CENFOL)."
                        else "Welcome $fullName to Most High God Church.",
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isEs) "Hemos asignado tus datos al líder del '$connectionGroup'. En breve te contactaremos para darte una cálida bienvenida."
                        else "Your data has been sent to the group leader.",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val shareText = "✝️ ¡Me acabo de registrar como miembro en la Iglesia del Dios Altísimo (CENFOL)! Una familia de fe y esperanza. ¡Te invito a unirte a nosotros!"
                        onShare(shareText)
                        isSuccessDialogShown = false
                        onNavigateBack()
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isEs) "Compartir en WhatsApp" else "Share on WhatsApp")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isSuccessDialogShown = false
                    onNavigateBack()
                }) {
                    Text("OK")
                }
            }
        )
    }
}
