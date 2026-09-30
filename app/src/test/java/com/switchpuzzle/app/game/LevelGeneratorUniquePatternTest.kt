package com.switchpuzzle.app.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class LevelGeneratorUniquePatternTest {

    @Test
    fun consecutiveLevels_haveDifferentPatterns() {

        val generator = LevelGenerator()

        val levelOne = generator.generateForLevel(1)
        val levelTwo = generator.generateForLevel(2)

        val patternOne = levelOne.cells.joinToString("")
        val patternTwo = levelTwo.cells.joinToString("")

        assertNotEquals(
            patternOne,
            patternTwo
        )
    }

    @Test
    fun multipleLevels_haveUniquePatterns() {

        val generator = LevelGenerator()

        val patterns = mutableSetOf<String>()

        repeat(10) { index ->

            val level = generator.generateForLevel(index + 1)

            val pattern =
                level.cells.joinToString("")

            patterns.add(pattern)
        }

        assertEquals(
            10,
            patterns.size
        )
    }
}
