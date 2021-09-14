package com.priambudi19.myfileapp.ui.add

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import coil.fetch.VideoFrameUriFetcher
import coil.load
import coil.size.Scale
import com.priambudi19.myfileapp.util.IntentConst
import com.priambudi19.myfileapp.R
import com.priambudi19.myfileapp.data.model.FileEntity
import com.priambudi19.myfileapp.databinding.ActivityAddFileBinding
import com.priambudi19.myfileapp.ui.add.AddFileViewModel.*
import com.priambudi19.myfileapp.util.getName
import com.priambudi19.myfileapp.util.showToast
import com.robertlevonyan.components.picker.ItemType
import okio.IOException
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*
import java.net.URI

class AddFileActivity : AppCompatActivity() {
    private val viewModel: AddFileViewModel by viewModel()
    private lateinit var binding: ActivityAddFileBinding
    private var type: Int? = null
    private var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        title = getString(R.string.add_file)
        super.onCreate(savedInstanceState)
        binding = ActivityAddFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        type = intent.getIntExtra(IntentConst.TYPE, 0)
        uri = intent.getStringExtra(IntentConst.URI)?.toUri()
        setupThumbnail(uri, type)
        setupFileName(uri)
        getMessage()

        binding.btnSave.setOnClickListener {
            try {
                with(binding) {
                    val title = inputTitle.text.toString()
                    val desc = inputDesc.text.toString()
                    if (title.isNotBlank() && desc.isNotBlank()) {
                        viewModel.insertFile(
                            FileEntity(
                                title = title,
                                description = desc,
                                uri = save(uri!!,tvFileNameAdd.text.toString()),
                                fileName = tvFileNameAdd.text.toString(),
                                type = type ?: ItemType.ITEM_FILES.ordinal,
                                date = System.currentTimeMillis()
                            )
                        )
                    }else if (title.isBlank()){
                        textInputLayout.isErrorEnabled = true
                        textInputLayout.error = "Field is required"
                    }else if(desc.isBlank()){
                        textInputLayoutDesc.isErrorEnabled = true
                        textInputLayoutDesc.error = "Field is required"
                    }else{
                        Unit
                    }
                }
            } catch (e: IOException) {
                showToast(e.message)
            }
        }

    }

    private fun save(uri: Uri, newName: String): String {
        val file = File(cacheDir.path,newName)
        val byte = contentResolver.openInputStream(uri)?.readBytes()
        byte?.let { file.writeBytes(it) }
        return file.toUri().toString()
    }

    private fun getMessage() {
        viewModel.event.observe(this,{
            when(it){
                is Event.Success -> {
                    showToast("File added!")
                    finish()
                }
                is Event.Error -> {
                    showToast(it.message)
                }
                else -> Unit
            }
        })
    }

    @SuppressLint("Recycle")
    private fun setupFileName(uri: Uri?) {
        binding.tvFileNameAdd.text = uri?.getName(this)
    }

    private fun setupThumbnail(uri: Uri?, type: Int?) {
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