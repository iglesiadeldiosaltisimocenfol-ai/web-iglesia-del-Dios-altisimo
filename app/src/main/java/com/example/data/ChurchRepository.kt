package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class ChurchRepository {

    // --- Dynamic State Flows ---
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _currentLanguage = MutableStateFlow("ES") // "ES" or "EN"
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _isAdminAuthenticated = MutableStateFlow(false)
    val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

    private val _liveService = MutableStateFlow(
        LiveService(
            id = "live_001",
            title = "Culto de Alabanza, Adoración y Palabra de Vida",
            preacher = "Pastor Principal David Morales",
            status = "EN VIVO",
            viewerCount = 428,
            scheduledTime = "Domingo 10:00 AM (En Vivo)",
            biblicalText = "Salmos 91:1-4 | 'El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente'",
            summary = "Un mensaje transformador sobre el refugio, la protección divina y el poder del Dios Altísimo en tiempos de desafío."
        )
    )
    val liveService: StateFlow<LiveService> = _liveService.asStateFlow()

    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage("1", "Hermana Gloria", "¡Gloria a Dios! Bendiciones a toda la congregación desde Cali 🙌", "10:14 AM"),
            ChatMessage("2", "Carlos Gómez", "Amén pastor, hermosa palabra de bendición.", "10:17 AM", isAmen = true),
            ChatMessage("3", "Familia Restrepo", "Conectados en familia alabando al Altísimo. ¡Dios es fiel!", "10:20 AM"),
            ChatMessage("4", "Pastora Elena", "Un saludo a todos nuestros hermanos conectados en Colombia y el mundo ❤️", "10:22 AM"),
            ChatMessage("5", "Andrés Martínez", "¡Amén! Pido oración por la salud de mi madre María.", "10:25 AM")
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _events = MutableStateFlow(
        listOf(
            ChurchEvent(
                id = "evt_1",
                title = "Congreso de Avivamiento Juvenil 'Fuego y Pasión'",
                date = "2026-11-20",
                time = "06:30 PM",
                location = "Auditorio Principal CENFOL",
                category = "Jóvenes",
                description = "Tres días de búsqueda profunda, música en vivo, conferencistas invitados y clamor por nuestra generación.",
                speaker = "Pastor Andrés & Equipo Alabanza",
                attendeesCount = 185,
                isRegistered = false
            ),
            ChurchEvent(
                id = "evt_2",
                title = "Vigilia de Oración e Intercesión por las Familias",
                date = "2026-11-27",
                time = "08:00 PM",
                location = "Templo Central & Transmisión Online",
                category = "Oración",
                description = "Clamor unido por sanidad, restauración de hogares, protección y derramamiento del Espíritu Santo.",
                speaker = "Cuerpo Pastoral CENFOL",
                attendeesCount = 310,
                isRegistered = true
            ),
            ChurchEvent(
                id = "evt_3",
                title = "Cena de Matrimonios 'Pacto de Amor y Gracia'",
                date = "2026-12-05",
                time = "07:00 PM",
                location = "Salón de Banquetes Los Olivos",
                category = "Familia",
                description = "Una noche inolvidable para renovar votos, fortalecer la comunicación conyugal y principios bíblicos en el hogar.",
                speaker = "Pastores David y Martha Morales",
                attendeesCount = 94,
                isRegistered = false
            ),
            ChurchEvent(
                id = "evt_4",
                title = "Gran Jornada Social y Misionera en la Comunidad",
                date = "2026-12-12",
                time = "09:00 AM",
                location = "Sede Comunitaria El Porvenir",
                category = "Acción Social",
                description = "Entrega de mercados, brigada de salud, refrigerios para niños y proclamación de la Buena Nueva de Jesús.",
                speaker = "Ministerio de Misericordia",
                attendeesCount = 120,
                isRegistered = false
            ),
            ChurchEvent(
                id = "evt_5",
                title = "Servicio Dominical de Santa Cena y Milagros",
                date = "2026-12-06",
                time = "10:00 AM",
                location = "Templo Dios Altísimo",
                category = "Servicio Principal",
                description = "Conmemoración solemne del sacrificio de Cristo en la cruz, tiempo de testimonios y unción fresca.",
                speaker = "Pastor David Morales",
                attendeesCount = 520,
                isRegistered = true
            )
        )
    )
    val events: StateFlow<List<ChurchEvent>> = _events.asStateFlow()

    private val _devotionals = MutableStateFlow(
        listOf(
            Devotional(
                id = "dev_1",
                title = "Permaneciendo en el Secreto del Dios Altísimo",
                week = "Semana 45 - Noviembre 2026",
                date = "08 Nov 2026",
                verseRef = "Salmos 91:1-2",
                verseText = "El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente. Diré yo a Jehová: Esperanza mía, y castillo mío; Mi Dios, en quien confiaré.",
                reflection = "El salmista no nos invita a ser visitantes ocasionales de la presencia de Dios, sino 'habitantes'. Habitar significa establecer allí nuestra residencia continua. En medio de un mundo ruidoso y lleno de ansiedades, el abrigo del Altísimo es un lugar de perfecta paz, donde los temores se disipan y el amor de Dios nos envuelve. Cuando hacemos de su presencia nuestra morada diaria a través de la oración y la Palabra, experimentamos una protección sobrenatural que nada en esta tierra puede arrebatar.",
                practicalApplication = "Dedica hoy los primeros 15 minutos de la mañana a estar en silencio ante Dios, orando este salmo en voz alta sobre tu vida, tu familia y tu trabajo.",
                studyQuestions = listOf(
                    "¿Qué diferencia hay entre visitar la presencia de Dios y 'habitar' en ella?",
                    "¿Cuáles son los principales distractores que intentan alejarte de tu tiempo devocional diario?",
                    "¿Cómo puedes aplicar la confianza en el 'castillo de Dios' ante la situación más desafiante que enfrentas esta semana?"
                ),
                author = "Pastor David Morales",
                category = "Fe y Confianza",
                isRead = true
            ),
            Devotional(
                id = "dev_2",
                title = "La Generosidad que Transforma Comunidades",
                week = "Semana 44 - Noviembre 2026",
                date = "01 Nov 2026",
                verseRef = "2 Corintios 9:7-8",
                verseText = "Cada uno dé como propuso en su corazón: no con tristeza, ni por necesidad, porque Dios ama al dador alegre. Y poderoso es Dios para hacer que abunde en vosotros toda gracia...",
                reflection = "La generosidad bíblica nunca es una carga impuesta, sino la respuesta natural de un corazón que ha sido tocado por la gracia desbordante de Jesucristo. Cuando sembramos con alegría en el Reino de Dios, en las misiones y en aquellos que sufren necesidad, abrimos las compuertas de la bendición no solo material, sino espiritual.",
                practicalApplication = "Busca esta semana bendecir a alguien en secreto con un detalle, un plato de comida o una ofrenda de amor sin esperar nada a cambio.",
                studyQuestions = listOf(
                    "¿Por qué la actitud del corazón es tan importante para Dios al momento de dar?",
                    "¿De qué maneras podemos ser generosos además del aspecto financiero?",
                    "¿Qué testimonio recuerdas donde Dios proveyó de forma milagrosa?"
                ),
                author = "Pastora Martha Morales",
                category = "Mayordomía",
                isRead = false
            ),
            Devotional(
                id = "dev_3",
                title = "El Poder de una Comunidad Guiada por el Espíritu",
                week = "Semana 43 - Octubre 2026",
                date = "25 Oct 2026",
                verseRef = "Hechos 2:42-44",
                verseText = "Y perseveraban en la doctrina de los apóstoles, en la comunión unos con otros, en el partimiento del pan y en las oraciones.",
                reflection = "La iglesia primitiva floreció porque no eran solo asistentes a un templo, sino una verdadera familia en Cristo. El discipulado en grupos de conexión CENFOL nos permite ser cuidados, pastoreados y crecer juntos en madurez espiritual.",
                practicalApplication = "Escribe o llama a un hermano de tu grupo de conexión para animarlo y orar juntos por teléfono hoy.",
                studyQuestions = listOf(
                    "¿Por qué es vital perseverar en comunidad y no vivir la fe en aislamiento?",
                    "¿Qué fruto ha traído a tu vida pertenecer a un grupo de conexión?"
                ),
                author = "Líder de Discipulado CENFOL",
                category = "Comunidad",
                isRead = false
            )
        )
    )
    val devotionals: StateFlow<List<Devotional>> = _devotionals.asStateFlow()

    private val _testimonies = MutableStateFlow(
        listOf(
            Testimony(
                id = "test_1",
                authorName = "Lucía Ramírez",
                date = "04 Nov 2026",
                category = "Sanidad Divina",
                title = "Médicos no daban esperanzas, pero Dios tenía la última palabra",
                story = "Hace tres meses me diagnosticaron una afección severa en los pulmones. El cuerpo pastoral de la Iglesia del Dios Altísimo ungió con aceite y oró por mí en el servicio de milagros. En los nuevos exámenes de control la semana pasada, los especialistas quedaron atónitos: ¡mis pulmones están 100% limpios y sanos! Toda la gloria y honra sea para nuestro Señor Jesucristo.",
                isApproved = true,
                likes = 47,
                hasLiked = false
            ),
            Testimony(
                id = "test_2",
                authorName = "Juan y Andrea Cárdenas",
                date = "28 Oct 2026",
                category = "Restauración Familiar",
                title = "Nuestro matrimonio estaba al borde del divorcio",
                story = "Llegamos a la iglesia destrozados y separados. A través de las consejerías pastorales y el grupo de conexión de matrimonios CENFOL, Cristo entró a nuestro hogar. Hoy servimos juntos en la alabanza con nuestros dos hijos y experimentamos una paz que sobrepasa todo entendimiento.",
                isApproved = true,
                likes = 63,
                hasLiked = true
            ),
            Testimony(
                id = "test_3",
                authorName = "Mateo Silva (Joven CENFOL)",
                date = "18 Oct 2026",
                category = "Transformación",
                title = "Libre de adicciones y con un nuevo propósito",
                story = "Estuve atrapado por años en la depresión y sustancias nocivas. En el campamento de jóvenes del Dios Altísimo sentí el abrazo del Padre por primera vez. Hoy estudio teología y lidero jóvenes para rescatarlos de la oscuridad hacia la luz admirable de Cristo.",
                isApproved = true,
                likes = 89,
                hasLiked = false
            )
        )
    )
    val testimonies: StateFlow<List<Testimony>> = _testimonies.asStateFlow()

    private val _gallery = MutableStateFlow(
        listOf(
            GalleryItem(
                id = "gal_1",
                title = "Adoración Congregacional",
                activityName = "Servicio Dominical de Gloria",
                date = "Noviembre 2026",
                description = "Cientos de vidas unidas en una sola voz glorificando al Dios Altísimo con júbilo y reverencia.",
                category = "Alabanza",
                imageUrl = "https://images.unsplash.com/photo-1510525009512-ad7fc13eefab?w=800&q=80"
            ),
            GalleryItem(
                id = "gal_2",
                title = "Campamento Juvenil 'Fuego Santo'",
                activityName = "Retiro Anual de Jóvenes CENFOL",
                date = "Octubre 2026",
                description = "Tres días de fogatas, enseñanzas bíblicas profundas, talleres de liderazgo y bautismos en agua.",
                category = "Jóvenes",
                imageUrl = "https://images.unsplash.com/photo-1519741497674-611481863552?w=800&q=80"
            ),
            GalleryItem(
                id = "gal_3",
                title = "Escuela Bíblica Infantil 'Hijos del Rey'",
                activityName = "Ministerio de Niños",
                date = "Octubre 2026",
                description = "Nuestros pequeños aprendiendo los valores del Reino de Dios mediante obras de teatro, manualidades y canciones.",
                category = "Niños",
                imageUrl = "https://images.unsplash.com/photo-1485546246426-74dc88dec4d9?w=800&q=80"
            ),
            GalleryItem(
                id = "gal_4",
                title = "Acción Social y Entrega de Mercados",
                activityName = "Misiones Urbanas CENFOL",
                date = "Septiembre 2026",
                description = "Llevando el amor práctico de Cristo a más de 150 familias necesitadas en los sectores vulnerables.",
                category = "Misiones",
                imageUrl = "https://images.unsplash.com/photo-1469571486292-0ba58a3f068b?w=800&q=80"
            ),
            GalleryItem(
                id = "gal_5",
                title = "Bautismos en el Río Pance",
                activityName = "Paso de Fe y Nueva Vida",
                date = "Agosto 2026",
                description = "38 nuevos creyentes proclamaron públicamente su fe en Jesucristo, enterrando el pasado y naciendo de nuevo.",
                category = "Bautismos",
                imageUrl = "https://images.unsplash.com/photo-1544427920-c49ccfb85579?w=800&q=80"
            )
        )
    )
    val gallery: StateFlow<List<GalleryItem>> = _gallery.asStateFlow()

    private val _members = MutableStateFlow(
        listOf(
            MemberRegistration(
                id = "mem_1",
                fullName = "Mariana Valenzuela",
                email = "mariana.val@gmail.com",
                phone = "+57 312 456 7890",
                ageGroup = "Jóvenes (18-28)",
                address = "Calle 14 #85-30, Cali",
                ministryInterest = "Ministerio de Alabanza & Medios",
                connectionGroup = "Grupo CENFOL Norte",
                isBaptized = true,
                prayerNeed = "Dirección en mis estudios universitarios y empleo",
                registeredAt = "02 Nov 2026"
            ),
            MemberRegistration(
                id = "mem_2",
                fullName = "Javier Fernando Mendoza",
                email = "javier.mendoza@yahoo.es",
                phone = "+57 318 901 2345",
                ageGroup = "Adultos (36-50)",
                address = "Carrera 42 #12-18, Cali",
                ministryInterest = "Ujieres y Bienvenida",
                connectionGroup = "Grupo CENFOL Valle del Lili",
                isBaptized = false,
                prayerNeed = "Pido bautismo en agua y sanidad para mi columna",
                registeredAt = "05 Nov 2026"
            )
        )
    )
    val members: StateFlow<List<MemberRegistration>> = _members.asStateFlow()

    private val _donations = MutableStateFlow(
        listOf(
            DonationRecord(
                id = "don_1",
                donorName = "Anónimo / Miembro #402",
                category = "Diezmo",
                amount = 150000.0,
                isRecurring = true,
                frequency = "Mensual",
                paymentMethod = "PSE / Transferencia",
                status = "Aprobado",
                date = "01 Nov 2026",
                receiptNumber = "REC-2026-9481"
            ),
            DonationRecord(
                id = "don_2",
                donorName = "Familia Ospina",
                category = "Templo Sagrado",
                amount = 250000.0,
                isRecurring = false,
                frequency = "Única vez",
                paymentMethod = "Tarjeta Débito/Crédito",
                status = "Aprobado",
                date = "04 Nov 2026",
                receiptNumber = "REC-2026-9512"
            ),
            DonationRecord(
                id = "don_3",
                donorName = "Hermano Santiago",
                category = "Misiones CENFOL",
                amount = 80000.0,
                isRecurring = true,
                frequency = "Quincenal",
                paymentMethod = "Transferencia",
                status = "Aprobado",
                date = "06 Nov 2026",
                receiptNumber = "REC-2026-9533"
            )
        )
    )
    val donations: StateFlow<List<DonationRecord>> = _donations.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            AppNotification("notif_1", "🔴 Transmisión En Vivo Iniciada", "El culto dominical ha comenzado. ¡Conéctate ahora a la presencia de Dios!", "Hace 15 min", "LIVE"),
            AppNotification("notif_2", "📖 Nuevo Devocional Semanal", "Estudio bíblico #45 disponible: 'Permaneciendo en el Secreto de Dios'", "Hace 2 horas", "DEVOTIONAL"),
            AppNotification("notif_3", "📅 Recordatorio de Vigilia", "Vigilia de oración este viernes 8:00 PM. No olvides traer tu Biblia.", "Ayer", "EVENT")
        )
    )
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    val stats: StateFlow<ChurchStats> = MutableStateFlow(
        ChurchStats(
            activeMembers = 1240,
            monthlyAttendanceAvg = 980,
            testimoniesCount = 84,
            offeringGoalProgress = 0.74f,
            currentOfferings = 37000000.0,
            offeringTarget = 50000000.0,
            activeConnectGroups = 46
        )
    ).asStateFlow()

    // --- Action Methods ---

    fun toggleDarkTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setLanguage(lang: String) {
        _currentLanguage.value = lang
    }

    fun authenticateAdmin(pin: String): Boolean {
        return if (pin == "2026" || pin == "7777") {
            _isAdminAuthenticated.value = true
            true
        } else {
            false
        }
    }

    fun logoutAdmin() {
        _isAdminAuthenticated.value = false
    }

    fun sendChatMessage(userName: String, messageText: String, isAmen: Boolean = false) {
        if (messageText.isBlank()) return
        val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val newMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            userName = userName.ifBlank { "Hermano(a) en Cristo" },
            message = messageText.trim(),
            timestamp = timeNow,
            isAmen = isAmen
        )
        _chatMessages.value = _chatMessages.value + newMsg
    }

    fun registerMember(
        name: String,
        email: String,
        phone: String,
        ageGroup: String,
        address: String,
        ministry: String,
        group: String,
        baptized: Boolean,
        prayerNeed: String
    ): MemberRegistration {
        val dateNow = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        val newMember = MemberRegistration(
            id = "mem_" + UUID.randomUUID().toString().take(6),
            fullName = name,
            email = email,
            phone = phone,
            ageGroup = ageGroup,
            address = address,
            ministryInterest = ministry,
            connectionGroup = group,
            isBaptized = baptized,
            prayerNeed = prayerNeed,
            registeredAt = dateNow
        )
        _members.value = listOf(newMember) + _members.value
        return newMember
    }

    fun submitDonation(
        name: String,
        category: String,
        amount: Double,
        isRecurring: Boolean,
        frequency: String,
        paymentMethod: String
    ): DonationRecord {
        val dateNow = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        val receiptCode = "REC-2026-" + (1000..9999).random()
        val record = DonationRecord(
            id = "don_" + UUID.randomUUID().toString().take(6),
            donorName = name.ifBlank { "Ofrenda Anónima" },
            category = category,
            amount = amount,
            isRecurring = isRecurring,
            frequency = frequency,
            paymentMethod = paymentMethod,
            status = "Aprobado",
            date = dateNow,
            receiptNumber = receiptCode
        )
        _donations.value = listOf(record) + _donations.value
        return record
    }

    fun toggleEventRegistration(eventId: String) {
        _events.value = _events.value.map {
            if (it.id == eventId) {
                val newStatus = !it.isRegistered
                it.copy(
                    isRegistered = newStatus,
                    attendeesCount = if (newStatus) it.attendeesCount + 1 else it.attendeesCount - 1
                )
            } else it
        }
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
        val newEvent = ChurchEvent(
            id = "evt_" + UUID.randomUUID().toString().take(6),
            title = title,
            date = date,
            time = time,
            location = location,
            category = category,
            description = description,
            speaker = speaker,
            attendeesCount = 1,
            isRegistered = true
        )
        _events.value = listOf(newEvent) + _events.value
    }

    fun submitTestimony(
        author: String,
        title: String,
        category: String,
        story: String
    ): Testimony {
        val dateNow = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        val testimony = Testimony(
            id = "test_" + UUID.randomUUID().toString().take(6),
            authorName = author.ifBlank { "Miembro de la Congregación" },
            date = dateNow,
            category = category,
            title = title,
            story = story,
            isApproved = false, // Pending admin approval
            likes = 0,
            hasLiked = false
        )
        _testimonies.value = listOf(testimony) + _testimonies.value
        return testimony
    }

    fun toggleLikeTestimony(testimonyId: String) {
        _testimonies.value = _testimonies.value.map {
            if (it.id == testimonyId) {
                val liked = !it.hasLiked
                it.copy(
                    hasLiked = liked,
                    likes = if (liked) it.likes + 1 else it.likes - 1
                )
            } else it
        }
    }

    fun approveTestimony(testimonyId: String) {
        _testimonies.value = _testimonies.value.map {
            if (it.id == testimonyId) it.copy(isApproved = true) else it
        }
    }

    fun toggleDevotionalRead(devotionalId: String) {
        _devotionals.value = _devotionals.value.map {
            if (it.id == devotionalId) it.copy(isRead = !it.isRead) else it
        }
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
        val dateNow = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        val newDev = Devotional(
            id = "dev_" + UUID.randomUUID().toString().take(6),
            title = title,
            week = "Estudio Especial",
            date = dateNow,
            verseRef = verseRef,
            verseText = verseText,
            reflection = reflection,
            practicalApplication = practical,
            studyQuestions = questions,
            author = author,
            category = category,
            isRead = false
        )
        _devotionals.value = listOf(newDev) + _devotionals.value
    }

    companion object {
        @Volatile
        private var instance: ChurchRepository? = null

        fun getInstance(): ChurchRepository {
            return instance ?: synchronized(this) {
                instance ?: ChurchRepository().also { instance = it }
            }
        }
    }
}
