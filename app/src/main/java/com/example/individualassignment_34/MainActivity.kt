package com.example.individualassignment_34

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.individualassignment_34.ui.theme.IndividualAssignment_34Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndividualAssignment_34Theme {
                MakeScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MakeScreen() {
    val state = rememberLazyGridState() //an object to store the state of the lazyVerticalGrid
    val maxCount = remember {mutableStateOf(30)}    //the max number to display
    val coroutineScope = rememberCoroutineScope()       //the scope for the button and number update coroutines

    Scaffold(modifier = Modifier.fillMaxSize(),
        //A floating button to take the user to the top. Stays in place as user scrolls
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //on click, run a coroutine to shift back to the top and reset the numbers
                    coroutineScope.launch(Dispatchers.Main) {
                        state.animateScrollToItem(0)
                        maxCount.value = 30
                    }
                }
            ) {
                Box(Modifier.size(height = 24.dp, width = 100.dp)){
                    Text(
                        text = "Reset to 1-30",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) {
        //A grid with scrolling enabled, bound to the grid state defined earlier
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Fixed(3),
            userScrollEnabled = true,
            contentPadding = PaddingValues(24.dp)
        ) {
            //Whenever the user scrolls, this grid should recompose. This will cause
            // atBottom to be reevaluated and the coroutine to run again.
            val atBottom = !state.canScrollForward  //an object to indicate if the user is at the bottom of the scrollable section
            //A coroutine to increase the max display number after a short delay.
            //(The instructions said to add a delay. I don't know why.)
            coroutineScope.launch(Dispatchers.Main) {
                if(atBottom){
                    delay(1000)
                    maxCount.value += 30
                }
            }
            //for each number from 1 to the current max, make a card showing that
            //number in the grid
            items(maxCount.value) {num ->
                Card(
                    modifier = Modifier
                        .padding(6.dp)
                        .size(100.dp)
                ){
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = (num+1).toString(),
                            fontSize = 64.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IndividualAssignment_34Theme {
        MakeScreen()
    }
}