package com.example.tuner.notes

/**
* Interfejs zapewniający sposób na uzyskanie częstotliwości z danej nuty.
*/

interface FrequencyFinder {
    fun getFrequency(name: NoteName?): Double
    // Pobiera częstotliwość dla podanej nazwy notatki.
    // (name) = Nazwa notatki, której częstotliwość jest pobierana.
    // Zwraca zęstotliwość reprezentująca nazwę podanej nuty.

}