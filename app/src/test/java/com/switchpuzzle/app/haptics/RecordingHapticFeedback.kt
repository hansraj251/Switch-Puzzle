package com.switchpuzzle.app.haptics

class RecordingHapticFeedback : HapticFeedback {

    val events = mutableListOf<HapticType>()

    override fun switchPressed() {
        events.add(HapticType.LIGHT)
    }

    override fun levelCompleted() {
        events.add(HapticType.SUCCESS)
    }
}
