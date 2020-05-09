package com.example.tuner

class TuneModel(id: Int?, name: String, exactFrequency: Double) {

    var id : Int = 0
    var name : String = ""
    var exactFrequency : Double = 0.0

    init {
        if (id != null) {
            this.id = id
        }
        this.name = name
        this.exactFrequency = exactFrequency
    }

    override fun toString(): String {
        return this.name
    }
}