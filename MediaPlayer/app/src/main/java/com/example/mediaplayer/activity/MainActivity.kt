package com.example.mediaplayer.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val PICK_AUDIO_REQUEST = 1
        private const val PICK_VIDEO_REQUEST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlayAudio.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            startActivityForResult(Intent.createChooser(intent, "Select Audio"), PICK_AUDIO_REQUEST)
        }

        binding.btnPlayVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedMediaUri: Uri = data.data ?: return

            when (requestCode) {
                PICK_AUDIO_REQUEST -> {
                    val audioFragment = AudioPlayerFragment().apply {
                        arguments = bundleOf("media_uri" to selectedMediaUri.toString())
                    }
                    audioFragment.show(supportFragmentManager, "AudioPlayer")
                }
                PICK_VIDEO_REQUEST -> {
                    val videoFragment= VideoPlayerFragment().apply {
                        arguments = bundleOf("media_uri" to selectedMediaUri.toString())
                    }
                    videoFragment.show(supportFragmentManager, "VideoPlayer")
                }
            }
        }
    }
}
