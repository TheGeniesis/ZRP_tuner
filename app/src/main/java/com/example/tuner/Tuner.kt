package com.example.tuner

import com.example.tuner.notes.Note
import io.reactivex.Observable

interface Tuner {
    fun startListening(): Observable<Note?>?
}