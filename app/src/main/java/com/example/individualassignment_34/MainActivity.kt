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
    val state = rememberLazyGridState()
    val atBottom = !state.canScrollForward

    val maxCount = remember {mutableStateOf(30)}

    val coroutineScope = rememberCoroutineScope()

//    LaunchedEffect(key1 = state.firstVisibleItemIndex) {
//        if(atBottom) {
//            delay(5000)
//            maxCount.value += 30
//        }
//    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
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
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Fixed(3),
            userScrollEnabled = true,
            contentPadding = PaddingValues(24.dp)
        ) {
            coroutineScope.launch(Dispatchers.Main) {
                if(atBottom){
                    delay(1000)
                    maxCount.value += 30
                }
            }
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