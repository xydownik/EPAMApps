package com.example.mediaplayer.viewmodel
import androidx.lifecycle.ViewModel

class AudioViewModel : ViewModel() {
    var playbackPosition: Int = 0
    var wasPlaying: Boolean = false
}
