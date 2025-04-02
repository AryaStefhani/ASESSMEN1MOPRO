package com.aryastefhani0140.miniproject1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aryastefhani0140.miniproject1.ui.screen.MainScreen
import com.aryastefhani0140.miniproject1.ui.theme.Miniproject1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Miniproject1Theme {
                MainScreen()
            }
        }
    }
}
