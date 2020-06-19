package com.example.tuner

import com.example.tuner.notes.Note
import com.example.tuner.pitchDetection.PitchDetector
import com.example.tuner.notes.MutableNote
import com.example.tuner.notes.NoteFinder
import com.example.tuner.androidMediaRecorder.AudioRecorder
import io.reactivex.Observable

class GuitarTuner(
    audioRecorder: AudioRecorder,
    detector: PitchDetector,
    finder: NoteFinder
) :
    Tuner {
    private val note: MutableNote = MutableNote()
    private val observable: Observable<Note>
    override fun startListening(): Observable<Note?>? {
        return observable.share()
    }

    init {
        observable = Observable.create { emitter ->
            try {
                audioRecorder.startRecording()
                while (!emitter.isDisposed()) {
                    val frequency: Double = detector.detect(audioRecorder.readNext())
                    finder.setFrequency(frequency)
                    synchronized(note) {
                        note.setFrequency(frequency)
                        note.setName(finder.getNoteName())
                        note.setPercentOffset(finder.getPercentageDifference())
                    }
                    emitter.onNext(note)
                }
                audioRecorder.stopRecording()
                emitter.onComplete()
            } catch (exception: Exception) {
                emitter.tryOnError(exception)
            }
        }
    }
}