package com.efor18.takumi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.efor18.takumi.characterdetails.navigation.characterDetailsGraph
import com.efor18.takumi.characterlist.navigation.Characters
import com.efor18.takumi.characterlist.navigation.charactersGraph
import com.efor18.takumi.episodedetails.navigation.episodeDetailsGraph

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = Characters,
) {
    ProvideNavigator(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            // Feature-specific navigation graphs
            charactersGraph(navController = navController)
            characterDetailsGraph()
            episodeDetailsGraph()
        }
    }
}
