package com.example.tuner

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddEditActivity : AppCompatActivity() {

    private var selectedInstrument: InstrumentModel? = null
    private var selectedTuneListElem = ArrayList<TuneModel>(10)
    private var tuneList = ArrayList<TuneModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        var id: Int? = null


        val context = this
        tuneList = DbTuneHandler(context).getAll()

        val selectedTuningOption = findViewById<ListView>(R.id.selected_tunings_list)

        selectedTuningOption.adapter = TuningAddTuneListAdapter(
            context,
            selectedTuneListElem
        )

        handleBackButton()
        handleAddTuneButton(selectedTuningOption)
        handleInstrumentSpinner()

        val tuningOption = findViewById<Spinner>(R.id.add_note_spinner)

        tuningOption.adapter = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            tuneList
        )

        findViewById<Button>(R.id.save_changes).setOnClickListener {
            if (id == null) {
                if (selectedTuneListElem.size == 0) {
                    //exception
                }
                var tune1: Int? = null
                if (selectedTuneListElem.size >= 1) {
                    tune1= selectedTuneListElem[0].id
                }
                var tune2: Int? = null
                if (selectedTuneListElem.size >= 2) {
                    tune2= selectedTuneListElem[1].id
                }
                var tune3: Int? = null
                if (selectedTuneListElem.size >= 3) {
                    tune3= selectedTuneListElem[2].id
                }
                var tune4: Int? = null
                if (selectedTuneListElem.size >= 4) {
                    tune4= selectedTuneListElem[3].id
                }
                var tune5: Int? = null
                if (selectedTuneListElem.size >= 5) {
                    tune5= selectedTuneListElem[4].id
                }
                var tune6: Int? = null
                if (selectedTuneListElem.size >= 6) {
                    tune6= selectedTuneListElem[5].id
                }
                var tune7: Int? = null
                if (selectedTuneListElem.size >= 7) {
                    tune7= selectedTuneListElem[6].id
                }
                var tune8: Int? = null
                if (selectedTuneListElem.size >= 8) {
                    tune8= selectedTuneListElem[7].id
                }
                var tune9: Int? = null
                if (selectedTuneListElem.size >= 9) {
                    tune9= selectedTuneListElem[8].id
                }
                var tune10: Int? = null
                if (selectedTuneListElem.size >= 10) {
                    tune10= selectedTuneListElem[9].id
                }

                if (selectedInstrument !== null) {
                    id = DbBasicTunerHandler(context).insertData(
                        BasicTunerModel(
                            null,
                            tuning_name.text.toString(),
                            selectedInstrument!!.id,
                            true,
                            0,
                            tune1,
                            tune2,
                            tune3,
                            tune4,
                            tune5,
                            tune6,
                            tune7,
                            tune8,
                            tune9,
                            tune10
                        )
                    ).toInt()
                } else {
                    //validation message
                }
                //insert
            } else {
                //update
            }

            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleInstrumentSpinner() {
        val instrumentOption = findViewById<Spinner>(R.id.instrument_id)

        val instruments = DbInstrumentHandler(this).getAll()
        instrumentOption.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            instruments
        )
        instrumentOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                instrumentOption.setSelection(instruments.first().id)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedInstrument = instruments[position]
            }
        }
    }


    private fun handleBackButton() {
        findViewById<Button>(R.id.go_back).setOnClickListener {
            val intent = Intent(this, TuneList::class.java)
            startActivity(intent)
        }
    }

    private fun handleAddTuneButton(selectedTuningOption: ListView) {
        findViewById<Button>(R.id.add_note_action).setOnClickListener {
            if (selectedTuneListElem.size >= 10) {
                //throw exception, out of limit
            } else {
                selectedTuneListElem.add(tuneList[add_note_spinner.selectedItemPosition])
                (selectedTuningOption.adapter as TuningAddTuneListAdapter).notifyDataSetChanged()
            }
        }
    }
}
