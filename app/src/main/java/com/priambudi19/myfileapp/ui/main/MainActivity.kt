package com.priambudi19.myfileapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.priambudi19.myfileapp.util.IntentConst
import com.priambudi19.myfileapp.R
import com.priambudi19.myfileapp.databinding.ActivityMainBinding
import com.priambudi19.myfileapp.ui.add.AddFileActivity
import com.priambudi19.myfileapp.util.getColorCompat
import com.robertlevonyan.components.picker.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFab()
    }

    private fun initFab() {
        binding.fabAdd.setOnClickListener { showDialog() }
    }


    private fun showDialog() {

        pickerDialog {
            val bgColor = getColorCompat(R.color.secondaryColor)
            setTitle(getString(R.string.choose_file_type))
            setTitleTextBold(true)
            setTitleTextSize(22f)
            setItems(
                setOf(
                    ItemModel(
                        ItemType.ITEM_CAMERA,
                        itemBackgroundColor = bgColor
                    ),
                    ItemModel(
                        ItemType.ITEM_GALLERY,
                        itemBackgroundColor = bgColor
                    ),
                    ItemModel(
                        ItemType.ITEM_VIDEO,
                        itemBackgroundColor = bgColor
                    ),
                    ItemModel(
                        ItemType.ITEM_VIDEO_GALLERY,
                        itemBackgroundColor = bgColor
                    ),
                    ItemModel(
                        ItemType.ITEM_FILES,
                        itemBackgroundColor = bgColor
                    )
                )
            )
            setListType(PickerDialog.ListType.TYPE_LIST)
        }.setPickerCloseListener { type, uris ->
            Log.d("PickerClose", "type : ${type.name}\n uri : $uris ")
            if (uris.isNotEmpty()) {
                val uri = uris.first()
                val i = Intent(this, AddFileActivity::class.java).apply {
                    putExtra(IntentConst.TYPE, type.ordinal)
                    putExtra(IntentConst.URI, uri.toString())
                }
                startActivity(i)
            }
        }.show()
    }


}