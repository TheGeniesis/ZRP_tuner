package com.example.tuner.notes

/**
 * Implementacja macierzy interfejsu {@link NoteFinder}.
 * implementacja może dać nieprawidłowe wyniki, jeśli podana częstotliwość jest poza zakresem (5587.65 do 16.3516).
 * Powinno to wystarczyć dla aplikacji tunera gitarowego. Nie tworzymy tutaj aplikacji na miarę "sprzedam na Apple Store za 50zł"
*/

class FindNoteArray : NoteFinder {
    private val noteFrequencies = doubleArrayOf(
        5587.65, 5274.04, 4978.03, 4698.64, 4434.92,
        4186.01, 3951.07, 3729.31, 3520.00, 3322.44, 3135.96, 2959.96, 2793.83, 2637.02, 2489.02,
        2349.32, 2217.46, 2093.00, 1975.53, 1864.66, 1760.00, 1661.22, 1567.98, 1479.98, 1396.91,
        1318.51, 1244.51, 1174.66, 1108.73, 1046.50, 987.767, 932.328, 880.000, 830.609, 783.991,
        739.989, 698.456, 659.255, 622.254, 587.330, 554.365, 523.251, 493.883, 466.164, 440.000,
        415.305, 391.995, 369.994, 349.228, 329.628, 311.127, 293.665, 277.183, 261.626, 246.942,
        233.082, 220.000, 207.652, 195.998, 184.997, 174.614, 164.814, 155.563, 146.832, 138.591,
        130.813, 123.471, 116.541, 110.000, 103.826, 97.9989, 92.4986, 87.3071, 82.4069, 77.7817,
        73.4162, 69.2957, 65.4064, 61.7354, 58.2705, 55.0000, 51.9131, 48.9994, 46.2493, 43.6535,
        41.2034, 38.8909, 36.7081, 34.6478, 32.7032, 30.8677, 29.1352, 27.5000, 25.9565, 24.4997,
        23.1247, 21.8268, 20.6017, 19.4454, 18.3540, 17.3239, 16.3516
    )
    private val noteNames =
        arrayOf("F", "E", "D♯", "D", "C♯", "C", "B", "A♯", "A", "G♯", "G", "F♯")

    private var noteName: String? = null
    private var percentDiff = 0f
    override fun setFrequency(frequency: Double) {
        val length = noteFrequencies.size
        var frequencyIndex = 0
        var nextClosestIndex = 0

        // Iteruj przez cały NoteArray, aby znaleźć najbliższe indeksy
        var i = 0
        var j = 1
        while (i < length && j < length) {
            if (i == 0 && frequency > noteFrequencies[i]) {
                frequencyIndex = 0
                nextClosestIndex = 0
                break
            } else if (noteFrequencies[i] >= frequency && frequency > noteFrequencies[j]) {
                frequencyIndex =
                    if (noteFrequencies[i] - frequency < frequency - noteFrequencies[j]) i else j
                nextClosestIndex = if (frequencyIndex == i) j else i
                break
            } else if (j == length - 1) {
                frequencyIndex = length - 1
                nextClosestIndex = length - 1
            }
            i++
            j++
        }

        // Uzyskaj nazwę nuty
        noteName = noteNames[frequencyIndex % noteNames.size]

        // Znajdź różnicę i najbliższą odpowiednią nutę
        val difference = frequency - noteFrequencies[frequencyIndex]
        val nextClosestFrequency = noteFrequencies[nextClosestIndex]
        percentDiff = if (difference < 0) {
            ((frequency - nextClosestFrequency) / (noteFrequencies[frequencyIndex] - nextClosestFrequency) * 100).toFloat()
        } else {
            (-((frequency - noteFrequencies[frequencyIndex]) / (nextClosestFrequency - noteFrequencies[frequencyIndex]) * 100)).toFloat()
        }
        if (percentDiff < -100) {
            percentDiff = -100f
        } else if (percentDiff > 100) {
            percentDiff = 100f
        }
    }

    override fun getNoteName(): String {
        return noteName!!
    }

    override fun getPercentageDifference(): Float {
        return percentDiff
    }
}