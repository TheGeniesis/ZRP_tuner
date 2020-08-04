package com.example.tuner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TuneList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tune_list)

       // title_bar.text = "Tunes";
        prepareAndHandleList(this)
        prepareAndHandleAddNewTuning()
    }

    private fun prepareAndHandleAddNewTuning()
    {
        val button = findViewById<Button>(R.id.add_new_tuning)
        button.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun prepareAndHandleList(context: Context)
    {
        val option = findViewById<Spinner>(R.id.select_tune)

        val instruments = DbInstrumentHandler(this).getAll()
        option.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            instruments
        )
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
                showListForInstrument(context, instruments, option)
            }
        }
    }

    private fun showListForInstrument(context: Context, instruments:  ArrayList<InstrumentModel>, option: Spinner ) {
        var selectedId: Int? = null
        for (instrument in instruments) {
            if (option.selectedItem.toString() == instrument.name) {
                selectedId = instrument.id
            }
        }
        val tuneResult = DbTuneHandler(context).getAll()
        if (selectedId !== null) {
            val tuningList = DbBasicTunerHandler(context).getByInstrumentId(selectedId)
            val tuningOptionList: ArrayList<TunerListView> = ArrayList(tuningList.size)
            for (tuning in tuningList) {
                tuningOptionList.add(
                    TunerListView(
                        tuning.id,
                        tuning.name,
                        tuning.customTuning,
                        if (tuning.tune1 !== null) tuneResult.find { it.id == tuning.tune1 }!!.name else null,
                        if (tuning.tune2 !== null) tuneResult.find { it.id == tuning.tune2 }!!.name else null,
                        if (tuning.tune3 !== null) tuneResult.find { it.id == tuning.tune3 }!!.name else null,
                        if (tuning.tune4 !== null) tuneResult.find { it.id == tuning.tune4 }!!.name else null,
                        if (tuning.tune5 !== null) tuneResult.find { it.id == tuning.tune5 }!!.name else null,
                        if (tuning.tune6 !== null) tuneResult.find { it.id == tuning.tune6 }!!.name else null,
                        if (tuning.tune7 !== null) tuneResult.find { it.id == tuning.tune7 }!!.name else null,
                        if (tuning.tune8 !== null) tuneResult.find { it.id == tuning.tune8 }!!.name else null,
                        if (tuning.tune9 !== null) tuneResult.find { it.id == tuning.tune9 }!!.name else null,
                        if (tuning.tune10 !== null) tuneResult.find { it.id == tuning.tune10 }!!.name else null
                    )
                )
            }

            val tuningOption = findViewById<ListView>(R.id.tune_list)

            tuningOption.adapter = TuneListElementAdapter(
                context,
                tuningOptionList
            )
        }
    }
}
