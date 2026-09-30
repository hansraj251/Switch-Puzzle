package com.switchpuzzle.app.progress

import com.switchpuzzle.app.game.PuzzleEngine
import com.switchpuzzle.app.game.PuzzleState
import org.junit.Assert.assertNotEquals
import org.junit.Test

class PuzzleInteractionStateTest {

    @Test
    fun togglingCell_producesChangedPuzzleState() {
        val engine = PuzzleEngine()

        val initialState = PuzzleState(
            size = 3,
            cells = List(9) { false }
        )

        val nextState = engine.toggle(
            state = initialState,
            index = 4
        )

        assertNotEquals(
            initialState.cells,
            nextState.cells
        )
    }
}
