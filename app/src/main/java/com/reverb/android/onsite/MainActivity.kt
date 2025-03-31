package com.reverb.android.onsite

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.reverb.android.onsite.data.repository.RecentListingsRepository
import com.reverb.android.onsite.ui.ListingGridItem
import com.reverb.android.onsite.ui.RecentListingsScreen
import com.reverb.android.onsite.ui.theme.ReverbAndroidOnsiteTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ReverbAndroidOnsiteTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {
            TopAppBar(
              title = { Text(text = "Recent Listings") },
            )
          },
        ) { innerPadding ->
          RecentListingsScreen(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}
