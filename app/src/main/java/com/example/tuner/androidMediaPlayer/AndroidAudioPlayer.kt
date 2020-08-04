package com.example.tuner.androidMediaPlayer

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tuner.androidConfiguration.AudioConfig

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

/**
 * Implementacja interfejsu AndroidAudioRecorder dla Androida, która powinien współpracować z android.media.AudioTrack
 */

class AndroidAudioPlayer(audioConfig: AudioConfig) : AudioPlayer {

    private val audioTrack: AudioTrack
    private val outputByteCount: Int

    override fun play() {
        audioTrack.reloadStaticData()
        audioTrack.play()
    }

    override fun stop() {
        audioTrack.pause()
        audioTrack.flush()
        audioTrack.stop()
    }

    override fun release() {
        audioTrack.release()
    }

    override fun setBuffer(waveformBuffer: FloatArray?) {
        if (waveformBuffer != null) {
            audioTrack.write(waveformBuffer, 0, waveformBuffer.size, AudioTrack.WRITE_BLOCKING)
        }
        waveformBuffer?.size?.div(outputByteCount)?.let {
            audioTrack.setLoopPoints(0,
                it, LOOP_COUNT_INFINITE)
        }
    }

    companion object {
        private const val LOOP_COUNT_INFINITE = -1
    }

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val audioFormat = AudioFormat.Builder()
            .setChannelMask(audioConfig.getOutputChannel())
            .setEncoding(audioConfig.getOutputFormat())
            .setSampleRate(audioConfig.getSampleRate())
            .build()
        audioTrack = AudioTrack(audioAttributes,
            audioFormat,
            audioConfig.getOutputBufferSize(),
            AudioTrack.MODE_STATIC,
            AudioManager.AUDIO_SESSION_ID_GENERATE)
        outputByteCount = audioConfig.getOutputFormatByteCount()
    }
}