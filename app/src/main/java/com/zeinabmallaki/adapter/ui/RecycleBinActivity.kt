package com.zeinabmallaki.adapter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeinabmallaki.adapter.adapter.NotesAdapter
import com.zeinabmallaki.adapter.adapter.RecycleBinAdapter
import com.zeinabmallaki.adapter.data.local.db.DBHelper
import com.zeinabmallaki.adapter.data.local.db.dao.NotesDao
import com.zeinabmallaki.adapter.databinding.ActivityRecycleBinBinding

class RecycleBinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecycleBinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleBinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()
    }


    private fun initRecycler(){

        val dao = NotesDao(DBHelper(this))
        val adapter = RecycleBinAdapter(this, dao)

        binding.recyclerNotes.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false )
        binding.recyclerNotes.adapter = adapter


    }

}