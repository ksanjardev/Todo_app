package uz.sanjar.androidweekexam33.ui.screens.home

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import uz.sanjar.androidweekexam33.R
import uz.sanjar.androidweekexam33.domain.TodoUIData
import uz.sanjar.androidweekexam33.ui.screens.calendar.components.TopBarText
import uz.sanjar.androidweekexam33.ui.screens.home.components.CategoryItem
import uz.sanjar.androidweekexam33.ui.screens.home.components.ItemCategory
import uz.sanjar.androidweekexam33.ui.screens.home.components.ItemTask
import uz.sanjar.androidweekexam33.utils.task_type.TaskType

/**   Created by Sanjar Karimov 12:42 PM 1/19/2025   */

class HomeScreen : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
        val context = LocalContext.current

        val notificationPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Notification permission granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }


        HomeScreenContent(viewModel::eventDispatcher, viewModel.uiState.collectAsState())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    eventDispatcher: (HomeContract.Intent) -> Unit = {},
    uiState: State<HomeContract.UIState> = remember { mutableStateOf(HomeContract.UIState()) },
) {

    val categoryList = listOf(
        CategoryItem(
            categoryName = "Health",
            taskCount = uiState.value.healthCount,
            taskType = TaskType.HEALTH
        ),
        CategoryItem(
            categoryName = "Work",
            taskCount = uiState.value.workCount,
            taskType = TaskType.WORK
        ),
        CategoryItem(
            categoryName = "Mental Health",
            taskCount = uiState.value.mentalCount,
            taskType = TaskType.MENTAL_HEALTH
        ),
        CategoryItem(
            categoryName = "Others",
            taskCount = uiState.value.otherCount,
            taskType = TaskType.OTHERS
        ),
    )

    Log.d("LALALALA", "HomeScreenContent: recomposed")

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    TopBarText("Today", "21 Jan")
                },
                navigationIcon = {},
                modifier = Modifier.clickable { eventDispatcher(HomeContract.Intent.OnCalendarClick) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 32.dp)
                    .padding(horizontal = 16.dp)

            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categoryList) {
                        ItemCategory(it)
                    }
                }

                Spacer(Modifier.height(10.dp))

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(uiState.value.todoList) { _, data ->
                        SwipeableActionsBox(
                            endActions = listOf(
                                SwipeAction(
                                    onSwipe = {
                                        eventDispatcher.invoke(
                                            HomeContract.Intent.OnDeleteSwipe(
                                                data
                                            )
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            tint = Color.White,
                                            contentDescription = null
                                        )
                                    },
                                    background = Color.Red
                                )
                            )
                        ) {
                            ItemTask(
                                taskData = data,
                                onClick = {
                                    eventDispatcher.invoke(
                                        HomeContract.Intent.OnTaskCheckedChange(it)
                                    )
                                },
                                time = data.time,
                                onSubtaskClick = {
                                    eventDispatcher(
                                        HomeContract.Intent.OnSubtaskCheckedChange(it)
                                    )
                                },
                                onItemClick = {
                                    eventDispatcher(
                                        HomeContract.Intent.OnEditClick(it)
                                    )
                                })
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { eventDispatcher.invoke(HomeContract.Intent.OnAddBtnClick) },
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = Color(0xFF393433)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}