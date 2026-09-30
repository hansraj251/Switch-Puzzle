package com.switchpuzzle.app.haptics

import org.junit.Assert.assertEquals
import org.junit.Test

class HapticFeedbackTest {

    @Test
    fun switchPress_usesLightHaptic() {

        val haptic =
            RecordingHapticFeedback()

        haptic.switchPressed()

        assertEquals(
            listOf(HapticType.LIGHT),
            haptic.events
        )
    }

    @Test
    fun levelComplete_usesSuccessHaptic() {

        val haptic =
            RecordingHapticFeedback()

        haptic.levelCompleted()

        assertEquals(
            listOf(HapticType.SUCCESS),
            haptic.events
        )
    }
}
