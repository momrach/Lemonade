package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                    ScreenContent(modifier = Modifier)
               }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    // step:Int para contar el paso en en que estamos 0,1,2,3
    // En cada paso una imagen
    var step by remember {
        mutableStateOf(0)
    }

    // taps:Int para contar cuantas pulsaciones hemos hecho en el paso 1 (exprimir limon)
    var taps by remember {
        mutableStateOf(value=0)
    }

    //Numero máximo de taps en el paso 1. Exprimir (simula las vueltas en el exprimidor)
    //En el paso 0 (coger limon) se aplica un valor diferente
    var maxTaps by remember { mutableStateOf(1)}

    //En función del Step obtenemos un recurso de imagen a utilizar
    val imageResource = when(step){
        0 -> R.drawable.lemon_tree
        1 -> R.drawable.lemon_squeeze
        2 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    //En funcion del setp obtenemos un recurso de string a utilizar
    val stringResource = when(step){
        0 -> R.string.lemon_tree
        1 -> R.string.lemon
        2 -> R.string.glass_of_lemonade
        else -> R.string.empty_glass
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ){ innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    //utilizamos el recurso de string en función del step
                    stringResource(id = stringResource),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    //utilizamos el recurso de imagen obtenido en funcion del paso
                    painterResource(id = imageResource),
                    contentDescription = stringResource(id = R.string.lemon_tree),
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color(red = 105 / 255f, green = 205 / 255f, blue = 216 / 255f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        // En el paso 0 obtenemos el maxTaps
                        // En el paso 1 incrementamos el taps, si llegamos al maxTaps incrementamos el step
                        // El step se incrementa y se le aplica modulo 4
                        // En otros pasos (2 y 3) se incrementa el step
                        .clickable {
                            if (step == 0) {
                                maxTaps = (2..4).random()
                            }
                            if (step == 1) {
                                taps += 1
                                if (taps >= maxTaps) {
                                    step = (step + 1) % 4
                                    taps = 0
                                }
                            } else {
                                step = (step + 1) % 4
                            }
                        }
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ScreenContent(modifier = Modifier)
        }
    }
}