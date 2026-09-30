package com.switchpuzzle.app.progress

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LevelProgressTest {

    @Test
    fun newProgress_startsAtLevelOne() {
        val progress = LevelProgress()

        assertEquals(1, progress.highestUnlockedLevel)
    }

    @Test
    fun completingLevel_unlocksNextLevel() {
        val progress = LevelProgress()

        progress.completeLevel(1)

        assertEquals(
            2,
            progress.highestUnlockedLevel
        )
    }

    @Test
    fun completingHigherLevel_unlocksFollowingLevel() {
        val progress = LevelProgress()

        progress.completeLevel(5)

        assertEquals(
            6,
            progress.highestUnlockedLevel
        )
    }

    @Test
    fun lowerLevelCompletion_doesNotReduceProgress() {
        val progress = LevelProgress()

        progress.completeLevel(5)
        progress.completeLevel(2)

        assertEquals(
            6,
            progress.highestUnlockedLevel
        )
    }

    @Test
    fun levelIsUnlocked_onlyWhenWithinProgress() {
        val progress = LevelProgress()

        progress.completeLevel(3)

        assertTrue(progress.isUnlocked(1))
        assertTrue(progress.isUnlocked(4))
        assertFalse(progress.isUnlocked(5))
    }
}
