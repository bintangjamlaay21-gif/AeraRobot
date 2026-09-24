package com.aera.robot.robot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class RobotState {
    IDLE, LISTENING, THINKING, SPEAKING
}

class RobotController {
    var currentState by mutableStateOf(RobotState.IDLE)
        private set

    fun setState(newState: RobotState) {
        currentState = newState
    }
}
