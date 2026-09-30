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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

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

@androidx.compose.runtime.Composable
private fun SwitchPuzzleApp() {

    val engine = remember {
        PuzzleEngine()
    }

    val generator = remember {
        LevelGenerator(engine)
    }

    var levelNumber by remember {
        mutableStateOf(1)
    }

    var puzzleState by remember {
        mutableStateOf(
            createLevel(
                generator = generator,
                level = levelNumber
            )
        )
    }

    val levelCompleted = puzzleState.isSolved

    MaterialTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "SWITCH PUZZLE",
                fontSize = 30.sp
            )

            Text(
                text = "LEVEL $levelNumber",
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 22.sp
            )

            Text(
                text = "Moves: ${puzzleState.moves}",
                modifier = Modifier.padding(8.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                for (row in 0 until puzzleState.size) {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        for (column in 0 until puzzleState.size) {

                            val index =
                                row * puzzleState.size + column

                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .background(
                                        color =
                                            if (puzzleState.cells[index]) {
                                                Color(0xFFFFC107)
                                            } else {
                                                Color(0xFF303030)
                                            },
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable(
                                        enabled = !levelCompleted
                                    ) {

                                        puzzleState =
                                            engine.toggle(
                                                state = puzzleState,
                                                index = index
                                            )
                                    },

                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text =
                                        if (puzzleState.cells[index]) {
                                            "ON"
                                        } else {
                                            "OFF"
                                        },

                                    color = Color.White,

                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            if (levelCompleted) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "LEVEL COMPLETE!",
                        fontSize = 24.sp
                    )

                    Text(
                        text = "Completed in ${puzzleState.moves} moves",
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Button(
                        onClick = {

                            levelNumber++

                            puzzleState =
                                createLevel(
                                    generator = generator,
                                    level = levelNumber
                                )
                        },

                        modifier = Modifier.padding(top = 16.dp)
                    ) {

                        Text(
                            text = "NEXT LEVEL"
                        )
                    }
                }
            }
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
        size = generated.size,
        cells = generated.cells
    )
}
