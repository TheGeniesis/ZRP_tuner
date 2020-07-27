package com.example.tuner

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import com.example.tuner.androidMediaRecorder.AudioRecorder
import com.example.tuner.notes.MutableNote
import com.example.tuner.notes.Note
import com.example.tuner.pitchDetection.PitchDetector
import io.reactivex.Observable


class GuitarTuner(
    audioRecorder: AudioRecorder,
    detector: PitchDetector
) :
    Tuner {
    private val note: MutableNote = MutableNote()
    private val observable: Observable<Note>
        get() {
            TODO()
        }

    override fun startListening(): Observable<Note?>? {
        return observable.share()
    }

    init {
        // construct audio stream in 16bit format with sample rate of 44100Hz

        // construct audio stream in 16bit format with sample rate of 44100Hz

//        while (true) {
//            audioRecorder.startRecording()
//            val frequency: Double = detector.detect(audioRecorder.readNext())
//            System.out.println(frequency)
//        }

//        observable = Observable.create { emitter ->
//            try {
//                audioRecorder.startRecording()
//                while (!emitter.isDisposed()) {
//                    val frequency: Double = detector.detect(audioRecorder.readNext())
//                    //finder.setFrequency(frequency)
////                    synchronized(note) {
////                        note.setFrequency(frequency)
////                        note.setName(finder.getNoteName())
////                        note.setPercentOffset(finder.getPercentageDifference())
////                    }
//                    emitter.onNext(note)
//                }
//                audioRecorder.stopRecording()
//                emitter.onComplete()
//            } catch (exception: Exception) {
//                emitter.tryOnError(exception)
//            }
//        }
    }
}