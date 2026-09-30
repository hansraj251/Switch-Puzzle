package com.switchpuzzle.app.haptics

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

class AndroidHapticFeedback(
    context: Context
) : HapticFeedback {

    private val vibrator =
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    override fun switchPressed() {
        vibrate(
            VibrationEffect.EFFECT_TICK
        )
    }

    override fun levelCompleted() {
        vibrate(
            VibrationEffect.EFFECT_HEAVY_CLICK
        )
    }

    private fun vibrate(
        effect: Int
    ) {
        if (!vibrator.hasVibrator()) {
            return
        }

        vibrator.vibrate(
            VibrationEffect.createPredefined(effect)
        )
    }
}
