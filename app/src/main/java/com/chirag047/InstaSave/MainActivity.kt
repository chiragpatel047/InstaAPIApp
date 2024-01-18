package com.chirag047.InstaSave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.advanced.base.R
import com.chirag047.InstaSave.models.NavItemModel
import com.chirag047.InstaSave.screen.HistoryScreen
import com.chirag047.InstaSave.screen.HomeScreen
import com.chirag047.InstaSave.ui.theme.BaseTheme
import dagger.hilt.android.AndroidEntryPoint

public var navScreen = mutableStateOf("Home")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.weight(1f)) {
                            when (navScreen.value) {
                                "Home" -> {
                                    HomeScreen(LocalContext.current.applicationContext)
                                }

                                "History" -> {
                                    HistoryScreen()
                                }
                            }
                        }
                        BottomNavBar()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomNavBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(20.dp),
        Arrangement.SpaceAround
    ) {

        val navItems = listOf<NavItemModel>(
            NavItemModel(
                "Home",
                R.drawable.home_outlined,
                R.drawable.home_filled
            ), NavItemModel(
                "History",
                R.drawable.history_outlined,
                R.drawable.history_filled
            )
        )

        navItems.forEach {

            Column(modifier = Modifier.clickable {
                navScreen.value = it.title

            }, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(
                        id = if (navScreen.value.equals(it.title))
                            it.filledIcon
                        else
                            it.icon
                    ),
                    tint = if (navScreen.value.equals(it.title))
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(4.dp)
                )

                Text(
                    text = it.title,
                    color = if (navScreen.value.equals(it.title))
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSecondaryContainer,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 12.sp
                )
            }
        }
    }


}