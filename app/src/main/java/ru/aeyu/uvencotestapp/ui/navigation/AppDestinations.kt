package ru.aeyu.uvencotestapp.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppScreens {
    val route: String
    val navArguments: List<NamedNavArgument>
}

object ScreenMain : AppScreens {
    override val route: String = "screenMain"
    override val navArguments: List<NamedNavArgument> = emptyList()
}

object ScreenDetails : AppScreens {
    const val ITEM_ID_ARG = "itemId"
    private const val ROUTE_NAME = "screenDetails"

    override val route: String = "$ROUTE_NAME/{$ITEM_ID_ARG}"

    fun createRoute(itemId: Int) = "$ROUTE_NAME/${itemId}"

    override val navArguments: List<NamedNavArgument> = listOf(
        navArgument(ITEM_ID_ARG) {
            type = NavType.IntType
        },
    )
}
