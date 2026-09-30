package com.switchpuzzle.app.progress

import org.junit.Assert.assertEquals
import org.junit.Test

class LevelProgressStoreTest {

    @Test
    fun saveAndLoad_preservesHighestUnlockedLevel() {
        val store = InMemoryLevelProgressStore()

        store.saveHighestUnlockedLevel(6)

        assertEquals(
            6,
            store.loadHighestUnlockedLevel()
        )
    }

    @Test
    fun newStore_startsAtLevelOne() {
        val store = InMemoryLevelProgressStore()

        assertEquals(
            1,
            store.loadHighestUnlockedLevel()
        )
    }
}
