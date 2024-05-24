package ru.aeyu.uvencotestapp.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.aeyu.uvencotestapp.ui.screens.details.DetailsScreen
import ru.aeyu.uvencotestapp.ui.screens.details.DetailsScreenViewModel
import ru.aeyu.uvencotestapp.ui.screens.main.MainScreen
import ru.aeyu.uvencotestapp.ui.screens.main.MainScreenViewModel

@Composable
fun AppNavigationHost(
    navHostController: NavHostController,
    onExit: (Boolean) -> Unit
) {

    NavHost(
        navController = navHostController,
        startDestination = ScreenMain.route,
    ) {
        composable(
            route = ScreenMain.route,
        ) {
            val mainViewModel: MainScreenViewModel = hiltViewModel()
            MainScreen(
                mainViewModel = mainViewModel,
                onItemClick = { item ->
                    mainViewModel.onItemClicked(item)
                    navHostController.navigate(
                        ScreenDetails.createRoute(
                            itemId = item.id,
                        )
                    )
                },
                onUSignClick = {
                    if (!navHostController.popBackStack())
                        onExit(true)
                },
                onStart = {
                    mainViewModel.onAddObserver()
                    mainViewModel.onStart()
                },
                onStop = {
                    mainViewModel.onRemoveObserver()
                }
            )
        }

        composable(
            route = ScreenDetails.route,
            arguments = ScreenDetails.navArguments
        ) {
            val detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
            val snackBarHostState = remember{
                SnackbarHostState()
            }
            DetailsScreen(
                detailsViewModel = detailsScreenViewModel,
                snackBarHostState = snackBarHostState,
                onUSignClick = {
                    navHostController.popBackStack()
                },
                onSaveClick = {
                   detailsScreenViewModel.saveItem(it)
                },
                onStart = {
                    detailsScreenViewModel.onAddObserver()
                },
                onStop = {
                    detailsScreenViewModel.onRemoveObserver()
                }
            )
        }
    }
}
