package com.akhbulatov.vcontachim.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ActivityTestBinding
import com.google.android.material.snackbar.Snackbar

class TestActivity : AppCompatActivity(R.layout.activity_test) {
    lateinit var binding:ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MyLogMAct", "onCreate")
        Log.d("MyLogMAct", "onCreate2")

        binding.button.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                // Какой-то код //
            }
        })
        val b: Byte = 76//Byte: хранит целое число от -128 до 127 и занимает 1 байт
        val c: Short = 23 //Short: хранит целое число от -32,768  до 32,767 и занимает 2 байт
        val d: Int =
            4325 //Int: хранит целое число от -2,147,483,648  до 2,147,483,647 и занимает 4 байт
        val e: Long =
            4325526 //Long: хранит целое число от -9,223,372,036,854,775,808  до 9,223,372,036,854,775,807  и занимает 8 байт

        var float: Float =
            120.8f//Float: хранит  число с плавающей точкой от - 3.4*10 38 до 3.4*10 38 и занимает 4 байта
        var double: Double =
            120.8//Double: хранит  число с плавающей точкой от 5.0*10 -324 до 1.7*10 308 и занимает 8 байта
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyLogMAct", "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d("MyLogMAct", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("MyLogMAct", "onPause")

        val view = View(VcontachimApplication.context)
        val button = findViewById<Button>(R.id.button)
        button.text = "Hello"
        val text = "Че там ?"
        Snackbar.make(view,text, Snackbar.LENGTH_LONG)
            .setAction("Тема есть!"){
                // Какой-то код //
            }
            .show()

    }

    override fun onStop() {
        super.onStop()
        Log.d("MyLogMAct", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MyLogMAct", "onRestart")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyLogMAct", "onDestroy")
    }
}