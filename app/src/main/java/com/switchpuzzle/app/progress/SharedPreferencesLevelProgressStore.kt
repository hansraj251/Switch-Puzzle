package com.switchpuzzle.app.progress

import android.content.Context

class SharedPreferencesLevelProgressStore(
    context: Context
) : LevelProgressStore {

    private val preferences =
        context.getSharedPreferences(
            "switch_puzzle_progress",
            Context.MODE_PRIVATE
        )

    override fun saveHighestUnlockedLevel(level: Int) {
        require(level >= 1) {
            "Level must be at least 1"
        }

        val current =
            loadHighestUnlockedLevel()

        preferences.edit()
            .putInt(
                KEY_HIGHEST_UNLOCKED_LEVEL,
                maxOf(current, level)
            )
            .apply()
    }

    override fun loadHighestUnlockedLevel(): Int {
        return preferences.getInt(
            KEY_HIGHEST_UNLOCKED_LEVEL,
            1
        )
    }

    private companion object {
        const val KEY_HIGHEST_UNLOCKED_LEVEL =
            "highest_unlocked_level"
    }
}
