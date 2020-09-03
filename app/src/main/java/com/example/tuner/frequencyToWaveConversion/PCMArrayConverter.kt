package com.example.tuner.frequencyToWaveConversion

import kotlin.experimental.or

/**
 * Implementacja interfejsu {@link ArrayConverter}, który działa z 16-bitowymi danymi PCM.

 * PCM (ang. Pulse-Code Modulation) – metoda reprezentacji sygnału analogowego w systemach cyfrowych,
 * używana w telekomunikacji, w cyfrowej obróbce sygnału (np. w procesorach dźwięku),
 * do przetwarzania obrazu, do zapisu muzyki na płytach CD (CD-Audio).
 * Metoda ta polega na rejestrowaniu wartości chwilowej sygnału analogowego (próbkowaniu) w określonych, najczęściej równych odstępach czasu,
 * czyli z określoną częstością, zwaną częstotliwością próbkowania. .

*/

class PCMArrayConverter : ArrayConverter {

    override fun convert(array: ByteArray?, convertedArray: FloatArray?) {
        val arrayLength = array?.size
        var i = 0
        var j = 0
        while (i < arrayLength!!) {
            var twoBytes = array[i] or array[i + 1] or  8
            convertedArray?.set(j, twoBytes.toFloat() / SHORT_DIVISOR)
            convertedArray?.set(j, (if (convertedArray[j] < -1) -1 else convertedArray[j].toInt()
                .coerceAtMost(1)).toFloat()
            )
            i += 2
            j++
        }
    }

    override fun convert(array: ShortArray?, convertedArray: FloatArray?) {
        val arrayLength = array?.size
        val convertedArrayLength = convertedArray?.size
        var i = 0
        while (i < arrayLength!! && i < convertedArrayLength!!) {
            convertedArray[i] =
                array[i].toFloat() / SHORT_DIVISOR
            if ( convertedArray[i] < -1) {
                convertedArray[i] = (-1).toFloat()
            } else {
                convertedArray[i] = convertedArray[i].coerceAtMost(
                    1f
                )
            }
            i++
        }
    }

    companion object {
        private const val SHORT_DIVISOR =
            (-1 * Short.MIN_VALUE).toShort()
    }
}