package ru.vlyashuk.pointmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.vlyashuk.pointmap.navigation.BottomNavScreen
import ru.vlyashuk.pointmap.navigation.Routes
import ru.vlyashuk.pointmap.ui.screens.AddPointScreen
import ru.vlyashuk.pointmap.ui.screens.MainScreen
import ru.vlyashuk.pointmap.ui.screens.MapScreen
import ru.vlyashuk.pointmap.ui.screens.ProfileScreen
import ru.vlyashuk.pointmap.ui.screens.UpdatePointScreen
import ru.vlyashuk.pointmap.ui.theme.PointMapTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PointMapTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val currentRoute = currentRoute(navController)
                        if (currentRoute in listOf(
                                BottomNavScreen.Main.route,
                                BottomNavScreen.Map.route,
                                BottomNavScreen.Profile.route
                            )
                        ) {
                            BottomNavBar(navController)
                        }
                    },
                    floatingActionButton = {
                        val currentRoute = currentRoute(navController)
                        if (currentRoute == BottomNavScreen.Main.route) {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(
                                        Routes.AddPoint.route ?: "add_point"
                                    )
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = stringResource(R.string.add_point)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavScreen.Main.route,
                    ) {
                        composable(BottomNavScreen.Main.route) {
                            MainScreen(navController)
                        }
                        composable(BottomNavScreen.Map.route) {
                            MapScreen(Modifier.padding(innerPadding))
                        }
                        composable(BottomNavScreen.Profile.route) {
                            ProfileScreen(Modifier.padding(innerPadding))
                        }
                        composable(Routes.AddPoint.route ?: "add_point") {
                            AddPointScreen(navController)
                        }
                        composable(
                            route = Routes.UpdatePoint,
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val pointId = backStackEntry.arguments?.getLong("id") ?: 0L
                            UpdatePointScreen(
                                navController = navController,
                                pointViewModel = hiltViewModel(),
                                pointId = pointId
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(BottomNavScreen.Main, BottomNavScreen.Map, BottomNavScreen.Profile)
    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}