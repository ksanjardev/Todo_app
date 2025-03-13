package uz.sanjar.androidweekexam33

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import uz.sanjar.androidweekexam33.utils.navigator.AppNavigatorHandler
import uz.sanjar.androidweekexam33.ui.screens.calendar.CalendarScreen
import uz.sanjar.androidweekexam33.ui.screens.home.HomeScreen
import uz.sanjar.androidweekexam33.ui.theme.AndroidWeekExam33Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigatorHandler: AppNavigatorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidWeekExam33Theme {
                Navigator(HomeScreen()){ navigator ->
                    LaunchedEffect(navigator) {
                        navigatorHandler.navigation.collect{
                            it(navigator)
                        }
                    }
                    CurrentScreen()
                }
            }
        }
    }
}
