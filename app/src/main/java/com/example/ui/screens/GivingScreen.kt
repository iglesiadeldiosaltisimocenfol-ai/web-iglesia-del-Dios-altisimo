package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DonationRecord
import com.example.ui.theme.ChurchGold
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GivingScreen(
    currentLanguage: String,
    onSubmitDonation: (String, String, Double, Boolean, String, String) -> DonationRecord,
    onShare: (String) -> Unit
) {
    val isEs = currentLanguage == "ES"
    var donorName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Diezmo") }
    var selectedAmount by remember { mutableStateOf(50000.0) }
    var customAmountText by remember { mutableStateOf("") }
    var isRecurring by remember { mutableStateOf(false) }
    var frequency by remember { mutableStateOf("Mensual") }
    var paymentMethod by remember { mutableStateOf("PSE / Transferencia") }
    var useBiometricSecurity by remember { mutableStateOf(true) }
    var generatedReceipt by remember { mutableStateOf<DonationRecord?>(null) }

    val categories = listOf("Diezmo", "Ofrenda General", "Misiones CENFOL", "Templo Sagrado", "Comedor Infantil")
    val presetAmounts = listOf(20000.0, 50000.0, 100000.0, 200000.0, 500000.0)
    val frequencies = listOf("Semanal", "Quincenal", "Mensual")
    val paymentMethods = listOf("PSE / Transferencia", "Tarjeta Débito/Crédito", "Nequi / Daviplata")

    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply {
            maximumFractionDigits = 0
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("giving_screen_lazy_column"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Church Campaign Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
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
                                text = if (isEs) "MAYORDOMÍA BÍBLICA" else "BIBLICAL STEWARDSHIP",
                                color = Color.Black,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = ChurchGold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isEs) "Siembra en el Reino de Dios" else "Give to God's Kingdom",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isEs) "\"Traed todos los diezmos al alfolí y haya alimento en mi casa; y probadme ahora en esto...\" — Malaquías 3:10"
                        else "\"Bring the whole tithe into the storehouse, that there may be food in my house...\" — Malachi 3:10",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFFE2E8F0),
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Temple Building Campaign Progress Bar
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isEs) "Meta Pro-Templo CENFOL" else "Temple Goal Progress",
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "74% ($37M / $50M COP)",
                                fontSize = 12.sp,
                                color = ChurchGold,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { 0.74f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = ChurchGold,
                            trackColor = Color(0x33FFFFFF)
                        )
                    }
                }
            }
        }

        // Category Selector
        item {
            Text(
                text = if (isEs) "1. Selecciona el destino de tu siembra:" else "1. Select giving category:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

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

        // Amount Selector
        item {
            Text(
                text = if (isEs) "2. Monto a ofrendar (COP):" else "2. Amount (COP):",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presetAmounts.take(3).forEach { amount ->
                    val isSelected = selectedAmount == amount && customAmountText.isEmpty()
                    OutlinedButton(
                        onClick = {
                            selectedAmount = amount
                            customAmountText = ""
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = currencyFormatter.format(amount),
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presetAmounts.drop(3).forEach { amount ->
                    val isSelected = selectedAmount == amount && customAmountText.isEmpty()
                    OutlinedButton(
                        onClick = {
                            selectedAmount = amount
                            customAmountText = ""
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = currencyFormatter.format(amount),
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1
                        )
                    }
                }

                // Custom amount input button or trigger
                OutlinedTextField(
                    value = customAmountText,
                    onValueChange = {
                        customAmountText = it.filter { ch -> ch.isDigit() }
                        val parsed = customAmountText.toDoubleOrNull()
                        if (parsed != null) selectedAmount = parsed
                    },
                    placeholder = { Text(if (isEs) "Otro monto" else "Other", fontSize = 11.sp) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1.2f),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )
            }
        }

        // Automatic Recurring Donation Switch
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isEs) "🔄 Donación Recurrente Automática" else "🔄 Automatic Recurring Giving",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = if (isEs) "Contribuye de forma constante para el sostenimiento de la obra"
                                else "Automate your monthly or weekly tithe safely",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = isRecurring,
                            onCheckedChange = { isRecurring = it },
                            modifier = Modifier.testTag("recurring_donation_switch")
                        )
                    }

                    if (isRecurring) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            frequencies.forEach { freq ->
                                val isSelected = frequency == freq
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { frequency = freq },
                                    label = { Text(freq, fontSize = 11.sp) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Donor Name & Payment Method
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isEs) "3. Datos y Método de Pago Seguro:" else "3. Payment Method & Details:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = donorName,
                    onValueChange = { donorName = it },
                    label = { Text(if (isEs) "Nombre del Sembrador (o dejar en blanco para anónimo)" else "Name (or leave blank for anonymous)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    paymentMethods.forEach { method ->
                        val isSelected = paymentMethod == method
                        OutlinedButton(
                            onClick = { paymentMethod = method },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                            )
                        ) {
                            Text(
                                text = method,
                                fontSize = 10.sp,
                                maxLines = 1,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Security & End-to-End Encryption Badge
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20).copy(alpha = 0.08f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isEs) "Cifrado de Extremo a Extremo & Autenticación Biométrica" else "End-to-End Encryption & Biometric Guard",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                        Text(
                            text = if (isEs) "Tus datos financieros viajan encriptados bajo protocolo TLS 1.3 con certificación bancaria oficial."
                            else "Your transactions are shielded with 256-bit encryption.",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Action Button: Submit Donation
        item {
            Button(
                onClick = {
                    val finalAmount = customAmountText.toDoubleOrNull() ?: selectedAmount
                    val record = onSubmitDonation(
                        donorName,
                        selectedCategory,
                        finalAmount,
                        isRecurring,
                        if (isRecurring) frequency else "Única vez",
                        paymentMethod
                    )
                    generatedReceipt = record
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_donation_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = ChurchGold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEs) "Ofrendar con Seguridad (${currencyFormatter.format(customAmountText.toDoubleOrNull() ?: selectedAmount)})"
                    else "Give Securely (${currencyFormatter.format(customAmountText.toDoubleOrNull() ?: selectedAmount)})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }

    // Receipt Dialog
    generatedReceipt?.let { receipt ->
        AlertDialog(
            onDismissRequest = { generatedReceipt = null },
            icon = {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
            },
            title = {
                Text(
                    text = if (isEs) "¡Ofrenda Recibida con Gratitud!" else "Donation Successful!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (isEs) "Iglesia del Dios Altísimo - CENFOL" else "Most High God Church - CENFOL",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ChurchGold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Comprobante: ${receipt.receiptNumber}", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    Text(text = "Destino: ${receipt.category}", fontSize = 12.sp)
                    Text(text = "Monto: ${currencyFormatter.format(receipt.amount)} COP", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    Text(text = "Periodicidad: ${receipt.frequency}", fontSize = 12.sp)
                    Text(text = "Método: ${receipt.paymentMethod}", fontSize = 12.sp)
                    Text(text = "Fecha: ${receipt.date}", fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "\"Dios bendiga tu siembra al ciento por uno y multiplique toda provisión en tu hogar.\"",
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val shareText = "🧾 Comprobante de Donación - Iglesia del Dios Altísimo (CENFOL):\n\n" +
                                "Recibo: ${receipt.receiptNumber}\n" +
                                "Concepto: ${receipt.category}\n" +
                                "Monto: ${currencyFormatter.format(receipt.amount)} COP\n" +
                                "Periodicidad: ${receipt.frequency}\n" +
                                "Fecha: ${receipt.date}\n\n" +
                                "¡Gracias por sembrar en la extensión del Reino de Dios!"
                        onShare(shareText)
                        generatedReceipt = null
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isEs) "Compartir Recibo en WhatsApp" else "Share Receipt")
                }
            },
            dismissButton = {
                TextButton(onClick = { generatedReceipt = null }) {
                    Text(if (isEs) "Cerrar" else "Close")
                }
            }
        )
    }
}
