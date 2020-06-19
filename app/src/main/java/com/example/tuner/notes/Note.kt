package com.example.tuner.notes

/**
 * Interfejs reprezentujący nutę muzyczną w wyniku algorytmu wykrywania tonu
 */

interface Note {
    fun getName(): String
    // Pobiera nazwę nuty.
    // Zwraca ciąg reprezentujący nazwę nuty.

    fun getFrequency(): Double
    // Pobiera wartość częstotliwości najbliższej znalezionej nuty.
    // Zwraca powójna wartość reprezentująca częstotliwość najbliższej nuty.

    fun getPercentOffset(): Float
    // Pobiera przesunięcie procentowe od najbliższej znalezionej nuty i rzeczywistą znalezioną częstotliwość.
    // Zwraca wartość Float reprezentująca procentowe przesunięcie względem nuty.
}