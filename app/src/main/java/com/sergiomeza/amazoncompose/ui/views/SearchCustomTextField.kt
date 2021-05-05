package com.sergiomeza.amazoncompose.ui.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.sergiomeza.amazoncompose.R
import com.sergiomeza.amazoncompose.ui.screens.Screen
import com.sergiomeza.amazoncompose.utils.Constants
import com.sergiomeza.amazoncompose.utils.TextFieldState

@ExperimentalComposeUiApi
@Composable
fun SearchCustomTextField(
    label: String,
    state: TextFieldState = remember { TextFieldState() },
    imeOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text),
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    navController: NavController,
    currentRoute: String?
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
                    if (currentRoute == Screen.Search.route) {
                        navController.popBackStack()
                    }
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