package com.example.tuner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlin.system.exitProcess

class AddEditActivity : AppCompatActivity() {

    private var selectedInstrument: InstrumentModel? = null
    private var selectedTuneListElem = ArrayList<TuneModel>(10)
    private var tuneList = ArrayList<TuneModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        var order = 0
        val context = this
        tuneList = DbTuneHandler(context).getAll()

        var selectedTuningOption = findViewById<ListView>(R.id.selected_tunings_list)

        selectedTuningOption.adapter = TuningAddTuneListAdapter(
            context,
            selectedTuneListElem
        )

        val instrumentList = DbInstrumentHandler(this).getAll()

        handleBackButton()
        handleAddTuneButton(context, selectedTuningOption)
        handleTuneSpinner(context)
        val spinnerInstrument = handleInstrumentSpinner(instrumentList)
        var id: Int? = intent.getSerializableExtra("tuning_id") as Int?
        if (id !== null) {
            val elemToModify = DbBasicTunerHandler(this).getById(id)
            if (elemToModify !== null) {
                if (elemToModify.tune1 !== null) {
                    selectedTuneListElem.add(tuneList.find { it.id == elemToModify.tune1 }!!)
                }
                if (elemToModify.tune2 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune2 }!!)
                }
                if (elemToModify.tune3 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune3 }!!)
                }
                if (elemToModify.tune4 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune4 }!!)
                }
                if (elemToModify.tune5 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune5 }!!)
                }
                if (elemToModify.tune6 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune6 }!!)
                }
                if (elemToModify.tune7 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune7 }!!)
                }
                if (elemToModify.tune8 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune8 }!!)
                }
                if (elemToModify.tune9 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune9 }!!)
                }
                if (elemToModify.tune10 !== null) {
                    selectedTuneListElem.add(
                        tuneList.find { it.id == elemToModify.tune10 }!!)
                }
                order = elemToModify.order
                id = elemToModify.id
                tuning_name.setText(elemToModify.name)
                selectedInstrument = instrumentList.find { it.id == elemToModify.instrumentId }
                spinnerInstrument?.setSelection(instrumentList.indexOf(selectedInstrument))
            }
        }

        findViewById<Button>(R.id.save_changes).setOnClickListener {
            var hasError = false
            if (tuning_name.text.toString().length == 0) {
                Toasty.error(context, "Tuning name can't be empty", Toast.LENGTH_SHORT).show()
                hasError = true
            }

            if (selectedTuneListElem.size == 0) {
                hasError = true
                Toasty.error(context, "There need to be chosen minimum 1 tune", Toast.LENGTH_SHORT).show()
            }
            var tune1: Int? = null
            if (selectedTuneListElem.size >= 1) {
                tune1 = selectedTuneListElem[0].id
            }
            var tune2: Int? = null
            if (selectedTuneListElem.size >= 2) {
                tune2 = selectedTuneListElem[1].id
            }
            var tune3: Int? = null
            if (selectedTuneListElem.size >= 3) {
                tune3 = selectedTuneListElem[2].id
            }
            var tune4: Int? = null
            if (selectedTuneListElem.size >= 4) {
                tune4 = selectedTuneListElem[3].id
            }
            var tune5: Int? = null
            if (selectedTuneListElem.size >= 5) {
                tune5 = selectedTuneListElem[4].id
            }
            var tune6: Int? = null
            if (selectedTuneListElem.size >= 6) {
                tune6 = selectedTuneListElem[5].id
            }
            var tune7: Int? = null
            if (selectedTuneListElem.size >= 7) {
                tune7 = selectedTuneListElem[6].id
            }
            var tune8: Int? = null
            if (selectedTuneListElem.size >= 8) {
                tune8 = selectedTuneListElem[7].id
            }
            var tune9: Int? = null
            if (selectedTuneListElem.size >= 9) {
                tune9 = selectedTuneListElem[8].id
            }
            var tune10: Int? = null
            if (selectedTuneListElem.size >= 10) {
                tune10 = selectedTuneListElem[9].id
            }
            if (id == null) {
                if (selectedInstrument !== null && !hasError) {
                    id = DbBasicTunerHandler(context).insertData(
                        BasicTunerModel(
                            null,
                            tuning_name.text.toString(),
                            selectedInstrument!!.id,
                            true,
                            order,
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
                    hasError = true
                    Toasty.error(context, "You have to chose instrument", Toast.LENGTH_SHORT).show()
                }
            } else if (!hasError){
                DbBasicTunerHandler(context).updateData(
                    BasicTunerModel(
                        id,
                        tuning_name.text.toString(),
                        selectedInstrument!!.id,
                        true,
                        order,
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
                )
            }
        }
    }

    private fun handleTuneSpinner(context: Context) {
        val tuningOption = findViewById<Spinner>(R.id.add_note_spinner)

        tuningOption.adapter = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            tuneList
        )
    }

    private fun handleInstrumentSpinner(instruments: ArrayList<InstrumentModel>): Spinner? {
        val instrumentOption = findViewById<Spinner>(R.id.instrument_id)

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

        return instrumentOption
    }


    private fun handleBackButton() {
        findViewById<Button>(R.id.go_back).setOnClickListener {
            val intent = Intent(this, TuneList::class.java)
            startActivity(intent)
        }
    }

    private fun handleAddTuneButton(context: Context, selectedTuningOption: ListView) {

        findViewById<Button>(R.id.add_note_action).setOnClickListener {
            if (selectedTuneListElem.size >= 10) {
                Toasty.error(context, "You can't choose more than 10 elements", Toast.LENGTH_SHORT).show()
            } else {
                selectedTuneListElem.add(tuneList[add_note_spinner.selectedItemPosition])
                (selectedTuningOption.adapter as TuningAddTuneListAdapter).notifyDataSetChanged()
            }
        }
    }
}
