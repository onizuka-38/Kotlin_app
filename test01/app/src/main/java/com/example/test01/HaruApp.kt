package com.example.test01

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

object Routes {
    const val HOME = "home"
    const val COGNITIVE = "cognitive"
    const val DIARY = "diary"
    const val LEARN = "learn"
    const val MY = "my"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HaruApp() {
    val nav = rememberNavController()
    val currentRoute = nav.currentRoute()
    val topTitle = when (currentRoute) {
        Routes.HOME -> "홈"
        Routes.COGNITIVE -> "인지 능력 검사"
        Routes.DIARY -> "오늘의 일기"
        Routes.LEARN -> "오늘의 학습"
        Routes.MY -> "마이"
        else -> ""
    }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(topTitle) }) },
        bottomBar = {
            HaruBottomBar(
                currentRoute = currentRoute,
                onSelect = { route ->
                    nav.navigate(route) {
                        popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = nav,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) { HomeScreen(onNavigate = { nav.navigate(it) }) }
            composable(Routes.COGNITIVE) { CognitiveIntroScreen(onStart = { /* TODO: 다음 단계 이동 */ }) }
            composable(Routes.DIARY) { SimplePage("오늘의 일기") }
            composable(Routes.LEARN) { SimplePage("오늘의 학습") }
            composable(Routes.MY) { MyScreen() }
        }
    }
}

@Composable
private fun HaruBottomBar(
    currentRoute: String?,
    onSelect: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute in listOf(Routes.HOME, Routes.COGNITIVE, Routes.DIARY, Routes.LEARN),
            onClick = { onSelect(Routes.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "홈") },
            label = { Text("홈") }
        )
        NavigationBarItem(
            selected = currentRoute == Routes.MY,
            onClick = { onSelect(Routes.MY) },
            icon = { Icon(Icons.Default.Person, contentDescription = "마이") },
            label = { Text("마이") }
        )
    }
}

@Composable
private fun NavHostController.currentRoute(): String? {
    val backStackEntry by currentBackStackEntryAsState()
    return backStackEntry?.destination?.route
}
