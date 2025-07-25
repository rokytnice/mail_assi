package com.example.mailassi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mailassi.R
import com.example.mailassi.EmailAnalyzer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EmailAnalyzer(this).process()
        setContentView(R.layout.activity_main)
    }
}
