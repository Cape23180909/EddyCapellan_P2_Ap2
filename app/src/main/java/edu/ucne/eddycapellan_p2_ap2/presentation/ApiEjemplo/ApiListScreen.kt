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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.ucne.eddycapellan_p2_ap2.remote.dto.PropietarioDto
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ApiListScreen(
    state: RepositoryUiState,
    onCreate: () -> Unit,
    onItemClick: (RepositoryDto) -> Unit,
    onRefresh: () -> Unit,
    navController: NavController
) {
    var query by remember { mutableStateOf("") }

    val filteredRepositories = state.repositories.filter { repo ->
        query.isBlank() || (repo.name?.contains(query, ignoreCase = true) == true)
    }

    // Estado local para seleccionar un repo
    var selectedRepo by remember { mutableStateOf<RepositoryDto?>(null) }

    Scaffold(
        floatingActionButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FloatingActionButton(
                    onClick = onRefresh,
                    containerColor = Color(0xFF039BE5),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                }

                FloatingActionButton(
                    onClick = onCreate,
                    containerColor = Color(0xFF43A047),
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
                .background(Color(0xFFEDE7F6))
                .padding(paddingValues)
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Lista de Repositorios",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A148C),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar repositorios") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
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

            selectedRepo?.let { repo ->
                Button(
                    onClick = {
                        val owner = repo.propietario?.login ?: return@Button
                        val name = repo.name ?: return@Button
                        val encodedOwner = URLEncoder.encode(owner, StandardCharsets.UTF_8.toString())
                        val encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString())
                        navController.navigate("contributors/$encodedOwner/$encodedName")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE2DFE5)
                    )
                ) {
                    Text("Ver contribuyentes de: ${repo.name}")
                }
            }

            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF4A148C)
                )
            }

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
                    RepositoryRow(
                        repo = repo,
                        onClick = {
                            selectedRepo = repo
                        }
                    )
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
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(22.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = repo.name ?: "Sin nombre",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF4A148C)
            )
            Text(
                text = repo.description ?: "Sin descripción",
                fontSize = 16.sp,
                color = Color(0xFF7B1FA2)
            )
            Text(
                text = repo.htmlUrl ?: "URL no disponible",
                fontSize = 14.sp,
                color = Color(0xFF039BE5)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryListScreenPreview() {
    val navController = rememberNavController()

    val sampleRepos = remember {
        mutableStateListOf(
            RepositoryDto(
                name = "Repo1",
                description = "Primer repo",
                htmlUrl = "https://github.com/repo1",
                propietario = PropietarioDto(login = "usuario1")
            ),
            RepositoryDto(
                name = "Repo2",
                description = "Segundo repo",
                htmlUrl = "https://github.com/repo2",
                propietario = PropietarioDto(login = "usuario2")
            ),
            RepositoryDto(
                name = "Repo3",
                description = null,
                htmlUrl = "https://github.com/repo3",
                propietario = PropietarioDto(login = "usuario3")
            )
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
                RepositoryDto(
                    name = "NuevoRepo",
                    description = "Descripción nueva",
                    htmlUrl = "https://github.com/nuevo",
                    propietario = PropietarioDto(login = "nuevoUsuario")
                )
            )
        },
        onItemClick = { /* Acción al hacer click */ },
        onRefresh = {
            println("Actualizar presionado")
        },
        navController = navController
    )
}