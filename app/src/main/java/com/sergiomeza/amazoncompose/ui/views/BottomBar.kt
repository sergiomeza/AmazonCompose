package com.sergiomeza.amazoncompose.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.sergiomeza.amazoncompose.ui.screens.Screen
import com.sergiomeza.amazoncompose.ui.theme.primary
import com.sergiomeza.amazoncompose.utils.Constants
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@ExperimentalAnimationApi
@Composable
fun BottomBar(
    navController: NavHostController,
    scrollUpState: State<Boolean?>,
    bottomNavVisible: Boolean,
    currentRoute: String?
){
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
    val bottomNavPos by animateFloatAsState(if (scrollUpState.value == true) 170f else 0f)
    AnimatedVisibility(visible = bottomNavVisible) {
        Column(modifier = Modifier.graphicsLayer {
            translationY = (bottomNavPos)
        }) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Constants.navItems.forEachIndexed { index, _ -> // Draw the top decorator of the BottomNav
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
                                    start = Offset(
                                        x = 0f,
                                        y = canvasHeight
                                    ),
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
                Constants.navItems.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            val indexSelected = Constants.navItems.indexOf(screen)
                            linePosition =
                                if (indexSelected == 0) 0f else indexSelected * lineWidth
                            navController.navigate(screen.route)
                        },
                        icon = {
                            var icon = screen.iconFilled
                            if (currentRoute != screen.route) {
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