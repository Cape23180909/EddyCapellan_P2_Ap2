package edu.ucne.eddycapellan_p2_ap2.presentation.navigation

import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ApiListScreen
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ApiViewModel

@Composable
fun ApiNavHost(
    navHostController: NavHostController,
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    NavHost(
        navController = navHostController,
        startDestination = "ApiList"
    ) {
        composable("ApiList") {
            val uiState by apiViewModel.uiState.collectAsState()

            ApiListScreen(
                state = uiState,
                onCreate = {
                    navHostController.navigate("ApiEdit/new") // Ruta para crear nuevo
                },
                onItemClick = { repo ->
                    navHostController.navigate("ApiDetail/${repo.name}") // Ruta para detalles
                },
                onRefresh = { apiViewModel.fetchRepositories("username") }
            )
        }

//        composable("Api/{name}") { backStackEntry ->
//            val repoNameParam = backStackEntry.arguments?.getString("name")
//            val isEdit = repoNameParam != "null"
//            val repository = if (isEdit) apiViewModel.getApiByName(repoNameParam) else null
//
//            ApiScreen(
//                repository = repository,
//                onSave = { name, description, htmlUrl ->
//                    val newRepo = RepositoryDto(
//                        name = name,
//                        description = description,
//                        htmlUrl = htmlUrl
//                    )
//                    apiViewModel.saveApi(newRepo)
//                    navHostController.popBackStack()
//                },
//                onCancel = {
//                    navHostController.popBackStack()
//                }
//            )
//        }
    }
}