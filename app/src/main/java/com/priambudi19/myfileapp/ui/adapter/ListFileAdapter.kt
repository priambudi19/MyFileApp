package com.priambudi19.myfileapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.fetch.VideoFrameUriFetcher
import coil.load
import com.priambudi19.myfileapp.R
import com.priambudi19.myfileapp.data.model.FileEntity
import com.priambudi19.myfileapp.databinding.ItemRowBinding
import com.priambudi19.myfileapp.util.formatToStringDate
import com.robertlevonyan.components.picker.ItemType

class ListFileAdapter : RecyclerView.Adapter<ListFileAdapter.ItemHolder>() {
    private val listFile: ArrayList<FileEntity> = arrayListOf()
    var onClickListener: ((FileEntity) -> Unit)? = null

    inner class ItemHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FileEntity) {
            with(binding) {
                tvDate.text = item.date.formatToStringDate()
                tvTitleRow.text = item.title
                tvDescRow.text = item.description
                tvFileName.text = item.fileName
                when (item.type) {
                    ItemType.ITEM_VIDEO_GALLERY.ordinal -> {
                        ivFileType.load(root.context.cacheDir.path + "/${item.fileName}") {
                            fetcher(VideoFrameUriFetcher(root.context))
                        }
                        ivPlayRow.visibility = View.VISIBLE
                    }
                    ItemType.ITEM_VIDEO.ordinal -> {
                        ivFileType.load(root.context.cacheDir.path + "/${item.fileName}") {
                            fetcher(VideoFrameUriFetcher(root.context))
                        }
                        ivPlayRow.visibility = View.VISIBLE
                    }
                    ItemType.ITEM_CAMERA.ordinal -> ivFileType.load(item.uri)
                    ItemType.ITEM_GALLERY.ordinal -> ivFileType.load(item.uri)
                    ItemType.ITEM_FILES.ordinal -> ivFileType.load(R.drawable.ic_file)
                    else -> ivFileType.load(R.drawable.ic_file)
                }
            }
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<FileEntity>) {
        this.listFile.clear()
        this.listFile.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = listFile[position]
        holder.bind(item)
        holder.binding.root.setOnClickListener { onClickListener?.invoke(item) }
    }

    override fun getItemCount(): Int = listFile.size
}