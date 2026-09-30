package com.switchpuzzle.app.progress

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LevelSelectionTest {

    @Test
    fun levelOne_isUnlockedByDefault() {
        val progress = LevelProgress()

        assertTrue(progress.isUnlocked(1))
    }

    @Test
    fun levelTwo_isLockedInitially() {
        val progress = LevelProgress()

        assertFalse(progress.isUnlocked(2))
    }

    @Test
    fun completingLevelOne_unlocksLevelTwo() {
        val progress = LevelProgress()

        progress.completeLevel(1)

        assertTrue(progress.isUnlocked(2))
        assertFalse(progress.isUnlocked(3))
    }

    @Test
    fun completingLevelThree_unlocksThroughLevelFour() {
        val progress = LevelProgress()

        progress.completeLevel(3)

        assertTrue(progress.isUnlocked(1))
        assertTrue(progress.isUnlocked(4))
        assertFalse(progress.isUnlocked(5))
    }
}
