package com.example.tuner.frequencyToWaveConversion

import com.example.tuner.androidConfiguration.AudioConfig

/**
* Implementacja interfejsu FrequencyConverter, który przekształca podaną wartość częstotliwość do "prawie" idealnego buforu sinusoidalnego kształtu fali.
*/

class WaveConverter(audioConfig: AudioConfig) : FrequencyConverter {

    private val sampleRate: Int

    private val initialWriteBufferSize: Int

    private val outputFormatByteCount: Int

    override fun convert(frequency: Double): FloatArray {

        val cyclesPerSampleRate = sampleRate / frequency

        val cyclesPerBuffer = (initialWriteBufferSize / cyclesPerSampleRate).toInt()

        val roundedCyclesPerBuffer = cyclesPerBuffer / outputFormatByteCount * outputFormatByteCount

        val outputBufferSize = (roundedCyclesPerBuffer * cyclesPerSampleRate).toInt()

        val entireBufferDeviation = roundedCyclesPerBuffer * cyclesPerSampleRate % cyclesPerSampleRate.toInt()

        val deviationDiff = entireBufferDeviation / outputBufferSize

        val audioData = FloatArray(outputBufferSize)

        for (i in audioData.indices)
        {
            audioData[i] = (Math.sin(TWO_PI * i / cyclesPerSampleRate) - deviationDiff).toFloat()
        }
        return audioData
    }

    companion object {
        private const val TWO_PI = 2 * Math.PI
    }

    init {
        sampleRate = audioConfig.getSampleRate()
        initialWriteBufferSize = audioConfig.getWriteSize()
        outputFormatByteCount = audioConfig.getOutputFormatByteCount()
    }
}