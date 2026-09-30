package com.switchpuzzle.app.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LevelGeneratorTest {

    @Test
    fun generatedLevel_hasCorrectSize() {

        val generator = LevelGenerator()

        val level = generator.generate(
            size = 3,
            scrambleMoves = 5
        )

        assertEquals(9, level.cells.size)
    }

    @Test
    fun generatedLevel_isNotAlreadySolved() {

        val generator = LevelGenerator()

        val level = generator.generate(
            size = 3,
            scrambleMoves = 5
        )

        assertFalse(level.isSolved)
    }

    @Test
    fun generatedLevel_isSolvableByReplayingScramble() {

        val generator = LevelGenerator()

        val level = generator.generate(
            size = 3,
            scrambleMoves = 5
        )

        var state = PuzzleState(
            size = level.size,
            cells = level.cells
        )

        level.scrambleIndices.reversed().forEach { index ->

            state = generator.engine.toggle(
                state = state,
                index = index
            )
        }

        assertTrue(state.isSolved)
    }
}
