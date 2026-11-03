package com.pmdm.lab.ut3.codelabs.codelab04_propinas

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme
import org.junit.Rule
import org.junit.Test

class PropinasScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun escribirUnImporte_muestraElTotalConLaPropina() {
        composeTestRule.setContent {
            LabUt3Theme { PropinasScreen() }
        }

        composeTestRule.onNodeWithText("Importe de la cuenta").performTextInput("50")

        // 50 € más el 10 % que viene marcado. substring: vale "55,00 €" y "55.00 €"
        composeTestRule.onNodeWithText("Total: 55", substring = true).assertIsDisplayed()
    }

    @Test
    fun escribirLetras_muestraElMensajeDeError() {
        composeTestRule.setContent {
            LabUt3Theme { PropinasScreen() }
        }

        composeTestRule.onNodeWithText("Importe de la cuenta").performTextInput("abc")

        composeTestRule.onNodeWithText("Escribe un número, por ejemplo 24,50").assertIsDisplayed()
    }
}
