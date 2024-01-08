package com.zeinabmallaki.adapter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.zeinabmallaki.adapter.databinding.ActivityAddNotesBinding
import com.zeinabmallaki.adapter.data.local.db.DBHelper
import com.zeinabmallaki.adapter.data.local.db.dao.NotesDao
import com.zeinabmallaki.adapter.data.model.DBNotesModel
import com.zeinabmallaki.adapter.utils.PersianDate

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getBooleanExtra("newNotes", false)
        val id = intent.getIntExtra("notesId", 0)


        val dao = NotesDao(DBHelper(this))

        if (type) {
            binding.txtDate.text = getDate()

        } else {

            val notes = dao.getNotesById(id)
            val edit = Editable.Factory()
            binding.edtTitleNotes.text = edit.newEditable(notes.title)
            binding.edtDetailNotes.text = edit.newEditable(notes.detail)
            binding.txtDate.text = notes.date
        }






        binding.btnSave.setOnClickListener {

            val title = binding.edtTitleNotes.text.toString()
            val details = binding.edtDetailNotes.text.toString()

            if (title.isNotEmpty()) {

                val notes = DBNotesModel(0, title, details, DBHelper.FALSE_STATE, getDate())
                val result = if (type)
                    dao.saveNotes(notes)
                else
                    dao.editNotes(id, notes)




                if (result) {
                    showText("یادداشت ذخیره شد")
                    finish()
                } else showText("خطا در ذخیره سازی")

            } else
                showText("عنوان خالی نباشد")
        }
        binding.btnCancel.setOnClickListener { finish() }

    }

    private fun getDate(): String {

        val persianDate = PersianDate()

        val currentDate = "${persianDate.year}/${persianDate.month}/${persianDate.day}"
        val currentTime = "${persianDate.hour} : ${persianDate.min}"

        return "$currentDate | $currentTime"

    }

    private fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}