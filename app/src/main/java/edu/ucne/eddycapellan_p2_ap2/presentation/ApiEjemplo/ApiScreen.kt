package edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(
    state: RepositoryUiState,
    onSave: (String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(state.name ?: "") }
    var description by remember { mutableStateOf(state.description ?: "") }
    var htmlUrl by remember { mutableStateOf(state.htmlUrl ?: "") }

    // Actualizar los estados cuando cambie el state
    LaunchedEffect(state.name, state.description, state.htmlUrl) {
        name = state.name ?: ""
        description = state.description ?: ""
        htmlUrl = state.htmlUrl ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Registrar/Editar Repositorio",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A148C),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Cancelar",
                            tint = Color(0xFF4A148C)
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEDE7F6),
                    titleContentColor = Color(0xFF4A148C)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDE7F6))
                .padding(padding)
                .padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del repositorio", color = Color(0xFF4A148C)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.inputError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF4A148C),
                        unfocusedTextColor = Color(0xFF4A148C),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF7B1FA2),
                        unfocusedBorderColor = Color(0xFF7B1FA2),
                        cursorColor = Color(0xFF4A148C)
                    )
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción", color = Color(0xFF4A148C)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF4A148C),
                        unfocusedTextColor = Color(0xFF4A148C),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF7B1FA2),
                        unfocusedBorderColor = Color(0xFF7B1FA2),
                        cursorColor = Color(0xFF4A148C)
                    )
                )

                OutlinedTextField(
                    value = htmlUrl,
                    onValueChange = { htmlUrl = it },
                    label = { Text("URL del repositorio", color = Color(0xFF4A148C)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    isError = state.inputError != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF4A148C),
                        unfocusedTextColor = Color(0xFF4A148C),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF7B1FA2),
                        unfocusedBorderColor = Color(0xFF7B1FA2),
                        cursorColor = Color(0xFF4A148C)
                    )
                )

                // Mostrar error
                state.inputError?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                state.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB39DDB)
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color(0xFF4A148C)
                        )
                    }
                    Button(
                        onClick = {
                            if (name.isBlank()) {
                                onSave("", description, htmlUrl)
                            } else {
                                onSave(name, description, htmlUrl)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7B1FA2)
                        )
                    ) {
                        Text(
                            text = "Guardar",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApiScreenPreview() {
    ApiScreen(
        state = RepositoryUiState(
            name = "Mi Repositorio",
            description = "Descripción de ejemplo",
            htmlUrl = "https://github.com/example",
            inputError = null
        ),
        onSave = { name, description, htmlUrl ->
            println("Guardando: $name, $description, $htmlUrl")
        },
        onCancel = { println("Cancelado") }
    )
}