package com.example.mediaplayer.activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.activityViewModels
import com.example.mediaplayer.databinding.FragmentAudioPlayerBinding
import com.example.mediaplayer.viewmodel.AudioViewModel

class AudioPlayerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAudioPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer
    private val viewModel: AudioViewModel by activityViewModels()

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
        _binding = FragmentAudioPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val uri = arguments?.getString("media_uri")?.let { Uri.parse(it) } ?: return

        binding.textFilePath.text = uri.toString()

        mediaPlayer = MediaPlayer().apply {
            setDataSource(requireContext(), uri)
            prepare()
            seekTo(viewModel.playbackPosition)
        }

        if (viewModel.wasPlaying) mediaPlayer.start()
        updateButton()

        binding.btnPlayPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                viewModel.wasPlaying = false
            } else {
                mediaPlayer.start()
                viewModel.wasPlaying = true
            }
            updateButton()
        }
    }

    private fun updateButton() {
        binding.btnPlayPause.text = if (viewModel.wasPlaying) "Pause" else "Play"
    }

    override fun onPause() {
        super.onPause()
        viewModel.playbackPosition = mediaPlayer.currentPosition
        viewModel.wasPlaying = mediaPlayer.isPlaying
        mediaPlayer.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.release()
        _binding = null
    }
}
