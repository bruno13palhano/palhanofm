package com.bruno13palhano.palhanofm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bruno13palhano.palhanofm.ui.components.BottomMenu
import com.bruno13palhano.palhanofm.ui.components.DrawerMenu
import com.bruno13palhano.palhanofm.ui.navigation.MainDestinations
import com.bruno13palhano.palhanofm.ui.navigation.MainNavGraph
import com.bruno13palhano.palhanofm.ui.theme.PalhanoFMTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PalhanoFMTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                var showBottomBar by rememberSaveable { mutableStateOf(true) }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                var appTitle by remember { mutableStateOf("") }

                appTitle = when (currentDestination?.route) {
                    MainDestinations.SCHEDULES_ROUTE -> {
                        stringResource(id = R.string.schedules_label)
                    }

                    MainDestinations.SETTINGS_ROUTE -> {
                        stringResource(id = R.string.settings_label)
                    }

                    else -> {
                        stringResource(id = R.string.app_name)
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawerMenu(
                        drawerState = drawerState,
                        navController = navController 
                    ) {
                        val coroutineScope = rememberCoroutineScope()
                        
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text(text = appTitle) },
                                    navigationIcon = {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    if (drawerState.isOpen) drawerState.close()
                                                    else drawerState.open()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Menu,
                                                contentDescription = stringResource(id = R.string.drawer_menu_description)
                                            )
                                        }
                                    }
                                )
                            },
                            bottomBar = {
                                if (showBottomBar) BottomMenu(navController = navController)
                            }
                        ) {
                            MainNavGraph(
                                modifier = Modifier.padding(it),
                                navController = navController,
                                showBottomMenu = { show ->
                                    showBottomBar = show
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}