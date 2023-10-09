package com.rmf.imagecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.rmf.imagecollection.presentation.NavGraphs
import com.rmf.imagecollection.presentation.appCurrentDestinationAsState
import com.rmf.imagecollection.presentation.destinations.Destination
import com.rmf.imagecollection.presentation.destinations.PhotoDetailScreenDestination
import com.rmf.imagecollection.presentation.startAppDestination
import com.rmf.imagecollection.ui.composable.MyBottomNavigation
import com.rmf.imagecollection.ui.theme.ImageCollectionTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageCollectionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val engine = rememberNavHostEngine()
                    val navController = engine.rememberNavController()

                    val currentDestination: Destination =
                        navController.appCurrentDestinationAsState().value
                            ?: NavGraphs.root.startAppDestination

                    Scaffold(
                        bottomBar = {
                            AnimatedContent(targetState = currentDestination != PhotoDetailScreenDestination,
                                label = "Bottom Navigation",
                                transitionSpec = {
                                    slideInVertically { height -> height } togetherWith
                                            slideOutVertically { height -> height }
                                }
                            ) { isVisible ->
                                if (isVisible) {
                                    MyBottomNavigation(navController = navController)
                                }
                            }
                        }) {
                        DestinationsNavHost(
                            engine = engine,
                            navController = navController,
                            navGraph = NavGraphs.root,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }
    }
}
