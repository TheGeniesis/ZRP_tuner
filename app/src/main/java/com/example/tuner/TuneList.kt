package com.example.tuner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_title_bar.*

class TuneList : AppCompatActivity() {

    lateinit var option: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tune_list)

        title_bar.text = "Tunes";

        var option = findViewById<Spinner>(R.id.select_tune)

        val instruments = DbInstrumentHandler(this).getAll()
        option.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            instruments
        )
        val context = this
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                option.setSelection(instruments.first().id)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedId: Int? = null
                for (instrument in instruments) {
                    if (option.selectedItem.toString() == instrument.name) {
                        selectedId = instrument.id
                    }
                }

                if (selectedId !== null) {
                    DbBasicTunerHandler(context).getByInstrumentId(selectedId)
                }
            }
        }
    }
}
