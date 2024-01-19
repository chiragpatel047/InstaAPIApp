package com.chirag047.InstaSave.screen

import android.app.DownloadManager
import android.content.Context
import android.icu.text.CaseMap.Title
import android.net.Uri
import android.os.Environment
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.advanced.base.R
import com.chirag047.InstaSave.components.FilledCustomButton
import com.chirag047.InstaSave.components.topBar
import com.chirag047.InstaSave.models.InstaModel
import com.chirag047.InstaSave.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(context: Context) {

    val homeViewModel: HomeViewModel = viewModel()
    val video: State<InstaModel> = homeViewModel.data.collectAsState()


    Column {
        topBar("Home")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            var search = remember {
                mutableStateOf("")
            }

            OutlinedTextField(value = search.value,
                onValueChange = {
                    search.value = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 0.dp, 15.dp, 0.dp),
                placeholder = {
                    Text(
                        text = "Paste link here...",
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp, 2.dp, 0.dp, 0.dp)
                    )
                })

            FilledCustomButton(imageIcon = R.drawable.searchicon) {
                homeViewModel.getVideo(search.value)
            }
        }

        belowContent(video, context)

    }
}

@Composable
fun belowContent(video: State<InstaModel>, context: Context) {


    val isVideoIsReady = remember {
        mutableStateOf(false)
    }

    if(!video.value.media.isEmpty()){
        isVideoIsReady.value =true
    }else{
        isVideoIsReady.value =false
    }

    AnimatedVisibility(visible = isVideoIsReady.value) {
        Column() {
            Image(
                painter = rememberImagePainter(video.value.thumbnail),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Black)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                content = {
                    Text(
                        text = "Download",
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                onClick = {
                    downloadVideo(
                        video.value.media,
                        video.value.title,
                        context
                    )
                }
            )
        }

    }

}

fun downloadVideo(url: String, title: String, context: Context) {
    val request =
        DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading Video")
            .setDescription(title)
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_MOBILE
                        or DownloadManager.Request.NETWORK_WIFI
            )
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + ".mp4")
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE
                        or DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}