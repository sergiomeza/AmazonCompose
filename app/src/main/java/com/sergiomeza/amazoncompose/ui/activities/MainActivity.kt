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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.data.model.Search
import com.sergiomeza.amazoncompose.data.viewmodel.SearchViewModel
import com.sergiomeza.amazoncompose.ui.screens.*
import com.sergiomeza.amazoncompose.ui.screens.AccountScreen
import com.sergiomeza.amazoncompose.ui.theme.*
import com.sergiomeza.amazoncompose.utils.Constants
import com.sergiomeza.amazoncompose.utils.TextFieldState
import com.sergiomeza.amazoncompose.utils.TextState
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {
    private val searchViewModel by viewModels<SearchViewModel>()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            var currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            var bottomNavVisible by remember { mutableStateOf(true) }
            //Animation
            var linePosition by remember { mutableStateOf(0f) }
            var lineWidth by remember { mutableStateOf(0f) }
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
                        topBar = {
                            AppBar(currentRoute = currentRoute,
                                searchHasFocus = { hasFocus ->
                                    bottomNavVisible = !hasFocus
                                }, navController = navController,
                                onSearch = {
                                    searchViewModel.addItem(Search(isRecent = true, text = it))
                                }
                            )
                         },
                        bottomBar = {
                            AnimatedVisibility(visible = bottomNavVisible) {
                                Column {
                                    Row (modifier = Modifier.fillMaxWidth()){
                                        items.forEachIndexed { index, _ -> // Draw the top decorator of the BottomNav
                                            Column(Modifier.weight(1f)) {
                                                val mod = if (index == 0) Modifier
                                                    .height(20.dp)
                                                    .fillMaxWidth() else Modifier
                                                Canvas(modifier = mod) {
                                                    translate(left = animatePosition) {
                                                        val canvasWidth = size.width
                                                        val canvasHeight = size.height
                                                        if (index == 0) { // Only Gets the first because is the Drawed in the screen
                                                            lineWidth = canvasWidth
                                                        }
                                                        drawLine(
                                                            start = Offset(x = 0f, y = canvasHeight),
                                                            end = Offset(
                                                                x = canvasWidth,
                                                                y = canvasHeight
                                                            ),
                                                            color = primary,
                                                            strokeWidth = 22f
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    BottomNavigation(
                                        backgroundColor = MaterialTheme.colors.surface,
                                        modifier = Modifier.navigationBarsPadding()
                                    ) {
                                        items.forEach { screen ->
                                            BottomNavigationItem(
                                                selected = currentRoute == screen.route,
                                                onClick = {
                                                    val indexSelected = items.indexOf(screen)
                                                    linePosition = if (indexSelected == 0) 0f else indexSelected * lineWidth
                                                    navController.navigate(screen.route)
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
                                                },
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    ) {
                        NavHost(navController = navController, startDestination = Screen.Home.route,
                            builder = {
                                items.forEach { screen ->
                                    composable(screen.route) {
                                        when(screen.route){
                                            Screen.Home.route -> HomeScreen(
                                                    navController = navController)
                                            Screen.Account.route -> AccountScreen(
                                                navController = navController)
                                            Screen.Cart.route -> CartScreen(
                                                navController = navController)
                                            Screen.Settings.route -> SettingsScreen(
                                                navController = navController)
                                        }
                                    }
                                }
                                composable(Screen.Search.route) {
                                    val searchItems by searchViewModel.searchItems.collectAsState()
                                    SearchScreen(
                                        navController = navController,
                                        items = searchItems,
                                        onRemoveSearch = {
                                            searchViewModel.removeSearch(it)
                                        }
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

@ExperimentalComposeUiApi
@Composable
private fun AppBar(currentRoute: String? = Screen.Home.route,
                   searchHasFocus: (Boolean) -> Unit,
                   navController: NavController,
                   onSearch: (String) -> Unit) {
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
                val searchState = remember { TextState() }
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
                    ) {
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
                                    navController = navController
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

@ExperimentalComposeUiApi
@Composable
fun SearchCustomTextField(
    label: String,
    state: TextFieldState = remember { TextFieldState() },
    imeOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text),
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    navController: NavController
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
    val focusRequester = remember { FocusRequester() }
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
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
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                searchState = if (focused) {
                    Constants.SearchState.SEARCHING
                } else {
                    Constants.SearchState.NORMAL
                }
                state.onFocusChange(focusState.isFocused)
                if (!focused) {
                    state.enableShowErrors()
                } else {
                    navController.navigate(Screen.Search.route) {
                        launchSingleTop = true
                    }
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
                        state.onFocusChange(false)
                        focusRequester.freeFocus()
                        navController.popBackStack()
                    }
                }
            ) {
                Icon(
                    imageVector = iconSearchLeading,
                    contentDescription = stringResource(R.string.search_amazon),
                    tint = if (isSystemInDarkTheme())
                        Color.White
                    else Color.Black
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