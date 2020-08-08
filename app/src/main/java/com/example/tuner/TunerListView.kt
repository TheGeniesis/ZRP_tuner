package com.example.tuner

class TunerListView(
        id: Int?,
        name: String,
        customTuning: Boolean,
        instrumentId: Int,
        tune1: String?,
        tune2: String?,
        tune3: String?,
        tune4: String?,
        tune5: String?,
        tune6: String?,
        tune7: String?,
        tune8: String?,
        tune9: String?,
        tune10: String?
    ) {

        var id: Int? = null
        var tune1: String? = null
        var tune2: String? = null
        var tune3: String? = null
        var tune4: String? = null
        var tune5: String? = null
        var tune6: String? = null
        var tune7: String? = null
        var tune8: String? = null
        var tune9: String? = null
        var tune10: String? = null
        var name: String = ""
        var customTuning: Boolean = false
        var instrumentId: Int = 0

        init {
            this.id = id
            this.name = name
            this.customTuning = customTuning
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
            this.instrumentId = instrumentId
        }

    fun tuning (): String
    {
        var result = ""
        if (tune1 !== null) { result += "$tune1, " }
        if (tune2 !== null) { result += "$tune2, " }
        if (tune3 !== null) { result += "$tune3, " }
        if (tune4 !== null) { result += "$tune4, " }
        if (tune5 !== null) { result += "$tune5, " }
        if (tune6 !== null) { result += "$tune6, " }
        if (tune7 !== null) { result += "$tune7, " }
        if (tune8 !== null) { result += "$tune8, " }
        if (tune9 !== null) { result += "$tune9, " }
        if (tune10 !== null) { result += "$tune10" }

        return result.trimEnd(',').trimEnd(' ').trimEnd(',');
    }

    fun name(): String
    {
        return name
    }

    fun customTuning(): Boolean
    {
        return customTuning
    }
    fun id(): Int?
    {
        return id
    }
    fun instrumentId(): Int?
    {
        return instrumentId
    }
}