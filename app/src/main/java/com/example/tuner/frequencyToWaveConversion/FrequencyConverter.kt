package com.example.tuner.frequencyToWaveConversion

/**
* Interfejs dla klasy, która przekształca podaną częstotliwość w buffor array danych waveform
*/

interface FrequencyConverter {
    fun convert(frequency: Double): FloatArray?
    // Konwertuje podaną wartość częstotliwości na Float reprezentującą kształt fali dźwiękowej dane o wartościach z zakresu [-1, 1].
    // (frequency) = Podana częstotliwość do konwersji.
    // Powinno zwrócić bufor fali dźwiękowej reprezentujący podaną częstotliwość.

}