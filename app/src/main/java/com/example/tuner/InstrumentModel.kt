package com.example.tuner

class InstrumentModel(id: Int?, var name: String) {

    var id : Int = 0

    init {
        if (id != null) {
            this.id = id
        }
    }

    override fun toString(): String {
        return name
    }
}