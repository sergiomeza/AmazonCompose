package com.sergiomeza.amazoncompose.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.data.model.Search
import com.sergiomeza.amazoncompose.data.viewmodel.ScrollViewModel
import com.sergiomeza.amazoncompose.data.viewmodel.SearchViewModel
import com.sergiomeza.amazoncompose.ui.screens.*
import com.sergiomeza.amazoncompose.ui.screens.AccountScreen
import com.sergiomeza.amazoncompose.ui.theme.*
import com.sergiomeza.amazoncompose.ui.views.BottomBar
import com.sergiomeza.amazoncompose.ui.views.CustomAppBar
import com.sergiomeza.amazoncompose.utils.Constants
import com.sergiomeza.amazoncompose.utils.TextFieldState
import com.sergiomeza.amazoncompose.utils.TextState
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {
    private val searchViewModel by viewModels<SearchViewModel>()
    private val scrollViewModel by viewModels<ScrollViewModel>()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            var bottomNavVisible by remember { mutableStateOf(true) }
            val keyboardController = LocalSoftwareKeyboardController.current
            val searchState = remember { TextState() }
            val scrollUpState = scrollViewModel.scrollUp.observeAsState()
            AmazonComposeTheme {
                ProvideWindowInsets {
                    Scaffold(
                        topBar = {
                            CustomAppBar(
                                currentRoute = currentRoute,
                                searchHasFocus = { hasFocus ->
                                    bottomNavVisible = !hasFocus
                                }, navController = navController,
                                onSearch = {
                                    searchViewModel.addItem(Search(isRecent = true, text = it))
                                    searchState.onFocusChange(false)
                                    navController.navigate("${Screen.SearchResult.route}/${it}") {
                                        launchSingleTop = true
                                    }
                                },
                                scrollUpState = scrollUpState,
                                searchState = searchState
                            )
                        },
                        bottomBar = {
                            BottomBar(
                                navController = navController,
                                scrollUpState = scrollUpState,
                                bottomNavVisible = bottomNavVisible,
                                currentRoute = currentRoute
                            )
                        }
                    ) {
                        NavHost(navController = navController,
                            startDestination = Screen.Home.route,
                            builder = {
                                Constants.navItems.forEach { screen ->
                                    composable(screen.route) {
                                        when (screen.route) {
                                            Screen.Home.route -> HomeScreen(
                                                navController = navController,
                                                scrollViewModel = scrollViewModel
                                            )
                                            Screen.Account.route -> AccountScreen(
                                                navController = navController
                                            )
                                            Screen.Cart.route -> CartScreen(
                                                navController = navController
                                            )
                                            Screen.Settings.route -> SettingsScreen(
                                                navController = navController
                                            )
                                        }
                                    }
                                }
                                composable(Screen.Search.route) {
                                    SearchScreen(
                                        viewModel = searchViewModel,
                                        onRemoveSearch = {
                                            searchViewModel.removeSearch(it)
                                        },
                                        onSearchClicked = {
                                            searchState.onFocusChange(false)
                                            searchState.text = it
                                            keyboardController?.hide()
                                            navController.navigate("${Screen.SearchResult.route}/${it}") {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                                composable("${Screen.SearchResult.route}/{term}",
                                    arguments = listOf(navArgument("term") {
                                        type = NavType.StringType
                                    })
                                ) {
                                    SearchResultScreen(
                                        navController = navController,
                                        term = it.arguments?.getString("term"),
                                        scrollViewModel = scrollViewModel
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}