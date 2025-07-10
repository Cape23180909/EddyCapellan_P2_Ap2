package edu.ucne.eddycapellan_p2_ap2.presentation.navigation

import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ApiListScreen
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ApiScreen
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ApiViewModel
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ContribuidorScreen
import edu.ucne.eddycapellan_p2_ap2.remote.dto.RepositoryDto

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
                    navHostController.navigate("ApiEdit/new") // Aquí navega correctamente
                },
                onItemClick = { repo ->
                    navHostController.navigate("ApiEdit/${repo.name}")
                },
                onRefresh = {
                    apiViewModel.fetchRepositories("username")
                },
                navController = navHostController
            )
        }

        composable(
            route = "ApiEdit/{name}",
            arguments = listOf(navArgument("name") { defaultValue = "new" })
        ) { backStackEntry ->
            val nameParam = backStackEntry.arguments?.getString("name") ?: "new"
            val isEdit = nameParam != "new"
            val repository = if (isEdit) apiViewModel.getApiByName(nameParam) else null

            val uiState = repository?.let { apiViewModel.toUiState(it) }
                ?: apiViewModel.toUiState(RepositoryDto("", "", "", null))

            ApiScreen(
                state = uiState,
                onSave = { name, description, htmlUrl ->
                    val newRepo = RepositoryDto(name, description, htmlUrl, null)
                    apiViewModel.saveApi(newRepo)
                    navHostController.popBackStack()
                },
                onCancel = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(
            route = "contributors/{jefe}/{repositorio}"
        ) { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("jefe") ?: ""
            val repo = backStackEntry.arguments?.getString("repositorio") ?: ""
            ContribuidorScreen(
                jefe = owner,
                repositorio = repo,
                onBack = { navHostController.popBackStack() }
            )
        }
    }
}