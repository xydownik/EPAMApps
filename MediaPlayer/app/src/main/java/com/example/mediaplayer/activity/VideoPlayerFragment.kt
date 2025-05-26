package com.example.mediaplayer.activity
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.activityViewModels
import com.example.mediaplayer.databinding.FragmentVideoPlayerBinding
import com.example.mediaplayer.viewmodel.VideoPlayerViewModel
import java.util.concurrent.TimeUnit

class VideoPlayerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoPlayerViewModel by activityViewModels()
    private val handler = Handler(Looper.getMainLooper())

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val uri = arguments?.getString("media_uri")?.let { Uri.parse(it) } ?: return

        binding.videoView.setVideoURI(uri)
        binding.seekBar.max = 0

        binding.btnPlayPause.setOnClickListener {
            if (binding.videoView.isPlaying) {
                binding.videoView.pause()
                binding.btnPlayPause.text = "Play"
            } else {
                binding.videoView.start()
                binding.btnPlayPause.text = "Pause"
            }
        }

        binding.videoView.setOnPreparedListener {
            binding.seekBar.max = binding.videoView.duration
            binding.videoView.seekTo(viewModel.playbackPosition)

            if (viewModel.wasPlaying) {
                binding.videoView.start()
                binding.btnPlayPause.text = "Pause"
            } else {
                binding.btnPlayPause.text = "Play"
            }

            updateSeekBar()
        }

        binding.videoView.setOnCompletionListener {
            viewModel.wasPlaying = false
            viewModel.playbackPosition = 0
            binding.seekBar.progress = 0
            updateTimeText(0, binding.videoView.duration)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) binding.videoView.seekTo(progress)
                updateTimeText(binding.videoView.currentPosition, binding.videoView.duration)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                val currentPosition = binding.videoView.currentPosition
                val duration = binding.videoView.duration

                if (duration > 0) {
                    binding.seekBar.progress = currentPosition
                    updateTimeText(currentPosition, duration)
                }

                // Продолжай обновление даже если видео на паузе
                handler.postDelayed(this, 500)
            }
        }, 0)
    }

    @SuppressLint("DefaultLocale")
    private fun updateTimeText(current: Int, total: Int) {
        val currentMin = TimeUnit.MILLISECONDS.toMinutes(current.toLong())
        val currentSec = TimeUnit.MILLISECONDS.toSeconds(current.toLong()) % 60
        val totalMin = TimeUnit.MILLISECONDS.toMinutes(total.toLong())
        val totalSec = TimeUnit.MILLISECONDS.toSeconds(total.toLong()) % 60
        binding.textTime.text = String.format("%02d:%02d / %02d:%02d", currentMin, currentSec, totalMin, totalSec)
    }

    override fun onPause() {
        super.onPause()
        viewModel.playbackPosition = binding.videoView.currentPosition
        viewModel.wasPlaying = binding.videoView.isPlaying
        binding.videoView.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}