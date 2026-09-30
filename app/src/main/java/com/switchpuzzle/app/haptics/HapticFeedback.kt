package com.switchpuzzle.app.haptics

enum class HapticType {
    LIGHT,
    SUCCESS
}

interface HapticFeedback {
    fun switchPressed()

    fun levelCompleted()
}
