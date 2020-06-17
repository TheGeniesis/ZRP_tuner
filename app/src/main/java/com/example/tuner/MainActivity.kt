package com.example.tuner

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_title_bar.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private var mAudioProcessor: AudioProcessor? = null
    private val mExecutor =
        Executors.newSingleThreadExecutor()
    private val MY_PERMISSIONS_RECORD_AUDIO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for first setup we need to create some basic data
        DatabaseFixture().createBasicDataInDB(this)

        val list = getList(this)
        setTuneTexts(list)

        requestAudioPermissions()


        button.setOnClickListener {
            val intent = Intent(this, TuneList::class.java)
            startActivity(intent)
        }
    }

    fun setFrequencyValue(value: String) {
       findViewById<TextView>(R.id.freq_label).text = value
    }

    fun startFrequencyChecker() {
        // init audio processor
        mAudioProcessor = AudioProcessor()
        // set context to be able to change frequency value in TextView
        mAudioProcessor!!.setContext(this)
        mAudioProcessor!!.init()
        mAudioProcessor!!.setPitchDetectionListener(object : AudioProcessor.PitchDetectionListener {
            override fun onPitchDetected(freq: Float, avgIntensity: Double) {
                // Listener - call when sound is louder than moment ago
                // TODO compare frequency with music note
            }
        })
        // run frequency listener
        mExecutor.execute(mAudioProcessor)
    }

    private fun requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )
            ) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG)
                    .show()

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
            }
        } else if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            //Go ahead with recording audio now
            startFrequencyChecker()
        }
    }

    //Handling request permission callback
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_RECORD_AUDIO -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted, yay!
                    startFrequencyChecker()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG)
                        .show()
                }
                return
            }
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
