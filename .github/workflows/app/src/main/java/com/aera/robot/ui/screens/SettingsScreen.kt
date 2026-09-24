package com.aera.robot.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aera.robot.data.LocalPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    prefs: LocalPreferences,
    onBack: () -> Unit
) {
    var nameInput by remember { mutableStateOf(prefs.robotName) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Robot") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Nama Robot") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    prefs.robotName = nameInput
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Pengaturan")
            }
        }
    }
}
