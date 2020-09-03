package com.example.tuner.pitchDetection

import com.example.tuner.androidConfiguration.AudioConfig

/**
 * Implementacja PitchDetector, która wykorzystuje algorytm YIN do określania częstotliwości dostarczone dane kształtu fali.
 * Algorytm YIN jest podobny do użytej funkcji autokorelacji do wykrywania wysokości tonu,
 * ale dodaje dodatkowe kroki w celu zwiększenia dokładności wyników.

 * Metoda ta bazuje na danych z "http://recherche.ircam.fr/equipes/pcm/cheveign/ps/2002_JASA_YIN_proof.pdf"
 * oraz paru innych programów, które wykorzystują tą metodę do wykrywania dźwięku.
 *
 * Jest sześć kroków w algorytmie YIN:
 *
 * Metoda autokorelacji
 * Funkcja różnicowa
 * Funkcja skumulowanej średniej znormalizowanej różnicy
 * Absolutny próg
 * Interpolacja paraboliczna
 * Najlepsze oszacowanie lokalne

 * Pierwszy i drugi krok są połaczone w jeden.
 */

class autoCorrelationPitchDetector(audioConfig: AudioConfig) : PitchDetector {

    private val sampleRate: Double
    private val resultBuffer: FloatArray

    override fun detect(wave: FloatArray?): Double {
        val tau: Int
        // Najpierw wykonaj funkcje normalizujące dane fali

        if (wave != null) {
            autoCorrelationDifference(wave)
        }
        // Pierwsz i drugi krok w algorytmie

        cumulativeMeanNormalizedDifference()
        // Trzeci krok w algorytmie

        tau = absoluteThreshold()
        // Czwarty krok w algorytmie

        val betterTau = parabolicInterpolation(tau)
        // Piąty krok w algorytmie

        return sampleRate / betterTau
        // Częstotliwość nuty to częstotliwość próbkowania podzielona przez tau (indeks w tablicy buforowej oznaczającej kropkę).
        // Okres jest czasem trwania (lub indeksem tutaj) jednego cyklu.
        // Częstotliwość = 1 / okres, w odniesieniu do częstotliwości próbkowania, częstotliwość = częstotliwość próbkowania / okres.
    }

    private fun autoCorrelationDifference(wave: FloatArray) {
        // newValue = oldValue / (runningSum / tau)
        // == (oldValue / 1) * (tau / runningSum)
        // == oldValue * (tau / runningSum)


        val length = resultBuffer.size
        var i: Int
        var j: Int
        j = 1
        while (j < length) {
            i = 0
            while (i < length) {
                //d sub t (tau) = (x(i) - x(i - tau))^2, from i = 1 aby uzyskać rozmiar bufora
                resultBuffer[j] += Math.pow((wave[i] - wave[i + j]).toDouble(), 2.0).toFloat()
                i++
            }
            j++
        }
    }

    private fun cumulativeMeanNormalizedDifference() {
        var i: Int
        val length = resultBuffer.size
        var runningSum = 0f
        resultBuffer[0] = 1f
        i = 1
        while (i < length) {
            runningSum += resultBuffer[i]
            resultBuffer[i] *= i / runningSum
            // Bieżąca wartość jest aktualizowana i jest bieżącą wartością pomnożoną przez indeks podzielony przez bieżącą wartość sumy.
            i++
        }
    }

    private fun absoluteThreshold(): Int {
        var tau: Int
        val length = resultBuffer.size

        // Pierwsze dwie wartości w buforze wyników powinny wynosić 1, więc zacznij od trzeciej wartości
        tau = 2
        while (tau < length) {

            // Jeśli jesteśmy poniżej progu, kontynuuj, aż znajdziemy najniższą wartość
            // wskazujący najniższy spadek na fali, odkąd po raz pierwszy przekroczyliśmy próg.
            if (resultBuffer[tau] < ABSOLUTE_THRESHOLD) {
                while (tau + 1 < length && resultBuffer[tau + 1] < resultBuffer[tau]) {
                    tau++
                }

                // We have the approximate tau value, so break the loop
                break
            }
            tau++
        }

        // Niektóre implementacje tego algorytmu ustawiają wartość tau na -1, aby wskazać niepoprawne tau
        // wartość została znaleziona. Ta implementacja zwróci tylko ostatnie tau.
        tau = if (tau >= length) length - 1 else tau
        return tau
    }

    private fun parabolicInterpolation(currentTau: Int): Float {

        // Znajduje punkty, które pasują do paraboli
        val x0 = if (currentTau < 1) currentTau else currentTau - 1
        val x2 = if (currentTau + 1 < resultBuffer.size) currentTau + 1 else currentTau

        // Znajduje lepsze oszacowanie tau
        val betterTau: Float
        betterTau = if (x0 == currentTau) {
            if (resultBuffer[currentTau] <= resultBuffer[x2]) {
                currentTau.toFloat()
            } else {
                x2.toFloat()
            }
        } else if (x2 == currentTau) {
            if (resultBuffer[currentTau] <= resultBuffer[x0]) {
                currentTau.toFloat()
            } else {
                x0.toFloat()
            }
        } else {

            // Dopasuj parabolę między pierwszym punktem, bieżącym tau i ostatnim punktem, aby znaleźć a
            // lepsze oszacowanie tau.
            val s0 = resultBuffer[x0]
            val s1 = resultBuffer[currentTau]
            val s2 = resultBuffer[x2]
            currentTau + (s2 - s0) / (2 * (2 * s1 - s2 - s0))
        }
        return betterTau
    }

    companion object {
        private const val ABSOLUTE_THRESHOLD = 0.125f
        // Zgodnie z metodą YIN próg powinien wynosić od 0,10 do 0,15
    }

    init {
        sampleRate = audioConfig.getSampleRate().toDouble()
        resultBuffer = FloatArray(audioConfig.getReadSize() / 2)
    }
}
