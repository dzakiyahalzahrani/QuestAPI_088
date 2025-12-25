package com.example.questapi_088.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.questapi_088.uicontroller.route.DestinasiDetail
import com.example.questapi_088.uicontroller.route.DestinasiEdit
import com.example.questapi_088.uicontroller.route.DestinasiEntry
import com.example.questapi_088.uicontroller.route.DestinasiHome
import com.example.questapi_088.view.DetailSiswaScreen
import com.example.questapi_088.view.EditSiswaScreen
import com.example.questapi_088.view.EntrySiswaScreen
import com.example.questapi_088.view.HomeScreen


@Composable
fun DataSiswaApp(navController: NavHostController = rememberNavController(), modifier: Modifier){
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination = DestinasiHome.route, modifier = Modifier ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToItemUpdate = {
                    navController.navigate("${DestinasiDetail.route}/${it}")
                },
                modifier = modifier)
        }
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(navigateBack = { navController.navigate(DestinasiHome.route) })
        }
        composable(
            DestinasiDetail.routeWithArgs, arguments = listOf(navArgument(DestinasiDetail
                .itemIdArg) {
                type = NavType.IntType })
        ){
            // --- PERBAIKAN DI BARIS INI ---
            DetailSiswaScreen(
                navigateToEditItem = { navController.navigate("${DestinasiEdit.route}/${it}") }, // Spasi dihilangkan
                navigateBack = { navController.navigate(DestinasiHome.route) }
            )
            // --- AKHIR PERBAIKAN ---
        }
        composable(
            DestinasiEdit.routeWithArgs, arguments = listOf(navArgument(DestinasiEdit.itemIdArg
            ){
                type = NavType.IntType})){
            EditSiswaScreen(navigateBack = { navController.navigate(DestinasiHome.route) },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}
