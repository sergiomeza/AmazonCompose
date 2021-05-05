package com.sergiomeza.amazoncompose.ui.views

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.ui.screens.Screen
import com.sergiomeza.amazoncompose.ui.theme.gradientHeader
import com.sergiomeza.amazoncompose.ui.theme.gradientHeaderDark
import com.sergiomeza.amazoncompose.ui.theme.locationHeader
import com.sergiomeza.amazoncompose.ui.theme.locationHeaderDark
import com.sergiomeza.amazoncompose.utils.TextState
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalComposeUiApi
@Composable
fun CustomAppBar(
    currentRoute: String? = Screen.Home.route,
    searchHasFocus: (Boolean) -> Unit,
    navController: NavController,
    onSearch: (String) -> Unit,
    scrollUpState: State<Boolean?>,
    searchState: TextState
) {
    val position by animateFloatAsState(if (scrollUpState.value == true) -180f else 0f)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .graphicsLayer {
                // TODO: Implement
//                translationY = (position)
            }
    ) {
        Box {
            if (currentRoute == Screen.Account.route) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.amazon_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(start = 16.dp)
                    )
                    Row {
                        IconButton(
                            onClick = { /* todo */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsNone,
                                contentDescription = stringResource(R.string.notifications),
                                tint = if (isSystemInDarkTheme())
                                    Color.White
                                else
                                    Color.Black
                            )
                        }
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.Search.route) {
                                    launchSingleTop = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search_amazon),
                                tint = if (isSystemInDarkTheme())
                                    Color.White
                                else
                                    Color.Black
                            )
                        }
                    }
                }
            } else {
                searchHasFocus(searchState.isFocused)
                var paddingDp by remember { mutableStateOf(8.dp) }
                paddingDp = if (searchState.isFocused){
                    0.dp
                } else {
                    16.dp
                }
                val animatePadding: Dp by animateDpAsState(
                    targetValue = paddingDp,
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 50,
                        easing = LinearOutSlowInEasing
                    )
                )
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    if (isSystemInDarkTheme()) gradientHeaderDark else gradientHeader
                                )
                            )
                    ) { //Header search
                        Box(
                            modifier = Modifier.padding(horizontal = animatePadding),
                        ) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(animatePadding / 2)
                                    .height(50.dp),
                                elevation = 4.dp) {
                                SearchCustomTextField(
                                    label = stringResource(id = R.string.search_amazon),
                                    state = searchState,
                                    imeOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                    imeAction = ImeAction.Done,
                                    onImeAction = {
                                        if(searchState.isValid && searchState.text.isNotEmpty()) {
                                            onSearch(searchState.text)
                                        }
                                    },
                                    navController = navController,
                                    currentRoute = currentRoute
                                )
                            }
                        }
                    }
                    if (currentRoute != Screen.Settings.route) {
                        if (!searchState.isFocused) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (isSystemInDarkTheme())
                                            locationHeaderDark
                                        else locationHeader
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                        .clickable {
                                            // TODO: 23/04/2021
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = stringResource(R.string.location),
                                        tint = if (isSystemInDarkTheme())
                                            Color.White
                                        else
                                            Color.Black
                                    )
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.location),
                                            style = MaterialTheme.typography.body2
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = stringResource(R.string.location),
                                            tint = if (isSystemInDarkTheme())
                                                Color.White
                                            else
                                                Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}