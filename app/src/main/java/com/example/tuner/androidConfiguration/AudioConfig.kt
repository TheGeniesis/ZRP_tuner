package com.example.tuner.androidConfiguration

/**
 * Interfejs zapewniający dostęp do szczegółów konfiguracji nagrywania i odtwarzania dźwięku.
 */

interface AudioConfig {

    fun getSampleRate(): Int
    // Częstotliwość próbkowania używana przez Audio Recorder'a w hercach.
    // Powinno zwrócić częstotliwość próbkowania nagrania audio.

    fun getInputBufferSize(): Int

    fun getOutputBufferSize(): Int
    // Rozmiar bazowej macierzy bufora fal wejściowych wykorzystywanej przez Audio Recorder'a.
    // To powinno być większe niż wartość zwrócona przez #getReadSize ()}.
    // Tej wartości należy używać tylko do tworzenia instancji Audio Recorder'a,
    // a podczas odczytywania lub wykonywania operacji na tablicy buforów przebiegów należy używać #getReadSize ()}.
    // Implementacja tej metody powinna zwrócić poprawną wartość dla urządzenia.
    // Powinno zwrócić wielkość bazowej tablicy buforów fal.

    fun getReadSize(): Int
    // Rozmiar tablicy danych, którą należy zapisać w buforze wyjściowym.
    // Tej wartości należy użyć podczas przetwarzania bufora danych kształtu fali.
    // Powinno zwrócić wielkość wartości odczytanych z podstawowej tablicy buforów fal.

    fun getWriteSize(): Int
    //Powinno zwrócić wielkość wartości zapisanych w podstawowej tablicy buforów fal.

    fun getInputChannel(): Int
    // Kanał wejściowy do pobierania danych wejściowych.
    ///Powinno zwrócić kanał wejściowy.

    fun getOutputChannel(): Int
    // Kanał wyjściowy do wysyłania danych. Na przykład wartość reprezentująca Stereo.
    // Powinno zwrócić kanał wyjściowy.

    fun getInputFormat(): Int
    // Wejściowy format audio tablicy buforowej (np. Short czy Float)
    // Powinno zwrócić format tablicy buforów.

    fun getOutputFormat(): Int
    // Wyjściowy format audio tablicy buforowej (np. Short czy Float)
    // Powinno zwrócić format tablicy buforów.

    fun getOutputFormatByteCount(): Int
    // Ilość bajtów reprezentujących pojedynczy kawałek danych wyjściowych.
    // Na przykład 16-bitowe dane PCM (Pulse-Code Modulation) mogą być reprezentowane przez dwa bajty.
    // Powinno zwrócić ilość bajtów potrzebnych do przedstawienia danych wyjściowych.

    fun getInputSource(): Int
    // Źródło wejściowe danych audio.
    // Powinno zwrócić źródło wejściowe.
}