package com.example.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ChurchRepository
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    WEB_PORTAL,
    LIVE,
    EVENTS,
    DEVOTIONALS,
    GIVING,
    GALLERY,
    TESTIMONIES,
    REGISTER,
    SUPPORT,
    ADMIN,
    SETTINGS
}

class ChurchViewModel(
    private val repository: ChurchRepository = ChurchRepository.getInstance()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _navigationStack = MutableStateFlow(listOf(Screen.HOME))

    val isDarkTheme: StateFlow<Boolean> = repository.isDarkTheme
    val currentLanguage: StateFlow<String> = repository.currentLanguage
    val isAdminAuthenticated: StateFlow<Boolean> = repository.isAdminAuthenticated

    val liveService: StateFlow<LiveService> = repository.liveService
    val chatMessages: StateFlow<List<ChatMessage>> = repository.chatMessages
    val events: StateFlow<List<ChurchEvent>> = repository.events
    val devotionals: StateFlow<List<Devotional>> = repository.devotionals
    val testimonies: StateFlow<List<Testimony>> = repository.testimonies
    val gallery: StateFlow<List<GalleryItem>> = repository.gallery
    val members: StateFlow<List<MemberRegistration>> = repository.members
    val donations: StateFlow<List<DonationRecord>> = repository.donations
    val notifications: StateFlow<List<AppNotification>> = repository.notifications
    val stats: StateFlow<ChurchStats> = repository.stats

    // Notification modal / toast state
    private val _bannerMessage = MutableStateFlow<String?>(null)
    val bannerMessage: StateFlow<String?> = _bannerMessage.asStateFlow()

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            _navigationStack.value = _navigationStack.value + screen
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        val stack = _navigationStack.value
        return if (stack.size > 1) {
            val newStack = stack.dropLast(1)
            _navigationStack.value = newStack
            _currentScreen.value = newStack.last()
            true
        } else {
            false
        }
    }

    fun showBanner(msg: String) {
        _bannerMessage.value = msg
    }

    fun dismissBanner() {
        _bannerMessage.value = null
    }

    fun toggleTheme() {
        repository.toggleDarkTheme()
    }

    fun setLanguage(lang: String) {
        repository.setLanguage(lang)
    }

    fun authenticateAdmin(pin: String): Boolean {
        val success = repository.authenticateAdmin(pin)
        if (success) {
            showBanner(if (currentLanguage.value == "ES") "Sesión de Administrador iniciada correctamente" else "Admin session started successfully")
        } else {
            showBanner(if (currentLanguage.value == "ES") "PIN incorrecto. Intente '2026'" else "Incorrect PIN. Try '2026'")
        }
        return success
    }

    fun logoutAdmin() {
        repository.logoutAdmin()
        showBanner("Sesión cerrada")
    }

    fun sendLiveChat(name: String, msg: String, isAmen: Boolean = false) {
        repository.sendChatMessage(name, msg, isAmen)
    }

    fun registerMember(
        name: String,
        email: String,
        phone: String,
        age: String,
        address: String,
        ministry: String,
        group: String,
        baptized: Boolean,
        prayerNeed: String
    ) {
        repository.registerMember(name, email, phone, age, address, ministry, group, baptized, prayerNeed)
        showBanner(if (currentLanguage.value == "ES") "¡Bienvenido a la familia! Registro guardado con éxito." else "Welcome to the family! Registration saved.")
    }

    fun submitDonation(
        name: String,
        category: String,
        amount: Double,
        isRecurring: Boolean,
        frequency: String,
        paymentMethod: String
    ): DonationRecord {
        val record = repository.submitDonation(name, category, amount, isRecurring, frequency, paymentMethod)
        showBanner(if (currentLanguage.value == "ES") "Ofrenda procesada con éxito. ¡Dios bendiga tu generosidad!" else "Donation processed successfully. God bless you!")
        return record
    }

    fun toggleEventRegistration(eventId: String) {
        repository.toggleEventRegistration(eventId)
    }

    fun addEvent(
        title: String,
        date: String,
        time: String,
        location: String,
        category: String,
        description: String,
        speaker: String
    ) {
        repository.addEvent(title, date, time, location, category, description, speaker)
        showBanner("Nuevo evento publicado en el calendario")
    }

    fun submitTestimony(author: String, title: String, category: String, story: String) {
        repository.submitTestimony(author, title, category, story)
        showBanner(if (currentLanguage.value == "ES") "Testimonio enviado para revisión pastoral. ¡Gracias por compartir!" else "Testimony submitted for pastoral review.")
    }

    fun toggleLikeTestimony(id: String) {
        repository.toggleLikeTestimony(id)
    }

    fun approveTestimony(id: String) {
        repository.approveTestimony(id)
        showBanner("Testimonio aprobado y publicado en el muro")
    }

    fun toggleDevotionalRead(id: String) {
        repository.toggleDevotionalRead(id)
    }

    fun addDevotional(
        title: String,
        verseRef: String,
        verseText: String,
        reflection: String,
        practical: String,
        questions: List<String>,
        author: String,
        category: String
    ) {
        repository.addDevotional(title, verseRef, verseText, reflection, practical, questions, author, category)
        showBanner("Nuevo devocional y estudio bíblico publicado")
    }

    // Share action helper
    fun shareContent(context: Context, text: String, title: String = "Compartir desde Iglesia del Dios Altísimo") {
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, title).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            showBanner("Error al abrir selector de compartir")
        }
    }
}
