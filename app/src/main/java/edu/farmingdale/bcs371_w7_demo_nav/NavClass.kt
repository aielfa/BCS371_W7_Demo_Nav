package edu.farmingdale.bcs371_w7_demo_nav


import android.content.Intent
import android.transition.Fade
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("basic") {
            BasicOperations(navController.toString())
        }
        composable("first_screen") {
            FirstScreen(navController)
        }
        composable("second_screen") {
            SecondScreen(navController)
        }
        composable("pizza_party_screen") {
            PizzaPartyScreen(navController)
        }
        composable("gpa_calculator_screen") {
            gpaappFun(navController)
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp).wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "First Screen")
            Button(onClick = { navController.navigate("second_screen") }) {
                Text(text = "Go to Second Screen")
            }
        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    var sliderValue by remember { mutableStateOf(0.5f) }
    var isChecked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderValue,
            onValueChange = { if (isChecked) sliderValue = it },
            enabled = isChecked,
            modifier = Modifier.fillMaxWidth()
        )
        Text(fontSize = 20.sp, text = "Second Screen")
        Button(onClick = { navController.navigate("basic") }) {
            Text(fontSize = 20.sp, text = "Go to Activity")
        }
        // Use navController for navigation
        Button(onClick = { navController.navigate("pizza_party_screen") }) {
            Text(fontSize = 20.sp, text = "Go to Pizza Party")
        }

        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(durationMillis = 1000, easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )
        delay(3000)
        navController.navigate("first_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = R.drawable.fsclogo), contentDescription = null)
    }
}
