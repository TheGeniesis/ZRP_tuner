package com.example.tuner.androidMediaPlayer

/**
* Interfejs dla klasy, która odtwarza dostarczone dane fali dźwiękowej.
*/

interface AudioPlayer {
    fun play()
    // Rozpoczyna odtwarzanie bufora podanego w #setBuffer(waveformBuffer: FloatArray?) na wyjściu audio.

    fun stop()
    // Zatrzymuje odtwarzanie na wyjściu audio bufora dostarczonego w #setBuffer(waveformBuffer: FloatArray?).
    fun release()
    // Zwalnia wszystkie wewnętrzne zasoby w AudioPlayer. Powinno to zostać wywołane, gdy
    // AudioPlayer nie jest już potrzebny i po wywołaniu {@link #stop ()}.
    // Po tej metodzie jest nazywany, nowy AudioPlayer będzie musiał zostać utworzony, aby odtworzyć ponownie.

    fun setBuffer(waveformBuffer: FloatArray?)
    // Ustawia dane waveform, które będą odtwarzane na wyjściu audio.
    // Odtwarza dane waveform.
}