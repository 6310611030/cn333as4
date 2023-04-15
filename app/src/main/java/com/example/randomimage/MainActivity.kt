package com.example.randomimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomimage.ui.theme.RandomImageTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import coil.request.ImageRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomImageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RandomImage()
                    
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RandomImage() {
    var width by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val imageCategory = listOf("movie", "game", "album", "book", "face",
        "fashion", "shoes", "watch", "furniture")
    var selectedCategory by remember { mutableStateOf(imageCategory[0])}
    var url by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Random Image",
            fontSize = 36.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(128.dp))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {Text(text = "Width",
                fontSize = 24.sp,)},
            value = width,
            onValueChange = { width = it },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {Text("Height",
                fontSize = 24.sp,)},
            value = height,
            onValueChange = { height = it },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedCategory,
                onValueChange = { },
                label = { Text("Image Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                imageCategory.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    ) {
                        Text(text = category)
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = { url = "https://loremflickr.com/$width/$height/$selectedCategory" },
            modifier = Modifier.fillMaxWidth(),) {
            Text(text = "Display Image")
        }
        if (url.isNotEmpty()) {
            DisplayImage(src = url)
        }

    }
}

@Composable
fun DisplayImage(src: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(src)
            .crossfade(true)
            .build(),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(225.dp)
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RandomImageTheme {
        RandomImage()
    }
}