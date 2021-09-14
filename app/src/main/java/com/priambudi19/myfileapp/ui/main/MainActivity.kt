package com.priambudi19.myfileapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.priambudi19.myfileapp.util.IntentConst
import com.priambudi19.myfileapp.R
import com.priambudi19.myfileapp.databinding.ActivityMainBinding
import com.priambudi19.myfileapp.ui.adapter.ListFileAdapter
import com.priambudi19.myfileapp.ui.add.AddFileActivity
import com.priambudi19.myfileapp.ui.detail.DetailActivity
import com.priambudi19.myfileapp.util.getColorCompat
import com.priambudi19.myfileapp.util.showToast
import com.priambudi19.myfileapp.vo.Resource
import com.robertlevonyan.components.picker.*
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var listFileAdapter: ListFileAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        getListFile()
        initFab()
    }

    private fun setupAdapter() {
        listFileAdapter = ListFileAdapter()
        listFileAdapter.onClickListener = {
            val i = Intent(this, DetailActivity::class.java).apply {
                putExtra(IntentConst.DATA, it)
            }
            startActivity(i)
        }
        binding.rvFile.apply {
            adapter = listFileAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun getListFile() {
        viewModel.getListFile().observe(this@MainActivity, {
            listFileAdapter.setList(it)
        })
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
            setListType(PickerDialog.ListType.TYPE_GRID)
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