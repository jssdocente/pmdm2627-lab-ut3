package com.pmdm.lab.ut3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabUt3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // consumeWindowInsets: los Scaffold de los codelabs saben que
                    // las barras del sistema ya están cubiertas y no repiten el hueco
                    LabApp(
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                    )
                }
            }
        }
    }
}
