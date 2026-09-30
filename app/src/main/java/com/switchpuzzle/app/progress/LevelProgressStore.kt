package com.switchpuzzle.app.progress

interface LevelProgressStore {

    fun saveHighestUnlockedLevel(level: Int)

    fun loadHighestUnlockedLevel(): Int
}
