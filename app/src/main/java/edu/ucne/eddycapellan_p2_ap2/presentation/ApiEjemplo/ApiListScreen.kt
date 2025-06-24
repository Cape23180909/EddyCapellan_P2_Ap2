package edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto

@Composable
fun ApiListScreen(
    state: RepositoryUiState,
    onCreate: () -> Unit,
    onItemClick: (RepositoryDto) -> Unit,
    onRefresh: () -> Unit
) {
    // Estado para la búsqueda
    var query by remember { mutableStateOf("") }

    // Filtrar repositorios basados en la búsqueda
    val filteredRepositories = state.repositories.filter { repo ->
        query.isBlank() || (repo.name?.contains(query, ignoreCase = true) == true)
    }

    Scaffold(
        floatingActionButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FloatingActionButton(
                    onClick = onRefresh,
                    containerColor = Color(0xFF03DAC5),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                }

                FloatingActionButton(
                    onClick = onCreate,
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Lista de Repositorios",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            // Barra de búsqueda
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar repositorios") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.DarkGray,
                    cursorColor = Color.Black
                )
            )

            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            // Mostrar mensajes de error
            state.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            state.inputError?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(filteredRepositories) { repo ->
                    RepositoryRow(repo = repo, onClick = { onItemClick(repo) })
                }
            }
        }
    }
}

@Composable
fun RepositoryRow(
    repo: RepositoryDto,
    onClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(22.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = repo.name ?: "Sin nombre",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = repo.description ?: "Sin descripción",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Text(
                text = repo.htmlUrl ?: "URL no disponible",
                fontSize = 14.sp,
                color = Color.Blue
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryListScreenPreview() {
    val sampleRepos = remember {
        mutableStateListOf(
            RepositoryDto(name = "Repo1", description = "Primer repo", htmlUrl = "https://github.com/repo1"),
            RepositoryDto(name = "Repo2", description = "Segundo repo", htmlUrl = "https://github.com/repo2"),
            RepositoryDto(name = "Repo3", description = null, htmlUrl = "https://github.com/repo3")
        )
    }
    val state = RepositoryUiState(
        repositories = sampleRepos,
        isLoading = false
    )

    ApiListScreen(
        state = state,
        onCreate = {
            sampleRepos.add(
                RepositoryDto(name = "NuevoRepo", description = "Descripción nueva", htmlUrl = "https://github.com/nuevo")
            )
        },
        onItemClick = { /* Acción al hacer click */ },
        onRefresh = {
            println("Actualizar presionado") // Simulación de recarga
        }
    )
}