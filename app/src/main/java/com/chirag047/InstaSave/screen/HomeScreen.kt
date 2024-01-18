package com.chirag047.InstaSave.screen

import android.app.DownloadManager
import android.content.Context
import android.icu.text.CaseMap.Title
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
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

    Column() {
        topBar()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            var search = remember {
                mutableStateOf("")
            }

            TextField(value = search.value, onValueChange = {
                search.value = it
            }, modifier = Modifier.weight(1f))

            FilledCustomButton(imageIcon = R.drawable.searchicon) {
                homeViewModel.getVideo(search.value)
            }
        }

        Image(
            painter = rememberImagePainter(video.value.thumbnail),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.primary),
            content = { Text(text = "Download", color = MaterialTheme.colorScheme.onPrimary) },
            onClick = { downloadVideo(video.value.media, video.value.title, context) }
        )
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