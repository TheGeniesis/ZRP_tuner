package com.example.tuner.pitchDetection

/**
* Interfejs dla implementacji wykrywających wysokość dźwięku za pomocą reprezentacji sinusoidalnej fali dźwiękowej.
*/

interface PitchDetector {
    fun detect(wave: FloatArray?): Double
    // Pobiera częstotliwość tonu (uwaga) przy użyciu dostarczonej tablicy wartości reprezentujących dźwięk fala.

    // (wave) = 16 bitowy array wartości reprezentujący falę dźwiękową do przetworzenia.
    // Powinno zwrócić podwójna wartość reprezentująca wykrytą wysokość.

}