package com.priambudi19.myfileapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import coil.fetch.VideoFrameUriFetcher
import coil.load
import coil.size.Scale
import com.priambudi19.myfileapp.R
import com.priambudi19.myfileapp.data.model.FileEntity
import com.priambudi19.myfileapp.databinding.ActivityDetailBinding
import com.priambudi19.myfileapp.util.IntentConst
import com.priambudi19.myfileapp.util.showToast
import com.robertlevonyan.components.picker.ItemType
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var data: FileEntity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        data = intent.getParcelableExtra<FileEntity>(IntentConst.DATA)
        setupThumnail()
        setupData()
        setupButton()
        setupInputListener()
        getMessage()


    }

    private fun setupInputListener() {
        with(binding){
            inputTitle.addTextChangedListener {
                if (it.isNullOrBlank()){
                    inputTitle.error = "Field is required"
                    textInputLayout.isErrorEnabled = true
                }else{
                    inputTitle.error = null
                    textInputLayout.isErrorEnabled = false
                }
            }
            inputDesc.addTextChangedListener {
                if (it.isNullOrBlank()){
                    inputTitle.error = "Field is required"
                    textInputLayoutDesc.isErrorEnabled = true
                }else{
                    inputTitle.error = null
                    textInputLayoutDesc.isErrorEnabled = false
                }
            }
        }
    }

    private fun setupButton() {
        with(binding) {
            viewModel.isEditing.observe(this@DetailActivity, {
                inputTitle.isEnabled = it
                inputDesc.isEnabled = it
                if (it) {
                    btnSaveEdit.text = "Save"
                    btnSaveEdit.setOnClickListener {
                        viewModel.edit()
                        data?.let { old ->
                            val title = inputTitle.text.toString()
                            val desc = inputDesc.text.toString()
                            if (title.isNotBlank() && desc.isNotBlank()) {
                                viewModel.updateDetail(
                                    FileEntity(
                                        id = old.id,
                                        title = title,
                                        description = desc,
                                        uri = old.uri,
                                        fileName = old.fileName,
                                        date = System.currentTimeMillis(),
                                        type = old.type
                                    )
                                )
                            }
                        }
                    }
                } else {
                    btnSaveEdit.text = "Edit"
                    btnSaveEdit.setOnClickListener {
                        viewModel.edit()
                    }
                }
            })
            btnDelete.setOnClickListener { data?.let { _data -> viewModel.deleteFile(_data) } }

        }
    }

    private fun setupData() {
        with(binding) {
            inputTitle.text = Editable.Factory.getInstance().newEditable(data?.title)
            inputDesc.text = Editable.Factory.getInstance().newEditable(data?.description)
        }
    }

    private fun setupThumnail() {
        with(binding) {
            when (data?.type) {
                ItemType.ITEM_GALLERY.ordinal -> {
                    ivPreview.load(data?.uri)
                }
                ItemType.ITEM_FILES.ordinal -> {
                    ivPreview.load(R.drawable.ic_attachment)
                }
                ItemType.ITEM_VIDEO.ordinal -> {
                    ivPlay.visibility = View.VISIBLE
                    ivPreview.load(data?.uri?.toUri()) {
                        fetcher(VideoFrameUriFetcher(this@DetailActivity))
                        scale(Scale.FILL)
                    }
                }
                ItemType.ITEM_CAMERA.ordinal -> {
                    ivPreview.load(data?.uri)
                }
                ItemType.ITEM_VIDEO_GALLERY.ordinal -> {
                    ivPlay.visibility = View.VISIBLE
                    ivPreview.load(cacheDir.path + "/${data?.fileName}") {
                        fetcher(VideoFrameUriFetcher(ivPreview.context))
                        build()
                    }
                }
                else -> Unit
            }
        }

    }

    private fun getMessage() {
        viewModel.event.observe(this, {
            when (it) {
                is DetailViewModel.Event.EditSuccess -> {
                    showToast(it.message)
                }
                is DetailViewModel.Event.EditError -> {
                    showToast(it.message)
                }

                is DetailViewModel.Event.DeleteSuccess -> {
                    showToast(it.message)
                    finish()
                }
                is DetailViewModel.Event.DeleteError -> {
                    showToast(it.message)
                }

                else -> Unit
            }
        })
    }
}