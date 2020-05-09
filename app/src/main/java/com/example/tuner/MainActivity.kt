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
        // for first setup we need to create some basic data
        createBasicDataInDB()

        title_bar.text = "SmoothTuner";


        button.setOnClickListener{
            val intent = Intent(this, TuneList::class.java)
            startActivity(intent)

        }
    }

    private fun createBasicDataInDB()
    {
        val context = this
        if (DbInstrumentHandler(this).getAll().isEmpty()) {
            var instrument = InstrumentModel(1, InstrumentEnum.GUITAR);
            val handler = DbInstrumentHandler(context)
            handler.insertData(instrument)

            instrument = InstrumentModel(2, InstrumentEnum.BASS);
            handler.insertData(instrument)
        }
    }
}
