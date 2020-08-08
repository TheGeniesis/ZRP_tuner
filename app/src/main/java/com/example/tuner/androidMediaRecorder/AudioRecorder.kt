package com.example.tuner.androidMediaRecorder


/**
* Interfejs klasy, która rejestruje lub odczytuje dane audio w zwróconym buforze.
*/
interface AudioRecorder {

    fun startRecording()
    // Rozpoczyna nasłuchiwanie danych audio i przygotowuje się do wywołania readNext(): FloatArray?.
    // Należy wywołać tę metodę przed wywołaniem metody readNext(): FloatArray aby rozpocząć nagrywanie.

    fun stopRecording()
    // Zatrzymuje nasłuchiwanie danych audio i czyści wszelkie podstawowe zasoby.

    fun readNext(): FloatArray?
    // Odczytuje dane audio do bufora macierzy i zwraca je.
    // Zwrócona tablica może być zmienna (mutable) w zależności od implementacji. (patrz: MutableNote jako przykład)
}