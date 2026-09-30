package com.switchpuzzle.app.progress

class InMemoryLevelProgressStore {

    private var highestUnlockedLevel = 1

    fun saveHighestUnlockedLevel(level: Int) {
        require(level >= 1) {
            "Level must be at least 1"
        }

        highestUnlockedLevel = level
    }

    fun loadHighestUnlockedLevel(): Int {
        return highestUnlockedLevel
    }
}
