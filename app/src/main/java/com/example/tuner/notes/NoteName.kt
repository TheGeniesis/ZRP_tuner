package com.example.tuner.notes

enum class NoteName(name: String) {
    A("A"),
    A_SHARP("A♯"),
    B("B"),
    C("C"),
    C_SHARP("C♯"),
    D("D"),
    D_SHARP("D♯"),
    E("E"),
    F("F"),
    F_SHARP("F♯"),
    G("G"),
    G_SHARP("G♯"),
    UNDEFINED("Undefined");

    companion object {
        fun forName(name: String?): NoteName {
            if (name != null) {
                val updatedName =
                    name.trim { it <= ' ' }.toUpperCase().replace('#', '♯')
                try {
                    for (noteName in values()) {
                        if (noteName.name == updatedName) {
                            return noteName
                        }
                    }
                } catch (e: IllegalArgumentException) {
                }
            }
            return UNDEFINED
        }
    }

}