package com.example.tuner.notes

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

/**
* Implementacja zmiennej nuty interfejsu NoteMutator. Metoda ta ma na celu kontrolowanie zmiennych w nutach.
*/

class MutableNote : NoteMutator {
    private lateinit var name: String
    private var frequency = 0.0
    private var percentOffset: Float = 0f

    constructor() {
    }

    internal constructor(
        name: String?,
        frequency: Double,
        percentOffset: Float
    ) {
        if (name != null) {
            this.name = name
        }
        this.frequency = frequency
        this.percentOffset = percentOffset
    }

    override fun getName(): String {
        return name
    }

    override fun getFrequency(): Double {
        return frequency
    }

    override fun getPercentOffset(): Float {
        return percentOffset
    }

    override fun setName(name: String) {
        if (name != null) {
            this.name = name
        }
    }

    override fun setFrequency(frequency: Double) {
        this.frequency = frequency
    }

    override fun setPercentOffset(percentOffset: Float) {
        this.percentOffset = percentOffset
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val note =
            o as MutableNote
        return java.lang.Double.compare(note.frequency, frequency) == 0 && java.lang.Float.compare(
            note.percentOffset,
            percentOffset
        ) == 0 &&
                name == note.name
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun hashCode(): Int {
        return Objects.hash(name, frequency, percentOffset)
    }
}