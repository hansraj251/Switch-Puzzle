package com.switchpuzzle.app.game

data class DifficultyConfig(
    val boardSize: Int,
    val scrambleMoves: Int
)

object Difficulty {

    fun forLevel(level: Int): DifficultyConfig {

        require(level >= 1) {
            "Level must be at least 1"
        }

        return DifficultyConfig(
            boardSize = 3,
            scrambleMoves = level + 2
        )
    }
}
