package com.example.tuner.androidMediaRecorder

import android.media.AudioRecord
import android.os.Build
import com.example.tuner.androidConfiguration.AudioConfig
import com.example.tuner.frequencyToWaveConversion.ArrayConverter

/**
* Implementacja interfejsu AudioRecorder współpracującego z android.media.AudioRecord.
*/

class AndroidAudioRecorder(audioConfig: AudioConfig, private val converter: ArrayConverter) :
    AudioRecorder {
    private val audioRecorder: AudioRecord
    private val readSize: Int
    private val buffer: ShortArray
    private val floatBuffer: FloatArray
    override fun startRecording() {
        audioRecorder.startRecording()
    }

    override fun stopRecording() {
        audioRecorder.stop()
    }

    override fun readNext(): FloatArray {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioRecorder.read(floatBuffer, 0, readSize, AudioRecord.READ_BLOCKING)
        } else {
            audioRecorder.read(buffer, 0, readSize)
            converter.convert(buffer, floatBuffer)
        }
        return floatBuffer
    }

    init {
        audioRecorder = AudioRecord(
            audioConfig.getInputSource(), audioConfig.getSampleRate(),
            audioConfig.getInputChannel(), audioConfig.getInputFormat(), audioConfig.getInputBufferSize()
        )
        readSize = audioConfig.getReadSize()
        buffer = ShortArray(readSize)
        floatBuffer = FloatArray(readSize)
    }
}