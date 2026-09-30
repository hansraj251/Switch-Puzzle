package com.switchpuzzle.app.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class LevelGeneratorDifficultyTest {

    @Test
    fun levelOne_usesDifficultyConfiguration() {

        val generator = LevelGenerator()

        val level = generator.generateForLevel(1)

        assertEquals(3, level.size)
        assertEquals(9, level.cells.size)
        assertFalse(level.isSolved)
        assertEquals(3, level.scrambleIndices.size)
    }

    @Test
    fun levelFive_usesDifficultyConfiguration() {

        val generator = LevelGenerator()

        val level = generator.generateForLevel(5)

        assertEquals(3, level.size)
        assertEquals(9, level.cells.size)
        assertFalse(level.isSolved)
        assertEquals(7, level.scrambleIndices.size)
    }
}
