package com.example.tuner.frequencyToWaveConversion

/**
 * Interfejs klasy, która konwertuje ByteArray lub ShortArray na FloatArray reprezentującą te same wartości.
 */

interface ArrayConverter {

    fun convert(array: ByteArray?, convertedArray: FloatArray?)
    // Konwertuje podaną ByteArray na odpowiednią FloatArray
    // (array) = ByteArray do konwersji.
    // (convertArray) = FloatArray reprezentująca ByteArray, ale jako wartość Float

    fun convert(array: ShortArray?, convertedArray: FloatArray?)
    //Konwertuje podaną krótką tablicę na odpowiednią tablicę zmiennoprzecinkową.
    // (array) = ShortArray do konwersji.
    // (convertArray) = FloatArray reprezentująca ShortArray, ale jako wartości Float
}