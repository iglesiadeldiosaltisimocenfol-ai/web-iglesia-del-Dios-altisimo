package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ChurchRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Dios Altísimo", appName)
  }

  @Test
  fun `repository initial state and donation`() {
    val repo = ChurchRepository.getInstance()
    assertTrue(repo.events.value.isNotEmpty())
    assertTrue(repo.devotionals.value.isNotEmpty())

    val donation = repo.submitDonation(
      name = "Miembro Test",
      category = "Diezmo",
      amount = 50000.0,
      isRecurring = true,
      frequency = "Mensual",
      paymentMethod = "PSE"
    )
    assertNotNull(donation.receiptNumber)
    assertEquals("Aprobado", donation.status)
  }

  @Test
  fun `admin authentication with default PIN`() {
    val repo = ChurchRepository.getInstance()
    assertTrue(repo.authenticateAdmin("2026"))
  }
}
