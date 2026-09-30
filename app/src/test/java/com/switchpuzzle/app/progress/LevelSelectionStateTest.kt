package com.switchpuzzle.app.progress

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LevelSelectionStateTest {

    @Test
    fun openingLevelSelect_changesScreen() {
        val state = LevelSelectionState(1)

        state.openLevelSelect()

        assertEquals(
            LevelSelectionState.Screen.LEVEL_SELECT,
            state.screen
        )
    }

    @Test
    fun selectingUnlockedLevel_changesSelectedLevelAndScreen() {
        val state = LevelSelectionState(3)

        state.openLevelSelect()
        state.selectLevel(2)

        assertEquals(
            LevelSelectionState.Screen.GAME,
            state.screen
        )
        assertEquals(
            2,
            state.selectedLevel
        )
    }

    @Test
    fun selectingLockedLevel_keepsLevelSelectScreen() {
        val state = LevelSelectionState(3)

        state.openLevelSelect()
        state.selectLevel(5)

        assertEquals(
            LevelSelectionState.Screen.LEVEL_SELECT,
            state.screen
        )
        assertEquals(
            3,
            state.selectedLevel
        )
    }

    @Test
    fun unlockedLevels_areReportedCorrectly() {
        val state = LevelSelectionState(4)

        assertTrue(state.isUnlocked(1))
        assertTrue(state.isUnlocked(4))
        assertFalse(state.isUnlocked(5))
    }
}
