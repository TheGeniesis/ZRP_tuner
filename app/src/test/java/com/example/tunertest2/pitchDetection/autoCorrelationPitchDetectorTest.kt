package com.example.tuner.pitchDetection

import com.example.tuner.androidConfiguration.AudioConfig
import com.example.tuner.pitchDetection.autoCorrelationPitchDetector
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class autoCorrelationPitchDetectorTest {

    @Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()
    private var detector: autoCorrelationPitchDetector? = null

    @Before
    fun setupDetector() {
        val config: AudioConfig = Mockito.mock(AudioConfig::class.java)
        Mockito.`when`(config.getSampleRate())
            .thenReturn(SAMPLE_RATE)
        Mockito.`when`(config.getReadSize())
            .thenReturn(READ_SIZE)
        detector = autoCorrelationPitchDetector(config)
    }

    companion object {
        private const val SAMPLE_RATE = 44100
        private const val READ_SIZE = 10000
    }
}