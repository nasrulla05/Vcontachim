package com.akhbulatov.vcontachim.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        Log.d("MyLogMAct","onCreate")
        Log.d("MyLogMAct","onCreate2")

        val button = findViewById<Button>(R.id.button)
        button.text = "Hi"
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyLogMAct","onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("MyLogMAct","onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("MyLogMAct","onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("MyLogMAct","onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MyLogMAct","onRestart")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLogMAct","onDestroy")
    }
}