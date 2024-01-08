package com.zeinabmallaki.adapter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeinabmallaki.adapter.adapter.NotesAdapter
import com.zeinabmallaki.adapter.databinding.ActivityMainBinding
import com.zeinabmallaki.adapter.data.local.db.DBHelper
import com.zeinabmallaki.adapter.data.local.db.dao.NotesDao

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dao: NotesDao
    private lateinit var adapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()


        binding.imgAddNotes.setOnClickListener {
            val intent = Intent(this, AddNotesActivity::class.java)
            intent.putExtra("newNotes",true)
            startActivity(intent) }


        binding.txtRecycleBin.setOnClickListener {

            val intent = Intent(this, RecycleBinActivity::class.java)
            startActivity(intent)


        }


    }


    override fun onStart() {
        super.onStart()

        val data = dao.getNotesForRecycler(DBHelper.FALSE_STATE)
        adapter.changeData(data)

    }

    private fun initRecycler()

    {

        dao = NotesDao(DBHelper(this))
        adapter = NotesAdapter(this, dao)

        binding.recyclerNotes.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false )
        binding.recyclerNotes.adapter = adapter


    }


}