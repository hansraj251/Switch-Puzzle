package com.switchpuzzle.app.progress

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LevelSelectionState(
    private var highestUnlockedLevel: Int
) {

    enum class Screen {
        GAME,
        LEVEL_SELECT
    }

    var screen: Screen by mutableStateOf(Screen.GAME)
        private set

    var selectedLevel: Int = 1
        private set

    init {
        require(highestUnlockedLevel >= 1) {
            "Highest unlocked level must be at least 1"
        }

        selectedLevel = highestUnlockedLevel
    }

    fun openLevelSelect() {
        screen = Screen.LEVEL_SELECT
    }

    fun selectLevel(level: Int) {
        require(level >= 1) {
            "Level must be at least 1"
        }

        if (isUnlocked(level)) {
            selectedLevel = level
            screen = Screen.GAME
        }
    }

    fun updateHighestUnlockedLevel(level: Int) {
        require(level >= 1) {
            "Highest unlocked level must be at least 1"
        }

        highestUnlockedLevel = maxOf(
            highestUnlockedLevel,
            level
        )
    }

    fun isUnlocked(level: Int): Boolean {
        require(level >= 1) {
            "Level must be at least 1"
        }

        return level <= highestUnlockedLevel
    }

    fun highestUnlockedLevel(): Int {
        return highestUnlockedLevel
    }
}
