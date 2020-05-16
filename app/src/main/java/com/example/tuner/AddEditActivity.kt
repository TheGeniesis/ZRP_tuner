package com.example.tuner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        var id : Int?= null

        handleBackButton()

        val context = this
        findViewById<Button>(R.id.save_changes).setOnClickListener {
            if (id == null) {
                id = DbBasicTunerHandler(context).insertData(BasicTunerModel(
                    null,
                    tuning_name.toString(),
                    1,
                    true,
                    0,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1
                )).toInt()

              //insert
            }  else {
                //update
            }

            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }
    }



    private fun handleBackButton(){
        findViewById<Button>(R.id.go_back).setOnClickListener {
            val intent = Intent(this, TuneList::class.java)
            startActivity(intent)
        }
    }
}
