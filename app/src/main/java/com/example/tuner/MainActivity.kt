package com.example.tuner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_title_bar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title_bar.text = "SmoothTuner";


        button.setOnClickListener{
            val intent = Intent(this, TuneList::class.java)
            startActivity(intent)

        }

    }
}
