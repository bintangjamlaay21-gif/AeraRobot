package com.aera.robot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aera.robot.data.GeminiRepository
import com.aera.robot.data.LocalPreferences
import com.aera.robot.robot.RobotController
import com.aera.robot.robot.RobotState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    robotController: RobotController,
    geminiRepo: GeminiRepository,
    prefs: LocalPreferences,
    onOpenSettings: () -> Unit,
    onStartVoiceInput: () -> Unit
) {
    var userInput by remember { mutableStateOf("") }
    var conversationLog by remember { mutableStateOf("Halo! Saya ${prefs.robotName}, asisten robot Anda.") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(prefs.robotName) },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Pengaturan")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status Tampilan Robot
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (robotController.currentState) {
                            RobotState.IDLE -> "🤖 (Siaga)"
                            RobotState.LISTENING -> "🎤 Mendengarkan..."
                            RobotState.THINKING -> "🧠 Berpikir..."
                            RobotState.SPEAKING -> "🗣️ Berbicara..."
                        },
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Log Percakapan
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(text = conversationLog)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Teks & Tombol Suara
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ketik pesan...") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    if (userInput.isNotBlank()) {
                        val query = userInput
                        userInput = ""
                        robotController.setState(RobotState.THINKING)
                        coroutineScope.launch {
                            val reply = geminiRepo.getResponse(query)
                            conversationLog = "Anda: $query\n\nAera: $reply"
                            robotController.setState(RobotState.IDLE)
                        }
                    }
                }) {
                    Text("Kirim")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            FloatingActionButton(
                onClick = onStartVoiceInput
            ) {
                Icon(Icons.Default.Mic, contentDescription = "Mulai Suara")
            }
        }
    }
}
