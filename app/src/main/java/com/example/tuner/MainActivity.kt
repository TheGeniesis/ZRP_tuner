package com.example.tuner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_title_bar.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for first setup we need to create some basic data
        DatabaseFixture().createBasicDataInDB(this)

        val list = getList(this)
        setTuneTexts(list)


        title_bar.text = "SmoothTuner";
        button.setOnClickListener {
            val intent = Intent(this, TuneList::class.java)
            startActivity(intent)
        }
    }

    private fun getList(context: Context): TunerListView?
    {
        val tuneResult = DbTuneHandler(context).getAll()
        val tuning = DbBasicTunerHandler(this).getCurrentTuning()

        if (tuning === null) {
            return null
        }

        return TunerListView(
            tuning.name,
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

    }

    private fun setTuneTexts(list: TunerListView?)
    {

        val tune1 = findViewById<TextView>(R.id.tune_block_tune_1)
        if (list != null) {
            tune1.text  = list.tune1
        }

        val tune2 = findViewById<TextView>(R.id.tune_block_tune_2)
        if (list != null) {
            tune2.text  = list.tune2
        }

        val tune3 = findViewById<TextView>(R.id.tune_block_tune_3)
        if (list != null) {
            tune3.text  = list.tune3
        }

        val tune4 = findViewById<TextView>(R.id.tune_block_tune_4)
        if (list != null) {
            tune4.text  = list.tune4
        }

        val tune5 = findViewById<TextView>(R.id.tune_block_tune_5)
        if (list != null) {
            tune5.text  = list.tune5
        }

        val tune6 = findViewById<TextView>(R.id.tune_block_tune_6)
        if (list != null) {
            tune6.text  = list.tune6
        }

        val tune7 = findViewById<TextView>(R.id.tune_block_tune_7)
        if (list != null) {
            tune7.text  = list.tune7
        }

        val tune8 = findViewById<TextView>(R.id.tune_block_tune_8)
        if (list != null) {
            tune8.text  = list.tune8
        }

        val tune9 = findViewById<TextView>(R.id.tune_block_tune_9)
        if (list != null) {
            tune9.text  = list.tune9
        }

        val tune10 = findViewById<TextView>(R.id.tune_block_tune_10)
        if (list != null) {
            tune10.text  = list.tune10
        }

    }
}
