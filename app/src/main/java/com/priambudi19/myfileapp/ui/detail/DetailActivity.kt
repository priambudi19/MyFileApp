package com.priambudi19.myfileapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.net.toUri
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
        getMessage()

    }

    private fun setupButton() {
        with(binding) {
            btnSaveEdit.setOnClickListener {
                btnSaveEdit.text = "Save"
                if (inputTitle.isEnabled) {
                    data?.let { _data ->
                        viewModel.updateDetail(
                            FileEntity(
                                _data.id,
                                inputTitle.text.toString(),
                                inputDesc.text.toString(),
                                _data.uri,
                                _data.fileName,
                                System.currentTimeMillis(),
                                _data.type
                            )
                        )
                    }
                }
                inputDesc.isEnabled = !inputDesc.isEnabled
                inputTitle.isEnabled = !inputTitle.isEnabled

            }
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
                    binding.btnSaveEdit.text = "Edit"
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