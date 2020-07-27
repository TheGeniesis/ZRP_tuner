package com.example.tuner

import io.reactivex.Completable

interface PitchPlayer {
    fun startPlaying(frequency: Double): Completable?
}