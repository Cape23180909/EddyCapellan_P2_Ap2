package edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ContribuidorScreen(
    jefe: String,
    repositorio: String,
    viewModel: ApiViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val contributors by viewModel.contributors.collectAsState()
    LaunchedEffect(repositorio) {
        viewModel.fetchContributors(jefe, repositorio)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Contribuyentes de $repositorio", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Volver")
        }
        LazyColumn {
            items(contributors) { contributor ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Usuario: ${contributor.login}")
                        Text("Contribuciones: ${contributor.contributions}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContribuidorScreenPreview() {
    MaterialTheme {
        ContribuidorScreen(
            jefe = "Enel Almonte",
            repositorio = "DemoAp2",
            onBack = {}
        )
    }
}