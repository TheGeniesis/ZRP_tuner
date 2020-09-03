package com.example.tuner

class InstrumentModel(id: Int?, name: String, order: Int) {

    var id : Int = 0
    var name : String = ""
    var order : Int = 0

    init {
        if (id != null) {
            this.id = id
        }
        this.name = name
        this.order = order
    }

    override fun toString(): String {
        return this.name
    }
}