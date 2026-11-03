package com.pmdm.lab.ut3.retos.reto01_ahorcado

import com.pmdm.lab.ut3.retos.reto01_ahorcado.domain.EstadoAhorcado
import com.pmdm.lab.ut3.retos.reto01_ahorcado.domain.EstadoPartida
import com.pmdm.lab.ut3.retos.reto01_ahorcado.domain.EventoTurno
import com.pmdm.lab.ut3.retos.reto01_ahorcado.domain.enmascarar
import com.pmdm.lab.ut3.retos.reto01_ahorcado.domain.estaAdivinada
import com.pmdm.lab.ut3.retos.reto01_ahorcado.domain.procesarIntento
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MotorAhorcadoTest {

    @Test
    fun enmascararPalabra_ocultaLetrasNoProbadas() {
        val mascara = "KOTLIN".enmascarar("OI")
        assertEquals("_ O _ _ I _", mascara)
    }

    @Test
    fun estaAdivinada_detectaVictoriaCorrectamente() {
        assertFalse("KOTLIN".estaAdivinada("KTL"))
        assertTrue("KOTLIN".estaAdivinada("KOTLIN"))
    }

    @Test
    fun procesarIntento_letraAcertada_actualizaEstadoSinRestarVidas() {
        val inicial = EstadoAhorcado(palabraSecreta = "KOTLIN", vidasRestantes = 6)
        val resultado = inicial.procesarIntento('O')

        assertEquals(6, resultado.vidasRestantes)
        assertEquals("O", resultado.letrasProbadas)
        assertEquals(EventoTurno.ACIERTO, resultado.ultimoEvento)
        assertEquals(EstadoPartida.JUGANDO, resultado.estado)
    }

    @Test
    fun procesarIntento_letraFallada_decrementaVidas() {
        val inicial = EstadoAhorcado(palabraSecreta = "KOTLIN", vidasRestantes = 6)
        val resultado = inicial.procesarIntento('Z')

        assertEquals(5, resultado.vidasRestantes)
        assertEquals("Z", resultado.letrasProbadas)
        assertEquals(EventoTurno.FALLO, resultado.ultimoEvento)
        assertEquals(EstadoPartida.JUGANDO, resultado.estado)
    }

    @Test
    fun procesarIntento_sinVidas_pasaADerrota() {
        val inicial = EstadoAhorcado(palabraSecreta = "KOTLIN", vidasRestantes = 1)
        val resultado = inicial.procesarIntento('X')

        assertEquals(0, resultado.vidasRestantes)
        assertEquals(EstadoPartida.DERROTA, resultado.estado)
    }
}
