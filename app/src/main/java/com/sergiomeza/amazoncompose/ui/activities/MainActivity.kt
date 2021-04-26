package com.sergiomeza.amazoncompose.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.ui.screens.*
import com.sergiomeza.amazoncompose.ui.screens.AccountScreen
import com.sergiomeza.amazoncompose.ui.theme.AmazonComposeTheme
import com.sergiomeza.amazoncompose.ui.theme.gradientHeader
import com.sergiomeza.amazoncompose.ui.theme.primary
import com.sergiomeza.amazoncompose.utils.Constants
import com.sergiomeza.amazoncompose.utils.TextFieldState
import com.sergiomeza.amazoncompose.utils.TextState
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            var linePosition by remember { mutableStateOf(0f) }
            val animatePosition: Float by animateFloatAsState(
                targetValue = linePosition,
                animationSpec = tween(
                    durationMillis = 300,
                    delayMillis = 50,
                    easing = LinearOutSlowInEasing
                )
            )
            val items = listOf(
                Screen.Home,
                Screen.Account,
                Screen.Cart,
                Screen.Settings
            )
            AmazonComposeTheme {
                ProvideWindowInsets {
                    Scaffold(
                        topBar = { AppBar(currentRoute = currentRoute) },
                        bottomBar = {
                            Column {
                                Canvas(modifier = Modifier
                                    .width(85.dp)
                                    .height(20.dp)
                                ) {
                                    translate(left = animatePosition) {
                                        val canvasWidth = size.width
                                        val canvasHeight = size.height
                                        drawLine(
                                            start = Offset(x = 0f, y = canvasHeight),
                                            end = Offset(x = canvasWidth, y = canvasHeight),
                                            color = primary,
                                            strokeWidth = 22f
                                        )
                                    }
                                }
                                BottomNavigation(
                                    backgroundColor = MaterialTheme.colors.surface,
                                    modifier = Modifier.navigationBarsPadding()
                                ) {
                                    items.forEach { screen ->
                                        BottomNavigationItem(
                                            modifier = Modifier,
                                            selected = currentRoute == screen.route,
                                            onClick = {
                                                val indexSelected = items.indexOf(screen)
                                                Log.d("TAG", "onCreate: SELECTED INDEX $indexSelected")
                                                linePosition = if (indexSelected == 0) 0f else indexSelected * 280f
                                                navController.navigate(screen.route) {
                                                    popUpTo = navController.graph.startDestination
                                                    launchSingleTop = true
                                                }
                                            },
                                            icon = {
                                                var icon = screen.iconFilled
                                                if (currentRoute != screen.route){
                                                    icon = screen.icon
                                                }

                                                Icon(
                                                    icon!!,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(24.dp),
                                                    tint = if (currentRoute == screen.route)
                                                        MaterialTheme.colors.primary else MaterialTheme.colors.secondary
                                                )
                                            }
                                        )
                                    }
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

@ExperimentalComposeUiApi
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
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .height(50.dp),
                                elevation = 4.dp) {
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

@ExperimentalComposeUiApi
@Composable
fun SearchCustomTextField(
    label: String,
    state: TextFieldState = remember { TextFieldState() },
    imeOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text),
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    //Search State
    var searchState: Constants.SearchState
            by remember { mutableStateOf(Constants.SearchState.NORMAL) }
    //search icon
    val iconSearchLeading: ImageVector =
        if (searchState == Constants.SearchState.NORMAL)
            Icons.Default.Search
        else
            Icons.Default.ArrowBack
    //
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        ),
        value = state.text,
        onValueChange = {
            state.text = it
            searchState = if (it.isNotEmpty()){
                Constants.SearchState.SEARCHING
            } else { Constants.SearchState.NORMAL }
        },
        placeholder = {
            Text(
                text = label,
                style = MaterialTheme.typography.body2
            )
        },
        modifier = Modifier
            .fillMaxWidth()
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
                keyboardController?.hide()
            },
        ),
        leadingIcon = {
            IconButton(
                modifier = Modifier.
                then(Modifier.size(24.dp)),
                onClick = {
                    if (searchState == Constants.SearchState.SEARCHING){
                        searchState = Constants.SearchState.NORMAL
                        state.text = ""
                        keyboardController?.hide()
                    }
                }
            ) {
                Icon(
                    imageVector = iconSearchLeading,
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

@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewSearch(){
    SearchCustomTextField(label = "Hola Test")
}