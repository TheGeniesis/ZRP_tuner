package com.example.tuner

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import kotlin.math.abs
import android.graphics.Matrix

class MainActivity : AppCompatActivity() {


    private var list : TunerListView? = null
    private var mAudioProcessor: AudioProcessor? = null
    private val mExecutor =
        Executors.newSingleThreadExecutor()
    private val MY_PERMISSIONS_RECORD_AUDIO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for first setup we need to create some basic data
        DatabaseFixture().createBasicDataInDB(this)

        list = getList(this)
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
        val context = this
        mAudioProcessor = AudioProcessor()
        // set context to be able to change frequency value in TextView
        mAudioProcessor!!.setContext(this)
        mAudioProcessor!!.init()
        mAudioProcessor!!.setPitchDetectionListener(object : AudioProcessor.PitchDetectionListener {
            override fun onPitchDetected(freq: Float, avgIntensity: Double) {
                val tuneResult = DbTuneHandler(context).getAll()
                val tuning = DbBasicTunerHandler(context).getCurrentTuning() ?: return
                var curr: TuneModel? = null
                var next: TuneModel? = null

                if (tuning.tune1 !== null) {
                    curr = tuneResult.find { it.id == tuning.tune1 }
                }

                if (tryToSetTune(curr, next, freq))  {
                    return
                }

                if (tuning.tune2 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune2 }
                    } else {
                        next = tuneResult.find { it.id == tuning.tune2 }
                    }
                }

                if (tryToSetTune(curr, next, freq))  {
                    return
                }

                if (tuning.tune3 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune3 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune3 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune3 }
                    }
                }

                if (tryToSetTune(curr, next, freq))  {
                    return
                }

                if (tuning.tune4 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune4 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune4 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune4 }
                    }
                }

                if (tryToSetTune(curr, next, freq)) {
                    return
                }

                if (tuning.tune5 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune5 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune5 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune5 }
                    }
                }

                if (tryToSetTune(curr, next, freq)) {
                    return
                }

                if (tuning.tune6 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune6 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune6 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune6 }
                    }
                }

                if (tryToSetTune(curr, next, freq)) {
                    return
                }

                if (tuning.tune7 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune7 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune7 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune7 }
                    }
                }

                if (tryToSetTune(curr, next, freq)) {
                    return
                }

                if (tuning.tune8 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune8 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune8 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune8 }
                    }
                }

                if (tryToSetTune(curr, next, freq)) {
                    return
                }

                if (tuning.tune9 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune9 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune9 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune9 }
                    }
                }

                if (tryToSetTune(curr, next, freq)) {
                    return
                }

                if (tuning.tune10 !== null) {
                    if (curr == null) {
                        curr = tuneResult.find { it.id == tuning.tune10 }
                    } else if (next == null) {
                        next = tuneResult.find { it.id == tuning.tune10 }
                    } else {
                        next = null
                        curr = tuneResult.find { it.id == tuning.tune10 }
                    }
                }

                if (tryToSetTune(curr, next, freq)) {
                    return
                }

                if (next != null) {
                    selectTune(next)
                    return
                }
                selectTune(curr!!)
            }
        })
        // run frequency listener
        mExecutor.execute(mAudioProcessor)
    }

    private fun tryToSetTune(curr: TuneModel?, next: TuneModel?, freq: Float): Boolean
    {
        if (curr!!.exactFrequency > freq && next == null) {
            return selectTune(curr)
        }

        if (curr != null && next != null && curr.exactFrequency < freq && next.exactFrequency > freq) {
            val diff = abs(next.exactFrequency - curr.exactFrequency) / 2;
            if ((next.exactFrequency - freq) > diff) {
                return selectTune(curr)
            } else {
                return selectTune(next)
            }
        }

        return false
    }

    private fun resetTuneColor () {
        val notSelectedColor = "black"
        findViewById<TextView>(R.id.tune_block_tune_1).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_2).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_3).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_4).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_5).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_6).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_7).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_8).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_9).setTextColor(Color.parseColor(notSelectedColor))
        findViewById<TextView>(R.id.tune_block_tune_10).setTextColor(Color.parseColor(notSelectedColor))
    }

    private fun selectTune(curr: TuneModel): Boolean {
        val selectedColor = "#d8d8d8"
        val elem = getFrequencyTune(curr)
        if (elem != null) {
            resetTuneColor()
            findViewById<TextView>(elem).setTextColor(Color.parseColor(selectedColor))

            return true
        }
        return false
    }

    private fun getFrequencyTune(curr: TuneModel): Int?
    {
        if (curr.name == list!!.tune1 ) { return R.id.tune_block_tune_1}
        if (curr.name == list!!.tune2 ) { return R.id.tune_block_tune_2}
        if (curr.name == list!!.tune3 ) { return R.id.tune_block_tune_3}
        if (curr.name == list!!.tune4 ) { return R.id.tune_block_tune_4}
        if (curr.name == list!!.tune5 ) { return R.id.tune_block_tune_5}
        if (curr.name == list!!.tune6 ) { return R.id.tune_block_tune_6}
        if (curr.name == list!!.tune7 ) { return R.id.tune_block_tune_7}
        if (curr.name == list!!.tune8 ) { return R.id.tune_block_tune_8}
        if (curr.name == list!!.tune9 ) { return R.id.tune_block_tune_9}
        if (curr.name == list!!.tune10 ) { return R.id.tune_block_tune_10}

        return null
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
        val tuning = DbBasicTunerHandler(context).getCurrentTuning()

        if (tuning === null) {
            return null
        }

        return TunerListView(
            tuning.id,
            tuning.name,
            tuning.customTuning,
            tuning.instrumentId,
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
