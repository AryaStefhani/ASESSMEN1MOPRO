package com.aryastefhani0140.miniproject1

sealed class Screen (val route:String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")

}
