package com.example.tuner

import com.example.tuner.frequencyToWaveConversion.FrequencyConverter
import com.example.tuner.androidMediaPlayer.AudioPlayer
import io.reactivex.Completable

class GuitarPitchPlayer(
    audioPlayer: AudioPlayer,
    frequencyConverter: FrequencyConverter
) :
    PitchPlayer {
    private val audioPlayer: AudioPlayer = audioPlayer
    private val frequencyConverter: FrequencyConverter = frequencyConverter
    override fun startPlaying(frequency: Double): Completable {
        return Completable.create { emitter ->
            try {
                val audioData: FloatArray? = frequencyConverter.convert(frequency)
                audioPlayer.setBuffer(audioData)
                audioPlayer.play()
            } catch (exception: Exception) {
                emitter.tryOnError(exception)
            }
        }.doOnEvent { audioPlayer.stop() }
            .doOnDispose { stopPlayingAndRelease() }
    }

    private fun stopPlayingAndRelease() {
        audioPlayer.stop()
        audioPlayer.release()
    }

}