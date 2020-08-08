package com.example.tuner.androidConfiguration
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

/**
 * Implementacja interface AudioConfig
 */


class AndroidAudioConfig : AudioConfig {

    override fun getSampleRate(): Int {
        return AUDIO_SAMPLE_RATE
    }

    override fun getInputBufferSize(): Int {
        return AUDIO_RECORD_BUFFER_SIZE
    }
    
    override fun getOutputBufferSize(): Int {
        return AUDIO_PLAYER_BUFFER_SIZE
    }

    override fun getReadSize(): Int {
        return AUDIO_RECORD_READ_SIZE
    }

    override fun getWriteSize(): Int {
        return AUDIO_PLAYER_WRITE_SIZE
    }

    override fun getInputChannel(): Int {
        return AUDIO_RECORD_CHANNEL_CONFIG
    }

    override fun getOutputChannel(): Int {
        return AUDIO_PLAYER_CHANNEL_CONFIG
    }

    override fun getInputFormat(): Int {
        return AUDIO_RECORD_AUDIO_FORMAT
    }

    override fun getOutputFormat(): Int {
        return AUDIO_PLAYER_AUDIO_FORMAT
    }

    override fun getOutputFormatByteCount(): Int {
        return AUDIO_PLAYER_AUDIO_FORMAT_BYTE_COUNT
    }

    override fun getInputSource(): Int {
        return AUDIO_RECORD_AUDIO_SOURCE
    }

    companion object {
        private const val AUDIO_SAMPLE_RATE = 44100

            // Wartości wejściowe
        private const val AUDIO_RECORD_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_DEFAULT
        private val AUDIO_RECORD_AUDIO_FORMAT =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) AudioFormat.ENCODING_PCM_FLOAT else AudioFormat.ENCODING_PCM_16BIT
        private val AUDIO_RECORD_BUFFER_SIZE = AudioRecord.getMinBufferSize(
            AUDIO_SAMPLE_RATE,
            AUDIO_RECORD_CHANNEL_CONFIG,
            AUDIO_RECORD_AUDIO_FORMAT
        )
        private val AUDIO_RECORD_READ_SIZE =
            AUDIO_RECORD_BUFFER_SIZE / 4
        private const val AUDIO_RECORD_AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT

        // Wartości wyjściowe
        private const val AUDIO_PLAYER_CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_STEREO
        private const val AUDIO_PLAYER_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_FLOAT
        private const val AUDIO_PLAYER_AUDIO_FORMAT_BYTE_COUNT = 2

        private val AUDIO_PLAYER_BUFFER_SIZE = AudioTrack.getMinBufferSize(
            AUDIO_SAMPLE_RATE,
            AUDIO_PLAYER_CHANNEL_CONFIG,
            AUDIO_PLAYER_AUDIO_FORMAT
        )
        private val AUDIO_PLAYER_WRITE_SIZE =
            AUDIO_PLAYER_BUFFER_SIZE / 4
    }
}