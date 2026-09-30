package com.switchpuzzle.app.game

data class PuzzleState(
    val size: Int,
    val cells: List<Boolean>,
    val moves: Int = 0
) {

    val isSolved: Boolean
        get() = cells.all { it }
}

class PuzzleEngine(
    private val size: Int = 3
) {

    fun toggle(
        state: PuzzleState,
        index: Int
    ): PuzzleState {

        val row = index / size
        val column = index % size

        val nextCells = state.cells.toMutableList()

        toggleCell(nextCells, row, column)
        toggleCell(nextCells, row - 1, column)
        toggleCell(nextCells, row + 1, column)
        toggleCell(nextCells, row, column - 1)
        toggleCell(nextCells, row, column + 1)

        return state.copy(
            cells = nextCells,
            moves = state.moves + 1
        )
    }

    private fun toggleCell(
        cells: MutableList<Boolean>,
        row: Int,
        column: Int
    ) {

        if (row !in 0 until size) {
            return
        }

        if (column !in 0 until size) {
            return
        }

        val index = row * size + column

        cells[index] = !cells[index]
    }
}
