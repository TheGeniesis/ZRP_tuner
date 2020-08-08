package com.example.tuner.notes

/**
* Interfejs implementacji, który znajduje najbliższą nazwę nuty i różnicę częstotliwości
* na podstawie podanej wartości częstotliwości.
*/

interface NoteFinder {

    fun setFrequency(frequency: Double)
    // Ustawia częstotliwość nuty. Ta metoda musi zostać wywołana przed wywołaniem innych metod.
    // Nieprzestrzeganie tego może spowodować nieprzewidywalne zachowanie.
    // (frequency) = Częstotliwość nuty.


    fun getNoteName(): String
    // Pobiera nazwę najbliższej nuty zapisanej w String do podanej częstotliwości.
    // Zwraca nazwę nuty.

    fun getPercentageDifference(): Float
    // Pobiera różnicę procentową między najbliższą znalezioną nutą a podaną częstotliwością.
    // Jest to przesunięcie procentowe między najbliższą nutą a drugą najbliższą nutą.
    // Wartość ujemna wskazuje, że jest to niższy ton niż najbliższa znaleziona nuta
    // Dodatnia wartość wskazuje, że jest to wyższy ton niż najbliższa znaleziona nuta.
    // Zwraca procentowe przesunięcie od najbliższej nuty.

}