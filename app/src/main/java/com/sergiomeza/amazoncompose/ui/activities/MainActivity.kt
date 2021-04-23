package com.sergiomeza.amazoncompose.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.ui.screens.*
import com.sergiomeza.amazoncompose.ui.screens.AccountScreen
import com.sergiomeza.amazoncompose.ui.theme.AmazonComposeTheme
import com.sergiomeza.amazoncompose.ui.theme.gradientHeader
import com.sergiomeza.amazoncompose.utils.TextFieldState
import com.sergiomeza.amazoncompose.utils.TextState
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            AmazonComposeTheme {
                ProvideWindowInsets {
                    Scaffold(
                        topBar = { AppBar(currentRoute = currentRoute) },
                        bottomBar = {
                            BottomNavigation(
                                backgroundColor = MaterialTheme.colors.surface,
                                modifier = Modifier.navigationBarsPadding()
                            ) {
                                val items = listOf(
                                    Screen.Home,
                                    Screen.Account,
                                    Screen.Cart,
                                    Screen.Settings
                                )
                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo = navController.graph.startDestination
                                                launchSingleTop = true
                                            }
                                        },
                                        icon = {
                                            if (currentRoute == screen.route) screen.iconFilled
                                            else screen.icon?.let {
                                                Icon(
                                                    it,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(24.dp),
                                                    tint = if (currentRoute == screen.route)
                                                        MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        NavHost(navController = navController, startDestination = Screen.Home.route,
                            builder = {
                                composable(Screen.Home.route) {
                                    HomeScreen(
                                        navController = navController)
                                }
                                composable(Screen.Account.route) {
                                    AccountScreen(
                                        navController = navController)
                                }
                                composable(Screen.Cart.route) {
                                    CartScreen(
                                        navController = navController)
                                }
                                composable(Screen.Settings.route) {
                                    SettingsScreen(
                                        navController = navController)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppBar(currentRoute: String? = Screen.Home.route) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
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
                                tint = Color.Black
                            )
                        }
                        IconButton(
                            onClick = { /* todo */ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search_amazon),
                                tint = Color.Black
                            )
                        }
                    }
                }
            } else {
                val searchState = remember { TextState() }
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(gradientHeader)
                            )
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 16.dp),
                        ) {
                            Card(Modifier.fillMaxWidth().padding(8.dp),elevation = 4.dp) {
                                SearchCustomTextField(
                                    label = stringResource(id = R.string.search_amazon),
                                    state = searchState,
                                    imeOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                    imeAction = ImeAction.Done,
                                    onImeAction = {
                                        // TODO: 23/04/2021
                                    }
                                )
                            }
                        }
                    }
                    if (currentRoute != Screen.Settings.route) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(0xFFDBF3FA)
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
                                    tint = Color.Black
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
                                        tint = Color.Black
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

@Composable
fun SearchCustomTextField(
    label: String,
    state: TextFieldState = remember { TextFieldState() },
    imeOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text),
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = state.text,
        onValueChange = {
            state.text = it
        },
        placeholder = {
            Text(
                text = label,
                style = MaterialTheme.typography.body2
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                state.onFocusChange(focused)
                if (!focused) {
                    state.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        keyboardOptions = imeOptions.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            },
        ),
        leadingIcon = {
            IconButton(
                modifier = Modifier.
                    then(Modifier.size(24.dp)),
                onClick = { /* todo */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_amazon),
                    tint = Color.Black
                )
            }
        },
        trailingIcon = {
            Row {
                IconButton(
                    modifier = Modifier.
                        then(Modifier.size(24.dp)),
                    onClick = { /* todo */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = stringResource(R.string.camera),
                        tint = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                IconButton(
                    modifier = Modifier.
                        then(Modifier.size(24.dp)),
                    onClick = { /* todo */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.RecordVoiceOver,
                        contentDescription = stringResource(R.string.mic),
                        tint = Color.LightGray
                    )
                }
            }
        }
    )
}

/**
 * To be removed when [TextField]s support error
 */
@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}