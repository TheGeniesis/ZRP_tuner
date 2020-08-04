package com.example.tuner.notes

internal interface NoteMutator : Note {
    fun setName(name: String)
    fun setFrequency(frequency: Double)
    fun setPercentOffset(percentOffset: Float)
}