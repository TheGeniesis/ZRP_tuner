package com.example.tuner

class BasicTunerModel(
    id: Int?,
    name: String,
    instrumentId: Int,
    customTuning: Boolean,
    order: Int,
    tune1: Int?,
    tune2: Int?,
    tune3: Int?,
    tune4: Int?,
    tune5: Int?,
    tune6: Int?,
    tune7: Int?,
    tune8: Int?,
    tune9: Int?,
    tune10: Int?
) {

    var instrumentId: Int = 0
    var customTuning: Boolean = false
    var order: Int = 0
    var tune1: Int? = null
    var tune2: Int? = null
    var tune3: Int? = null
    var tune4: Int? = null
    var tune5: Int? = null
    var tune6: Int? = null
    var tune7: Int? = null
    var tune8: Int? = null
    var tune9: Int? = null
    var tune10: Int? = null
    var name: String = ""
    var id: Int = 0

    init {
        if (id != null) {
            this.id = id
        }
        this.order = order
        this.name = name
        this.customTuning = customTuning
        this.instrumentId = instrumentId
        this.tune1 = tune1
        this.tune2 = tune2
        this.tune3 = tune3
        this.tune4 = tune4
        this.tune5 = tune5
        this.tune6 = tune6
        this.tune7 = tune7
        this.tune8 = tune8
        this.tune9 = tune9
        this.tune10 = tune10
    }

    override fun toString(): String {
        return this.name
    }
}