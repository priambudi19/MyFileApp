package com.priambudi19.myfileapp.ui.add

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import coil.fetch.VideoFrameUriFetcher
import coil.load
import coil.size.Scale
import com.priambudi19.myfileapp.util.IntentConst
import com.priambudi19.myfileapp.R
import com.priambudi19.myfileapp.databinding.ActivityAddFileBinding
import com.robertlevonyan.components.picker.ItemType
import java.io.*

class AddFileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        title = getString(R.string.add_file)
        super.onCreate(savedInstanceState)
        binding = ActivityAddFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val type = intent.getIntExtra(IntentConst.TYPE, 0)
        val uri = intent.getStringExtra(IntentConst.URI)?.toUri()
        setupThumbnail(uri, type)
        setupFileName(uri)

    }

    @SuppressLint("Recycle")
    private fun setupFileName(uri: Uri?) {
        uri?.let {
            val cursor = contentResolver.query(
                it,
                arrayOf(android.provider.MediaStore.Files.FileColumns.DISPLAY_NAME),
                null,
                null,
                null
            )
            cursor?.let { cr ->
                if (cr.moveToFirst()) {
                    val name = Uri.parse(cr.getString(0)).lastPathSegment
                    binding.tvFileNameAdd.text = name
                }
                cr.close()
            }
        }
    }

    private fun setupThumbnail(uri: Uri?, type: Int) {
        uri?.let {
            with(binding) {
                when (type) {
                    ItemType.ITEM_GALLERY.ordinal -> {
                        ivPreview.load(uri)
                    }
                    ItemType.ITEM_FILES.ordinal -> {
                        ivPreview.load(R.drawable.ic_attachment)
                    }
                    ItemType.ITEM_VIDEO.ordinal -> {
                        ivPlay.visibility = View.VISIBLE
                        ivPreview.load(uri) {
                            fetcher(VideoFrameUriFetcher(this@AddFileActivity))
                            scale(Scale.FILL)
                        }
                    }
                    ItemType.ITEM_CAMERA.ordinal -> {
                        ivPreview.load(uri)
                    }
                    ItemType.ITEM_VIDEO_GALLERY.ordinal -> {
                        ivPlay.visibility = View.VISIBLE
                        ivPreview.load(uri) {
                            fetcher(VideoFrameUriFetcher(this@AddFileActivity))
                            scale(Scale.FILL)
                        }
                    }
                    else -> Unit
                }
            }

        }
    }


}