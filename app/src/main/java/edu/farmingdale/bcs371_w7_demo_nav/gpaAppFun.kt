package edu.farmingdale.bcs371_w7_demo_nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun gpaappFun(navController: NavController) {

    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }

    // Declare variables for GPA result and background color
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.White) }
    var btnLabel by remember { mutableStateOf("Compute GPA") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backColor)
            .padding(16.dp),  // Added padding for better layout
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = grade1,
            onValueChange = { grade1 = it },
            label = { Text("Course 1 Grade") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = grade2,
            onValueChange = { grade2 = it },
            label = { Text("Course 2 Grade") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = grade3,
            onValueChange = { grade3 = it },
            label = { Text("Course 3 Grade") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (btnLabel == "Compute GPA") {
                    val gpaVal = calGPA(grade1, grade2, grade3)
                    if (gpaVal != null) {
                        gpa = gpaVal.toString()

                        // Change background color based on GPA
                        backColor = when {
                            gpaVal < 60 -> Color.Red
                            gpaVal in 60.0..79.0 -> Color.Yellow
                            else -> Color.Green
                        }
                        btnLabel = "Clear"
                    } else {
                        gpa = "Invalid input"
                    }
                } else {
                    // Reset all values to none
                    grade1 = ""
                    grade2 = ""
                    grade3 = ""
                    gpa = ""
                    backColor = Color.White
                    btnLabel = "Compute GPA"
                }
            },
            enabled = grade1.isNotEmpty() && grade2.isNotEmpty() && grade3.isNotEmpty(), // Disable if any input is empty
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(btnLabel)
        }

        if (gpa.isNotEmpty()) {
            Text(text = "GPA: $gpa", modifier = Modifier.padding(top = 16.dp))
        }

        Button(
            onClick = { navController.navigate("pizza_party_screen") },
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
        ) {
            Text("Back to Pizza Party")
        }
    }
}

fun calGPA(grade1: String, grade2: String, grade3: String): Double? {
    return try {
        val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
        if (grades.all { it in 0.0..100.0 }) { // Ensure grades are between 0 and 100
            grades.average()
        } else {
            null // Return null if any grade is out of bounds
        }
    } catch (e: NumberFormatException) {
        null
    }
}
