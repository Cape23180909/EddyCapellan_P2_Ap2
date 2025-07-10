package edu.ucne.eddycapellan_p2_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ApiListScreen
import edu.ucne.eddycapellan_p2_ap2.presentation.ApiEjemplo.ApiViewModel
import edu.ucne.eddycapellan_p2_ap2.presentation.contribuidor.ContribuidorScreen

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
                    navHostController.navigate("ApiEdit/new")
                },
                onRefresh = {
                    apiViewModel.getApi()
                },
                navController = navHostController,
                onRepositorySelected = { repo ->
                    // Extraer owner y nombre del repositorio de la URL
                    val urlParts = repo.htmlUrl?.split("/") ?: listOf()
                    if (urlParts.size >= 5) {
                        val owner = urlParts[3]
                        val repoName = urlParts[4]
                        navHostController.navigate("Contributors/$owner/$repoName")
                    }
                }
            )
        }

        composable(
            "Contributors/{owner}/{repoName}",
            arguments = listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("repoName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("owner") ?: ""
            val repoName = backStackEntry.arguments?.getString("repoName") ?: ""
            ContribuidorScreen(
                owner = owner,
                repoName = repoName,
                onBack = { navHostController.popBackStack() }
            )
        }
    }
}