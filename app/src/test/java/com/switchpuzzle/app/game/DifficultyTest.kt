package com.switchpuzzle.app.game

import org.junit.Assert.assertEquals
import org.junit.Test

class DifficultyTest {

    @Test
    fun levelOne_isThreeByThreeWithThreeScrambleMoves() {

        val difficulty = Difficulty.forLevel(1)

        assertEquals(3, difficulty.boardSize)
        assertEquals(3, difficulty.scrambleMoves)
    }

    @Test
    fun levelFive_isThreeByThreeWithSevenScrambleMoves() {

        val difficulty = Difficulty.forLevel(5)

        assertEquals(3, difficulty.boardSize)
        assertEquals(7, difficulty.scrambleMoves)
    }

    @Test
    fun levelTen_isThreeByThreeWithTwelveScrambleMoves() {

        val difficulty = Difficulty.forLevel(10)

        assertEquals(3, difficulty.boardSize)
        assertEquals(12, difficulty.scrambleMoves)
    }
}
