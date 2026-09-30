package com.switchpuzzle.app

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.switchpuzzle.app.game.LevelGenerator
import com.switchpuzzle.app.game.PuzzleEngine
import com.switchpuzzle.app.game.PuzzleState
import com.switchpuzzle.app.haptics.AndroidHapticFeedback
import com.switchpuzzle.app.progress.LevelProgress
import com.switchpuzzle.app.progress.LevelSelectionState
import com.switchpuzzle.app.progress.SharedPreferencesLevelProgressStore

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContent {
            SwitchPuzzleApp()
        }
    }
}

@Composable
private fun SwitchPuzzleApp() {

    val context =
        androidx.compose.ui.platform.LocalContext.current

    val engine = remember {
        PuzzleEngine()
    }

    val generator = remember {
        LevelGenerator(engine)
    }

    val progressStore = remember {
        SharedPreferencesLevelProgressStore(context)
    }

    val haptic = remember {
        AndroidHapticFeedback(context)
    }

    val progress = remember {
        LevelProgress(
            initialHighestUnlockedLevel =
                progressStore.loadHighestUnlockedLevel()
        )
    }

    val selectionState = remember {
        LevelSelectionState(
            progress.highestUnlockedLevel
        )
    }

    var levelNumber by remember {
        mutableStateOf(
            progress.highestUnlockedLevel
        )
    }

    var puzzleState by remember {
        mutableStateOf(
            createLevel(
                generator = generator,
                level = levelNumber
            )
        )
    }

    var completionHapticSent by remember {
        mutableStateOf(false)
    }

    val levelCompleted =
        puzzleState.isSolved

    LaunchedEffect(levelCompleted) {

        if (
            levelCompleted &&
            !completionHapticSent
        ) {

            progress.completeLevel(
                levelNumber
            )

            progressStore.saveHighestUnlockedLevel(
                progress.highestUnlockedLevel
            )

            selectionState.updateHighestUnlockedLevel(
                progress.highestUnlockedLevel
            )

            haptic.levelCompleted()

            completionHapticSent = true
        }

        if (!levelCompleted) {
            completionHapticSent = false
        }
    }

    MaterialTheme {

        when (selectionState.screen) {

            LevelSelectionState.Screen.GAME -> {

                GameScreen(
                    levelNumber = levelNumber,
                    puzzleState = puzzleState,
                    levelCompleted = levelCompleted,
                    engine = engine,
                    generator = generator,
                    haptic = haptic,
                    onPuzzleChanged = { newState ->
                        puzzleState = newState
                        completionHapticSent = false
                    },
                    onOpenLevelSelect = {
                        selectionState.openLevelSelect()
                    },
                    onNextLevel = {

                        levelNumber++

                        selectionState.selectLevel(
                            levelNumber
                        )

                        puzzleState =
                            createLevel(
                                generator = generator,
                                level = levelNumber
                            )

                        completionHapticSent = false
                    }
                )
            }

            LevelSelectionState.Screen.LEVEL_SELECT -> {

                LevelSelectScreen(
                    highestUnlockedLevel =
                        selectionState.highestUnlockedLevel(),
                    selectedLevel = levelNumber,
                    isUnlocked = {
                        selectionState.isUnlocked(it)
                    },
                    onLevelSelected = { selectedLevel ->

                        selectionState.selectLevel(
                            selectedLevel
                        )

                        levelNumber =
                            selectionState.selectedLevel

                        puzzleState =
                            createLevel(
                                generator = generator,
                                level = levelNumber
                            )

                        completionHapticSent = false
                    },
                    onBack = {
                        selectionState.selectLevel(
                            levelNumber
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun GameScreen(
    levelNumber: Int,
    puzzleState: PuzzleState,
    levelCompleted: Boolean,
    engine: PuzzleEngine,
    generator: LevelGenerator,
    haptic: AndroidHapticFeedback,
    onPuzzleChanged: (PuzzleState) -> Unit,
    onOpenLevelSelect: () -> Unit,
    onNextLevel: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text = "SWITCH PUZZLE",
            fontSize = 30.sp
        )

        Text(
            text = "LEVEL $levelNumber",
            modifier =
                Modifier.padding(top = 8.dp),
            fontSize = 22.sp
        )

        Text(
            text =
                "Moves: ${puzzleState.moves}",
            modifier =
                Modifier.padding(8.dp)
        )

        Button(
            onClick = onOpenLevelSelect,
            modifier =
                Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "LEVELS"
            )
        }

        Column(
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            for (
                row in 0 until puzzleState.size
            ) {

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    for (
                        column in 0 until puzzleState.size
                    ) {

                        val index =
                            row *
                                puzzleState.size +
                                column

                        Box(
                            modifier =
                                Modifier
                                    .size(90.dp)
                                    .background(
                                        color =
                                            if (
                                                puzzleState
                                                    .cells[index]
                                            ) {
                                                Color(
                                                    0xFFFFC107
                                                )
                                            } else {
                                                Color(
                                                    0xFF303030
                                                )
                                            },
                                        shape =
                                            RoundedCornerShape(
                                                12.dp
                                            )
                                    )
                                    .clickable(
                                        enabled =
                                            !levelCompleted
                                    ) {

                                        haptic.switchPressed()

                                        val newState =
                                            engine.toggle(
                                                state =
                                                    puzzleState,
                                                index =
                                                    index
                                            )

                                        onPuzzleChanged(
                                            newState
                                        )
                                    },
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    if (
                                        puzzleState
                                            .cells[index]
                                    ) {
                                        "ON"
                                    } else {
                                        "OFF"
                                    },
                                color =
                                    Color.White,
                                fontSize =
                                    18.sp
                            )
                        }
                    }
                }
            }
        }

        if (levelCompleted) {

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text =
                        "LEVEL COMPLETE!",
                    fontSize =
                        24.sp
                )

                Text(
                    text =
                        "Completed in ${puzzleState.moves} moves",
                    modifier =
                        Modifier.padding(top = 8.dp)
                )

                Button(
                    onClick = onNextLevel,
                    modifier =
                        Modifier.padding(top = 16.dp)
                ) {

                    Text(
                        text =
                            "NEXT LEVEL"
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelSelectScreen(
    highestUnlockedLevel: Int,
    selectedLevel: Int,
    isUnlocked: (Int) -> Boolean,
    onLevelSelected: (Int) -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 16.dp,
                    bottom = 24.dp
                ),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text =
                "SELECT LEVEL",
            fontSize =
                30.sp
        )

        Text(
            text =
                "Unlocked: $highestUnlockedLevel",
            modifier =
                Modifier.padding(
                    top = 8.dp,
                    bottom = 24.dp
                )
        )

        for (
            row in 0 until 5
        ) {

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp),
                modifier =
                    Modifier.padding(
                        bottom = 8.dp
                    )
            ) {

                for (
                    column in 0 until 5
                ) {

                    val level =
                        row * 5 + column + 1

                    val unlocked =
                        isUnlocked(level)

                    Box(
                        modifier =
                            Modifier
                                .size(58.dp)
                                .background(
                                    color =
                                        if (unlocked) {
                                            if (
                                                level ==
                                                    selectedLevel
                                            ) {
                                                Color(
                                                    0xFFFFC107
                                                )
                                            } else {
                                                Color(
                                                    0xFF303030
                                                )
                                            }
                                        } else {
                                            Color(
                                                0xFFBDBDBD
                                            )
                                        },
                                    shape =
                                        RoundedCornerShape(
                                            12.dp
                                        )
                                )
                                .clickable(
                                    enabled =
                                        unlocked
                                ) {
                                    onLevelSelected(
                                        level
                                    )
                                },
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                if (unlocked) {
                                    level.toString()
                                } else {
                                    "🔒"
                                },
                            color =
                                if (unlocked) {
                                    Color.White
                                } else {
                                    Color.DarkGray
                                },
                            fontSize =
                                18.sp
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier =
                Modifier.padding(top = 24.dp)
        ) {
            Text(
                text = "BACK"
            )
        }
    }
}

private fun createLevel(
    generator: LevelGenerator,
    level: Int
): PuzzleState {

    val generated =
        generator.generateForLevel(level)

    return PuzzleState(
        size =
            generated.size,
        cells =
            generated.cells
    )
}
