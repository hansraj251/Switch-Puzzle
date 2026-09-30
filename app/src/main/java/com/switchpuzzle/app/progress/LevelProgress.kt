package com.switchpuzzle.app.progress

class LevelProgress(
    initialHighestUnlockedLevel: Int = 1
) {

    var highestUnlockedLevel: Int =
        initialHighestUnlockedLevel
        private set

    fun completeLevel(level: Int) {
        require(level >= 1) {
            "Level must be at least 1"
        }

        highestUnlockedLevel =
            maxOf(
                highestUnlockedLevel,
                level + 1
            )
    }

    fun isUnlocked(level: Int): Boolean {
        require(level >= 1) {
            "Level must be at least 1"
        }

        return level <= highestUnlockedLevel
    }
}
