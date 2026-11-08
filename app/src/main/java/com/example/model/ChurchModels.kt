package com.example.model

data class ChurchEvent(
    val id: String,
    val title: String,
    val date: String, // e.g. "2026-11-15"
    val time: String, // e.g. "10:00 AM"
    val location: String,
    val category: String, // e.g. "Servicio Principal", "Jóvenes", "Oración", "Discipulado"
    val description: String,
    val speaker: String,
    val attendeesCount: Int,
    val isRegistered: Boolean = false
)

data class Devotional(
    val id: String,
    val title: String,
    val week: String,
    val date: String,
    val verseRef: String,
    val verseText: String,
    val reflection: String,
    val practicalApplication: String,
    val studyQuestions: List<String>,
    val author: String,
    val category: String,
    val isRead: Boolean = false
)

data class Testimony(
    val id: String,
    val authorName: String,
    val date: String,
    val category: String, // "Sanidad", "Familia", "Provisión", "Restauración"
    val title: String,
    val story: String,
    val isApproved: Boolean = true,
    val likes: Int = 0,
    val hasLiked: Boolean = false
)

data class LiveService(
    val id: String,
    val title: String,
    val preacher: String,
    val status: String, // "EN VIVO", "PRÓXIMO", "GRABADO"
    val viewerCount: Int,
    val scheduledTime: String,
    val biblicalText: String,
    val summary: String
)

data class ChatMessage(
    val id: String,
    val userName: String,
    val message: String,
    val timestamp: String,
    val isAmen: Boolean = false
)

data class MemberRegistration(
    val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val ageGroup: String,
    val address: String,
    val ministryInterest: String,
    val connectionGroup: String,
    val isBaptized: Boolean,
    val prayerNeed: String,
    val registeredAt: String
)

data class DonationRecord(
    val id: String,
    val donorName: String,
    val category: String, // "Diezmo", "Ofrenda General", "Misiones CENFOL", "Templo Sagrado"
    val amount: Double,
    val isRecurring: Boolean,
    val frequency: String, // "Única vez", "Semanal", "Quincenal", "Mensual"
    val paymentMethod: String, // "Tarjeta Débito/Crédito", "Transferencia / PSE", "Efectivo"
    val status: String,
    val date: String,
    val receiptNumber: String
)

data class GalleryItem(
    val id: String,
    val title: String,
    val activityName: String,
    val date: String,
    val description: String,
    val category: String,
    val imageUrl: String
)

data class ChurchStats(
    val activeMembers: Int,
    val monthlyAttendanceAvg: Int,
    val testimoniesCount: Int,
    val offeringGoalProgress: Float,
    val currentOfferings: Double,
    val offeringTarget: Double,
    val activeConnectGroups: Int
)

data class AppNotification(
    val id: String,
    val title: String,
    val body: String,
    val timestamp: String,
    val type: String, // "EVENT", "DEVOTIONAL", "ANNOUNCEMENT", "LIVE"
    val isRead: Boolean = false
)
