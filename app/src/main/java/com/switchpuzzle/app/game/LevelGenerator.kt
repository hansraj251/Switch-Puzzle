package com.switchpuzzle.app.game

import kotlin.random.Random

data class GeneratedLevel(
    val size: Int,
    val cells: List<Boolean>,
    val scrambleIndices: List<Int>
) {

    val isSolved: Boolean
        get() = cells.all { it }
}

class LevelGenerator(
    val engine: PuzzleEngine = PuzzleEngine()
) {

    private val generatedPatterns = mutableSetOf<String>()

    fun generate(
        size: Int,
        scrambleMoves: Int
    ): GeneratedLevel {

        require(size > 0) {
            "Size must be greater than zero"
        }

        require(scrambleMoves > 0) {
            "Scramble moves must be greater than zero"
        }

        val maxAttempts = 1000

        repeat(maxAttempts) {

            var state = PuzzleState(
                size = size,
                cells = List(size * size) { true }
            )

            val scrambleIndices = mutableListOf<Int>()

            repeat(scrambleMoves) {

                val index = Random.nextInt(size * size)

                scrambleIndices.add(index)

                state = engine.toggle(
                    state = state,
                    index = index
                )
            }

            if (state.isSolved) {
                return@repeat
            }

            val pattern = state.cells.joinToString("")

            if (generatedPatterns.add(pattern)) {

                return GeneratedLevel(
                    size = size,
                    cells = state.cells,
                    scrambleIndices = scrambleIndices
                )
            }
        }

        error(
            "Unable to generate a unique puzzle pattern"
        )
    }

    fun generateForLevel(
        level: Int
    ): GeneratedLevel {

        val difficulty = Difficulty.forLevel(level)

        return generate(
            size = difficulty.boardSize,
            scrambleMoves = difficulty.scrambleMoves
        )
    }
}
