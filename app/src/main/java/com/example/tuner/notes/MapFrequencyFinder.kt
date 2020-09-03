package com.example.tuner.notes

import java.util.*

/**
* Implementacja oparta na interfejsie FrequencyFinder. Mapuje określone nuty dla określonych częstotliwości
*/

class MapFrequencyFinder : FrequencyFinder {
    private val notes: MutableMap<NoteName?, Double> =
        HashMap()

    override fun getFrequency(name: NoteName?): Double {
        return notes[name]!!
    }

    init {
        notes[NoteName.A] = 110.0
        notes[NoteName.A_SHARP] = 116.5
        notes[NoteName.B] = 123.5
        notes[NoteName.C] = 130.8
        notes[NoteName.C_SHARP] = 138.6
        notes[NoteName.D] = 146.8
        notes[NoteName.D_SHARP] = 155.6
        notes[NoteName.E] = 164.8
        notes[NoteName.F] = 174.6
        notes[NoteName.F_SHARP] = 185.0
        notes[NoteName.G] = 196.0
        notes[NoteName.G_SHARP] = 207.6
        notes[NoteName.UNDEFINED] = 0.0
    }
}